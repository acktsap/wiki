package acktsap.validation;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ValidatorApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ValidatorApplication.class)
            .web(WebApplicationType.NONE)
            .build()
            .run(args);
    }

}
