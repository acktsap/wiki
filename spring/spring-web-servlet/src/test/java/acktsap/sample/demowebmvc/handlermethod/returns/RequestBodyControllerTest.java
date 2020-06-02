package acktsap.sample.demowebmvc.handlermethod.returns;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import acktsap.sample.webservlet.Event;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RequestBodyControllerTest {

  @Autowired
  ObjectMapper objectMapper; // spring boot에서는 기본적으로 가져옴

  @Autowired
  MockMvc mockMvc;

  @Test
  public void createEvent() throws Exception {
    Event event = new Event();
    event.setName("keesun");
    event.setLimit(-20);

    String json = objectMapper.writeValueAsString(event);

    mockMvc.perform(post("/requestbody")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("keesun"))
        .andExpect(jsonPath("limit").value(-20));
  }

  @Test
  public void createEvent2() throws Exception {
    Event event = new Event();
    event.setName("keesun");
    event.setLimit(20);

    String json = objectMapper.writeValueAsString(event);

    mockMvc.perform(post("/requestbody/httpentity")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("keesun"))
        .andExpect(jsonPath("limit").value(20));
  }

}
