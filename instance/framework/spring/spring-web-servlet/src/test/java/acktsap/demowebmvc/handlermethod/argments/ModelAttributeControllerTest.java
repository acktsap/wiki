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
public class ModelAttributeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testPlain() throws Exception {
        mockMvc.perform(get("/modelattribute/plain")
            .param("name", "acktsap")
            .param("limit", "30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value("acktsap"))
            .andExpect(jsonPath("limit").value("30"));
    }

    @Test
    public void testMissing() throws Exception {
        mockMvc.perform(get("/modelattribute/missing")
            .param("name", "acktsap")
            .param("limit", "30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value("acktsap"))
            .andExpect(jsonPath("limit").value("30"));
    }

    @Test
    public void testBindingError() throws Exception {
        mockMvc.perform(get("/modelattribute/plain")
            .param("name", "acktsap")
            .param("limit", "aaaa")) // error
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testBindingResult() throws Exception {
        mockMvc.perform(get("/modelattribute/bindingresult")
            .param("name", "acktsap")
            .param("limit", "aaa")) // must be number. but bindingresult holds error
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value("acktsap"))
            .andExpect(jsonPath("limit").doesNotExist()); // so, null
    }

    @Test
    public void testValid() throws Exception {
        mockMvc.perform(get("/modelattribute/valid")
            .param("name", "acktsap")
            .param("limit", "-10")) // error on Min(0)
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value("acktsap"))
            .andExpect(jsonPath("limit").value("-10")); // inserted since binding result holds error
    }

    @Test
    public void testValidated() throws Exception {
        mockMvc.perform(get("/modelattribute/valid")
            // .param("name", "acktsap") // missing
            .param("limit", "-10")) // valid since group1 is used
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").doesNotExist()) // so, null
            .andExpect(jsonPath("limit").value("-10"));
    }

}
