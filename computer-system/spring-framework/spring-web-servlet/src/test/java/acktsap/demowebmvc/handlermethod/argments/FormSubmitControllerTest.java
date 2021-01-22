package acktsap.demowebmvc.handlermethod.argments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FormSubmitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFormPost() throws Exception {
        mockMvc.perform(post("/form")
                .param("name", "acktsap")
                .param("limit", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("acktsap"))
                .andExpect(jsonPath("limit").value("20"));
    }

    @Test
    public void testFormView() throws Exception {
        mockMvc.perform(get("/form"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("/events/form"))
                .andExpect(model().attributeExists("event"));
    }

    @Test
    public void testFormViewError() throws Exception {
        mockMvc.perform(post("/form")
                .param("name", "acktsap")
                .param("limit", "-10")) // wrong, validated에서 걸림
                .andExpect(status().isOk())
                .andExpect(model().hasErrors()); // model has error
    }

}
