package acktsap.sample.demowebmvc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {EventController.class, EventApi.class})
public class BaseController {

  @ExceptionHandler({EventException.class, RuntimeException.class})
  public String eventErrorHandler(RuntimeException ex, Model model) {
    model.addAttribute("message", "runtime error");
    return "error";
  }

  @InitBinder("event")
  public void initEventBinder(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id");
    webDataBinder.addValidators(new EventValidator());
  }

  @ModelAttribute
  public void categories(Model model) {
    List<String> categories = new ArrayList<>();
    categories.add("study");
    categories.add("seminar");
    categories.add("hobby");
    categories.add("social");

    model.addAttribute("categories", categories);
  }
}
