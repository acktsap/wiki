package acktsap.sample.demowebmvc.handlermethod.argments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import acktsap.sample.webservlet.Event;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CommonModelAttributeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void getEvents() throws Exception {
    Event newEvent = new Event();
    newEvent.setName("Winter is coming.");
    newEvent.setLimit(10000);

    mockMvc.perform(get("/modelattribute2/list")
        .flashAttr("newEvent", newEvent))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("categories"))
        .andExpect(xpath("//p").nodeCount(2));
  }

}
