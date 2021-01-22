package acktsap.demowebmvc.handlermethod.argments;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SessionAttributesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void eventForm() throws Exception {
        MockHttpServletRequest mockRequest = mockMvc.perform(get("/session/events/name"))
                .andDo(print())
                .andExpect(view().name("/events/form-name"))
                .andExpect(model().attributeExists("event"))
                .andExpect(request().sessionAttribute("event", notNullValue()))
                .andReturn().getRequest();
        Object event = mockRequest.getSession().getAttribute("event");
        System.out.println(event);
    }

}
