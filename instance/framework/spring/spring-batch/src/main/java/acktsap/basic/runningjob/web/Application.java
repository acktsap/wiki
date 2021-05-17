package acktsap.basic.runningjob.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    // trigger job: localhost:8080/job
    public static void main(String[] args) {
        // without this, all job will run
        System.setProperty("spring.batch.job.enabled", "false");
        SpringApplication.run(Application.class, args);
    }
}
