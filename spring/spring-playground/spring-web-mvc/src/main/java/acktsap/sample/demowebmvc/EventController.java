package acktsap.sample.demowebmvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("event")
public class EventController {

  @GetMapping("/events/form/name")
  public String eventsFormName(Model model) {
    throw new EventException();
    //
    // model.addAttribute("event", new Event());
    // return "/events/form-name";
  }

  @PostMapping("/events/form/name")
  public String eventsFormNameSubmit(@Validated @ModelAttribute Event event,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/events/form-name";
    }


    return "redirect:/events/form/limit";
  }

  @GetMapping("/events/form/limit")
  public String eventsFormLimit(@ModelAttribute Event event, Model model) {
    model.addAttribute("event", event);
    return "/events/form-limit";
  }

  @PostMapping("/events/form/limit")
  public String eventsFormLimitSubmit(@Validated @ModelAttribute Event event,
      BindingResult bindingResult,
      SessionStatus sessionStatus,
      RedirectAttributes attributes) {
    if (bindingResult.hasErrors()) {
      return "/events/form-limit";
    }
    sessionStatus.setComplete();
    attributes.addFlashAttribute("newEvent", event);
    return "redirect:/events/list";
  }

  @GetMapping("/events/list")
  public String getEvents(Model model,
      @SessionAttribute LocalDateTime visitTime) {
    System.out.println(visitTime);

    Event spring = new Event();
    spring.setName("spring");
    spring.setLimit(10);

    Event newEvent = (Event) model.asMap().get("newEvent");

    List<Event> eventList = new ArrayList<>();
    eventList.add(spring);
    eventList.add(newEvent);

    model.addAttribute(eventList);

    return "/events/list";
  }

}
