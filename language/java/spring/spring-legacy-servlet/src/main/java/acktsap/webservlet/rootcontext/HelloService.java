package acktsap.webservlet.rootcontext;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getName() {
        return "Hello Servlet";
    }

}
