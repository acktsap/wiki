package acktsap.demowebmvc.requestmapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomAnnotationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCustom() throws Exception {
        mockMvc.perform(get("/custom"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("custom"));
    }

}
