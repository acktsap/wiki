package acktsap.sample.mvchelloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

  @Autowired
  EventService eventService;

  // @GetMapping : MetaAnnotation for
  // @RequestMapping(value="/events", method = RequestMethod.GET)
  @GetMapping("/events")
  public String events(Model model) {
    System.out.println("GET - /events");
    model.addAttribute("events", eventService.getEvents());

    // find view in 'resources/templates' directory
    return "events/list";
  }

}
