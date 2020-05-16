package acktsap.sample.demowebmvc.handlermethod.arguments;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import acktsap.sample.demowebmvc.Event;

/**
 * {@link ModelAttribute}.
 * 
 * - 여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객체를 새로 만들 때 사용할 수 있다.
 * 
 * - 여러 곳? URI 패스, 요청 매개변수, 세션 등
 * 
 * 값을 바인딩 할 수 없는 경우에는?
 * 
 * - BindException 발생 400 에러
 * 
 * 바인딩 에러를 직접 다루고 싶은 경우
 * 
 * - BindingResult 타입의 아규먼트를 바로 오른쪽에 추가한다.
 * 
 * 바인딩 이후에 검증 작업을 추가로 하고 싶은 경우
 * 
 * - @Valid 또는 @Validated 애노테이션을 사용한다.
 */
@Controller
@RequestMapping("/modelattribute")
public class ModelAttributeController {

  @GetMapping("/plain")
  @ResponseBody
  public Event plain(@ModelAttribute Event event) {
    return event;
  }

  // @ModelAttribute : 생략가능
  @GetMapping("/missing")
  @ResponseBody
  public Event missing(Event event) {
    return event;
  }

  /**
   * BindingResult에 에러 값이 담겨 옴 처리는 정상적으로 되나 event에 해당하는 부분이 비어있음.
   */
  @GetMapping("bindingresult")
  @ResponseBody
  public Event bindingresult(Event event, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("====================");
      bindingResult.getAllErrors().forEach(e -> {
        System.out.println(e);
      });
    }
    return event;
  }

  /**
   * Valid check할 때 에러결과가 BindingResult에 담겨옴.
   */
  @GetMapping("valid")
  @ResponseBody
  public Event valid(@Valid Event event, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("====================");
      bindingResult.getAllErrors().forEach(e -> {
        System.out.println(e);
      });
    }
    return event;
  }

  /**
   * 스프링 MVC 핸들러 메소드 아규먼트에 사용할 수 있으며 validation group이라는 힌트를 사용할 수 있다.
   * 
   * - @Valid 애노테이션에는 그룹을 지정할 방법이 없다.
   * 
   * - @Validated는 스프링이 제공하는 애노테이션으로 그룹 클래스를 설정할 수 있다.
   */
  @GetMapping("validated")
  @ResponseBody
  public Event validated(@Validated(Event.Group1.class) Event event, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("====================");
      bindingResult.getAllErrors().forEach(e -> {
        System.out.println(e);
      });
    }
    return event;
  }

}
