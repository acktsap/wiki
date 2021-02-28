package acktsap.demowebmvc.requestmapping;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
public class HeadOptionsControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Head : GET 요청과 동일하지만 응답 본문을 받아오지 않고 응답 헤더만 받아온다.
     */
    @Test
    public void testHead() throws Exception {
        mockMvc.perform(head("/headoptions"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOptions() throws Exception {
        mockMvc.perform(options("/headoptions"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(header().stringValues(HttpHeaders.ALLOW, hasItems(
                        containsString("GET"),
                        containsString("POST"),
                        containsString("HEAD"),
                        containsString("OPTIONS"))));
    }

}
