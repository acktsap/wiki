package acktsap.sample.demowebmvc.handlermethod.arguments;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import acktsap.sample.demowebmvc.Event;

/**
 * Run server and go to localhost:8080/form
 * 
 * See also {@link ThymeleafProperties} for default view location.
 */
@Controller
@RequestMapping("/form")
public class FormSubmitController {

  @GetMapping
  public String getEvents(Model model) {
    model.addAttribute("event", new Event());
    return "/events/form";
  }

  // get from html <form> tag
  @PostMapping
  public String createEvent(@Validated(Event.Common.class) @ModelAttribute Event event,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.err.println(bindingResult);
      // model이 error 담고 있음
      // 이를 thymeleaf에서 보여줌
      return "/events/form";
    }

    // save to database

    // resubmit을 방지하기 위해서 list 보여주는 페이지로 redirection함
    return "redirect:/form/list";
  }

  // open "localhost:8080/form/list"
  @GetMapping("/list")
  public String listEvents(Model model) {
    // load from database, this is a mock
    Event event = new Event();
    event.setName("spring");
    event.setLimit(10);

    List<Event> eventList = new ArrayList<>();
    eventList.add(event);

    // no name -> 이름 그대로 attribute 지정됨, 이 경우 eventList
    model.addAttribute(eventList);

    // list view 보여줌
    return "/events/list";
  }

}
