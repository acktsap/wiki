package acktsap.runningjob.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    // TODO: https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#commandLineJobRunner
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
