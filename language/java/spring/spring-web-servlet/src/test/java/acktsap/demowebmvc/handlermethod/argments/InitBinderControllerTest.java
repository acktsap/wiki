package acktsap.demowebmvc.handlermethod.argments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import acktsap.webservlet.Event;

@RunWith(SpringRunner.class)
@WebMvcTest
public class InitBinderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetEventWithIdPresent() throws Exception {
        mockMvc.perform(get("/initbinder/events")
            .param("id", "333")
            .param("name", "acktsap"))
            .andDo(print())
            .andExpect(jsonPath("id").doesNotExist())
            .andExpect(jsonPath("name").value("acktsap"));
    }

    @Test
    public void testGetEventWithName() throws Exception {
        Event event = new Event();
        event.setId(333);
        event.setName("bad");

        mockMvc.perform(get("/initbinder/events")
            .param("id", "333")
            .param("name", "bad"))
            .andExpect(status().isBadRequest());
    }

}
