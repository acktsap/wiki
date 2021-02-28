package acktsap.demowebmvc.handlermethod.argments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import acktsap.webservlet.Event;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RedirectionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testRedirectAttribute() throws Exception {
        mockMvc.perform(get("/redirection/attribute"))
                .andExpect(redirectedUrl("/redirection/list?name=aaa&limit=111")) // present to uri
                .andExpect(model().attribute("name", "aaa"))
                .andExpect(model().attribute("limit", "111"));
    }

    @Test
    public void testRedirectFlashAttribute() throws Exception {
        mockMvc.perform(get("/redirection/flashattribute"))
                .andExpect(redirectedUrl("/redirection/list")) // hidden to uri
                .andExpect(flash().attribute("name", "bbb"))
                .andExpect(flash().attribute("limit", "222"));
    }

    @Test
    public void getEvents() throws Exception {
        Event flashEvent = new Event();
        flashEvent.setName("Winter is coming.");
        flashEvent.setLimit(10000);

        mockMvc.perform(get("/redirection/list")
                .sessionAttr("visitTime", LocalDateTime.now()) // binded to @SessionAttribute
                .flashAttr("flashEvent", flashEvent)) // binded to @ModelAttribute("flashEvent")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

}
