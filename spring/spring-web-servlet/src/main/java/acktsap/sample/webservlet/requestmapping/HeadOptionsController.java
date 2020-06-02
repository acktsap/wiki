package acktsap.sample.webservlet.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 우리가 구현하지 않아도 Spring Web MVC에서 자동으로 처리하는 HTTP Method
 * 
 * HEAD
 *
 * - GET 요청과 동일하지만 응답 본문을 받아오지 않고 응답 헤더만 받아온다.
 *
 * Options
 *
 * - 사용할 수 있는 HTTP Method 제공
 *
 * - 서버 또는 특정 리소스가 제공하는 기능을 확인할 수 있다.
 *
 * - 서버는 Allow 응답 헤더에 사용할 수 있는 HTTP Method 목록을 제공해야 한다.
 *
 * See also : https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
 */
@Controller
@RequestMapping("/headoptions")
public class HeadOptionsController {

  @GetMapping
  @ResponseBody
  public String get() {
    return "get";
  }

  @PostMapping
  @ResponseBody
  public String post() {
    return "post";
  }

}
