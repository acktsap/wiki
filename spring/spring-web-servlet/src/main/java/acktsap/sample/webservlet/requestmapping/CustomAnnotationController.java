package acktsap.sample.webservlet.requestmapping;

import org.springframework.stereotype.Controller;

@Controller
public class CustomAnnotationController {

  @GetCustomMapping
  public String custom() {
    return "custom";
  }
}
