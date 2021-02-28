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
public class UriPatternControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testPathvariable() throws Exception {
        mockMvc.perform(get("/uripattern/pathvariable1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    public void testPathvariableWithOptionalWithValue() throws Exception {
        mockMvc.perform(get("/uripattern/pathvariable2/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    public void testPathvariableWithOptionalWithoutValue() throws Exception {
        mockMvc.perform(get("/uripattern/pathvariable2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(Integer.MAX_VALUE));
    }

    @Test
    public void testMatrixVariable() throws Exception {
        mockMvc.perform(get("/uripattern/matrixvariable1/1;name=acktsap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("acktsap"))
                .andExpect(jsonPath("limit").value(Integer.MIN_VALUE));
    }

    @Test
    public void testMatrixVariableWithMap() throws Exception {
        mockMvc.perform(get("/uripattern/matrixvariable2/1;name=acktsap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("acktsap"));
    }

}
