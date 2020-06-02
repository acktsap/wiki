package acktsap.sample.webservlet.exceptionhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import acktsap.sample.webservlet.Event;

/**
 * 특정 예외가 발생한 요청을 처리하는 핸들러 정의
 * 
 * - 지원하는 메소드 아규먼트 (해당 예외 객체, 핸들러 객체, ...)
 * 
 * - 지원하는 리턴 값
 * 
 * - REST API의 경우 응답 본문에 에러에 대한 정보를 담아주고, 상태 코드를 설정하려면 ResponseEntity를 주로 사용한다.
 */
@Controller
@RequestMapping("/exception")
public class CustomExceptionHandlerController {

  @ExceptionHandler(CustomException.class)
  @ResponseBody
  public String handleCustomException(CustomException ex) {
    return "custom";
  }

  @ExceptionHandler(EntityException.class)
  public ResponseEntity<Event> handleruntimeException(EntityException ex) {
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/custom")
  public String custom() {
    throw new CustomException();
  }

  @GetMapping("/entity")
  public String entity() {
    throw new EntityException();
  }

  @GetMapping("/global")
  public String global() {
    throw new GlobalException();
  }

}
