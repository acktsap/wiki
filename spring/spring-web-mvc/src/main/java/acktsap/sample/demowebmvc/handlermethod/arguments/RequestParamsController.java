package acktsap.sample.demowebmvc.handlermethod.arguments;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import acktsap.sample.demowebmvc.Event;

@Controller
@RequestMapping("/requestparams")
public class RequestParamsController {

  @GetMapping("/plain")
  @ResponseBody
  public Event plain(@RequestParam String name) {
    Event event = new Event();
    event.setName(name);
    return event;
  }

  // @RequestParam : 생략가능
  @GetMapping("/missing")
  @ResponseBody
  public Event missing(String name) {
    Event event = new Event();
    event.setName(name);
    return event;
  }

  @GetMapping("/map")
  @ResponseBody
  public Event map(@RequestParam Map<String, String> map) {
    Event event = new Event();
    event.setName(map.get("name"));
    return event;
  }

}
