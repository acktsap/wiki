package acktsap.demowebmvc.requestmapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MediaMappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testMediaJson() throws Exception {
        mockMvc.perform(get("/media/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("mediaJson"));
    }

    @Test
    public void testMediaJsonUtf8() throws Exception {
        mockMvc.perform(get("/media/json")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("mediaJson"));
    }

    @Test
    public void testMediaTextJson() throws Exception {
        mockMvc.perform(get("/media/textjson")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("mediaTextJson"));
    }

    /**
     * text만 리턴하는데 json요청해서 튕김 : 406
     */
    @Test
    public void shouldFailOnJsonAccept() throws Exception {
        mockMvc.perform(get("/media/textjson")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotAcceptable()); // 406
    }

    /**
     * TODO
     *
     * json 아닌 것만 받는데 json요청해서 튕김 : 406
     */
    @Test
    public void shouldFailOnNotJsonAccept() throws Exception {
        mockMvc.perform(get("/media/notjson")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotAcceptable()); // 406
    }

}
