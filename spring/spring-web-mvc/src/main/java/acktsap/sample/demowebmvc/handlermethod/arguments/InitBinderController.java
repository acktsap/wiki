package acktsap.sample.demowebmvc.handlermethod.arguments;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import acktsap.sample.demowebmvc.Event;

/**
 * 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
 * 
 * 바인딩 설정
 * 
 * - webDataBinder.setDisallowedFields();
 * 
 * 포매터 설정
 * 
 * - webDataBinder.addCustomFormatter();
 * 
 * Validator 설정
 * 
 * - webDataBinder.addValidators();
 * 
 * 특정 모델 객체에만 바인딩 또는 Validator 설정을 적용하고 싶은 경우
 * 
 * - @InitBinder("event")
 *
 */
@Controller
@RequestMapping("/initbinder")
public class InitBinderController {

  class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
      return Event.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
      Event event = (Event) target;
      if (event.getName().equals("bad")) {
        errors.rejectValue("name", "wrongValue", "the value is not allowed");
      }
    }
  }

  @InitBinder("event")
  public void initEventBinder(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id"); // not allow id
    webDataBinder.addValidators(new EventValidator());
  }

  @GetMapping("/events")
  @ResponseBody
  public Event getEvent(@RequestBody @ModelAttribute Event event) {
    System.out.println("tset: " + event);
    return event;
  }

}
