package acktsap.sample.webservlet.webcontext;

import org.springframework.stereotype.Service;

/**
 * AppConfig에 의해서 Scan됨
 */
@Service
public class HelloService {

  public String getName() {
    return "Hello Servlet";
  }

}
