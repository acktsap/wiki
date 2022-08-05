package acktsap.demowebmvc.requestmapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HeaderParamsMappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFromHeader() throws Exception {
        mockMvc.perform(get("/header")
            .header(HttpHeaders.FROM, "localhost"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("fromHeader"));
    }

    @Test
    public void testNotFromHeader() throws Exception {
        mockMvc.perform(get("/header"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("notFromHeader"));
    }

    @Test
    public void testAuthorizationHeaderValue() throws Exception {
        mockMvc.perform(get("/header")
            .header(HttpHeaders.AUTHORIZATION, "111"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("authorizationHeaderValue"));
    }

    @Test
    public void testParams() throws Exception {
        mockMvc.perform(get("/params")
            .param("name", "acktsap"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("params"));
    }

    @Test
    public void testNotParams() throws Exception {
        mockMvc.perform(get("/params"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("notParams"));
    }

    @Test
    public void testSpecificParams() throws Exception {
        mockMvc.perform(get("/params")
            .param("name", "111"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string("specificParams"));
    }

}
