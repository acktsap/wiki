package acktsap.spring.kafka.testbroker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Collections;

@EmbeddedKafka(partitions = 1, ports = {9092})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setDefaultProperties(Collections.singletonMap("server.port", "4000"));
        springApplication.run(args);
    }
}
