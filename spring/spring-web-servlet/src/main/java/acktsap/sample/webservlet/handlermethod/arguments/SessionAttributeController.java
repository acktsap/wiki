package acktsap.sample.webservlet.handlermethod.arguments;

import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * SessionAttribute : HTTP 세션에 들어있는 값 참조할 때 사용
 * 
 * - HttpSession을 사용할 때 비해 타입 컨버전을 자동으로 지원하기 때문에 조금 편리함.
 * 
 * - HTTP 세션에 데이터를 넣고 빼고 싶은 경우에는 HttpSession을 사용할 것.
 * 
 * SessionAttributes와는 다르다.
 * 
 * - SessionAttributes는 해당 컨트롤러 내에서만 동작.
 * 
 * - 즉, 해당 컨트롤러 안에서 다루는 특정 모델 객체를 세션에 넣고 공유할 때 사용.
 * 
 * - SessionAttribute는 컨트롤러 밖(인터셉터 또는 필터 등)에서 만들어 준 세션 데이터에 접근할 때 사용한다.
 */
@Controller
@RequestMapping("/session")
public class SessionAttributeController {

  // open localhost:8080/session/visit1
  // open localhost:8080/session/visit2
  // 시간이 동일하게 나옴!!!

  // @SessionAttribute 바로 사용
  @GetMapping("/visit1")
  @ResponseBody
  public String visitTime(@SessionAttribute LocalDateTime visitTime) {
    System.out.println(visitTime);
    return visitTime.toString();
  }

  // HttpSession 바로 사용
  @GetMapping("/visit2")
  @ResponseBody
  public String visitTime(Model model, HttpSession httpSession) {
    LocalDateTime visitTime = (LocalDateTime) httpSession.getAttribute("visitTime");
    System.out.println(visitTime);
    return visitTime.toString();
  }

}
