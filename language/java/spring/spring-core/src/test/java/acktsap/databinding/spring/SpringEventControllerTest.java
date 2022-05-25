/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.databinding.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SpringEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/event/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("1"));
    }

}
