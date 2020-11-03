package acktsap.sample.webservlet.requestmapping;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HeaderParamsMappingController {

  @GetMapping(value = "/header", headers = HttpHeaders.FROM)
  @ResponseBody
  public String fromHeader() {
    return "fromHeader";
  }

  @GetMapping(value = "/header", headers = "!" + HttpHeaders.FROM)
  @ResponseBody
  public String notFromHeader() {
    return "notFromHeader";
  }

  @GetMapping(value = "/header", headers = HttpHeaders.AUTHORIZATION + "=" + "111")
  @ResponseBody
  public String authorizationHeaderValue() {
    return "authorizationHeaderValue";
  }

  @GetMapping(value = "/params", params = "name")
  @ResponseBody
  public String params() {
    return "params";
  }

  @GetMapping(value = "/params", params = "!name")
  @ResponseBody
  public String notParams() {
    return "notParams";
  }

  @GetMapping(value = "/params", params = "name=111")
  @ResponseBody
  public String specificParams() {
    return "specificParams";
  }

}
