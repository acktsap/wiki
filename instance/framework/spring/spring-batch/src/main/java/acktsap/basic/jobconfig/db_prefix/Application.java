package acktsap.basic.jobconfig.db_prefix;

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
        properties.put("spring.batch.job.enabled", true);  // without this, all job will run
        properties.put("spring.batch.jdbc.table-prefix", "TEST_BAT_");
        application.setDefaultProperties(properties);

        application.run(args);
    }
}
