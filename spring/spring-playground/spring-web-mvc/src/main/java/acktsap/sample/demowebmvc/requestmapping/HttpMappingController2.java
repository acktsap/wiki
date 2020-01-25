package acktsap.sample.demowebmvc.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 이 클래스 안의 전체가 GET으로 mapping됨
 */
@Controller
@RequestMapping(method = RequestMethod.GET)
public class HttpMappingController2 {

  @RequestMapping("/hi")
  @ResponseBody
  public String hi() {
    return "hi";
  }
}
