package acktsap.basic.scaling.parallelstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    @RestController
    public static class Test {
        @GetMapping("/")
        String test() {
            return "test";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
