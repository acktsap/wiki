package acktsap.webconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest // 모든 bean들을 다 등록해줌
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Marshaller marshaller;

    /**
     * Custom Formatter 사용하는 방법. intercepter보기
     */
    @Test
    public void hello() throws Exception {
        this.mockMvc.perform(get("/hello")
                .param("name", "keesun"))
                .andDo(print())
                .andExpect(content().string("hello keesun"));
    }

    /**
     * JPA를 이용해서 Converter 사용하는 방법, intercepter보기
     */
    @Test
    public void hello2() throws Exception {
        EntityPerson newPerson = new EntityPerson();
        newPerson.setName("keesun");
        EntityPerson savedPerson = personRepository.save(newPerson);

        this.mockMvc.perform(get("/hello2")
                .param("id", savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello keesun"));
    }

    /**
     * Default handler로 인해 /static에서 읽어옴
     */
    @Test
    public void helloStaticDefault() throws Exception {
        this.mockMvc.perform(get("/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Hello Index")));
    }

    /**
     * Custom handler로 인해 /modile에서 읽어옴
     */
    @Test
    public void helloStaticCustom() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Hello Mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }

    /**
     * Uses string converter
     */
    @Test
    public void stringMessage() throws Exception {
        this.mockMvc.perform(get("/message")
                .content("hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void jsonMessage() throws Exception {
        EntityPerson person = new EntityPerson();
        person.setId(2019L);
        person.setName("keesun");

        String jsonString = objectMapper.writeValueAsString(person);

        this.mockMvc.perform(get("/jsonMessage")
                // contentType을 참조해서 converter 정함 : json
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                // jsonPath 사용할 수 있음
                .andExpect(jsonPath("$.id").value(2019L))
                .andExpect(jsonPath("$.name").value("keesun"));
    }

    @Test
    public void xmlMessage() throws Exception {
        EntityPerson person = new EntityPerson();
        person.setId(2019L);
        person.setName("keesun");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/jsonMessage")
                // contentType을 참조해서 converter 정함 : xml
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                // xpath 사용할 수 있음
                .andExpect(xpath("person/name").string("keesun"))
                .andExpect(xpath("person/id").string("2019"));
    }

}
