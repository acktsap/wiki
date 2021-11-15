package acktsap.webservlet.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Run and open "localhost:8080/events" in a browser
 */
@SpringBootApplication
public class WebServletHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServletHelloWorldApplication.class, args);
    }

}

