package acktsap.demowebmvc.handlermethod.argments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RequestParamsControllerTest {

    @Autowired
    MockMvc mockMvc;

    /* /requestparams/map?name=acktsap */
    @Test
    public void testPlain() throws Exception {
        mockMvc.perform(get("/requestparams/plain")
                .param("name", "acktsap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("acktsap"));
    }

    @Test
    public void testPlainMissingAnnotation() throws Exception {
        mockMvc.perform(get("/requestparams/missing")
                .param("name", "acktsap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("acktsap"));
    }

    @Test
    public void testMap() throws Exception {
        mockMvc.perform(get("/requestparams/map")
                .param("name", "acktsap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("acktsap"));
    }

}
