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
public class UrlMappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testRfd() throws Exception {
        mockMvc.perform(get("/uri/rfd/sfaf"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void testExactWord() throws Exception {
        mockMvc.perform(get("/uri/lim"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("exactWord"));
    }

    @Test
    public void testSingleWord() throws Exception {
        mockMvc.perform(get("/uri/a"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("singleWord"));
    }

    @Test
    public void testMultiWord() throws Exception {
        mockMvc.perform(get("/uri/multiword/a1a1a1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("multiWord"));
    }

    @Test
    public void testMultiPath() throws Exception {
        mockMvc.perform(get("/uri/multipath/aaa/bbb/ccc"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("multiPath"));
    }

    @Test
    public void testRegex() throws Exception {
        mockMvc.perform(get("/uri/regex/abc"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("regex"));
    }

}
