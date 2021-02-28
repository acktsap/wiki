package acktsap.demowebmvc.requestmapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class HttpMappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloGet() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void helloPost() throws Exception {
        mockMvc.perform(post("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void helloPut() throws Exception {
        mockMvc.perform(put("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void helloDelete() throws Exception {
        mockMvc.perform(delete("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void hello2Get() throws Exception {
        mockMvc.perform(get("/hello2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello2"));
    }

    @Test
    public void hello2Put() throws Exception {
        mockMvc.perform(put("/hello2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello2"));
    }

    @Test
    public void shouldFailOnHello2Post() throws Exception {
        mockMvc.perform(post("/hello2"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void hiGet() throws Exception {
        mockMvc.perform(get("/hi"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hi"));
    }

}
