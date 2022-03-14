package acktsap.basic.runningjob.web;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    // trigger job
    // localhost:8080/job
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);

        Properties properties = new Properties();
        properties.put("spring.batch.job.enabled", false);  // without this, all job will run
        application.setDefaultProperties(properties);

        application.run(args);
    }
}
