package acktsap.jobconfig.db_prefix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class Application {

    // trigger job
    // localhost:8080/job
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        Properties properties = new Properties();
        properties.put("spring.batch.job.enabled", false);  // without this, all job will run
        properties.put("spring.batch.jdbc.table-prefix", "TEST_BAT_");
        application.setDefaultProperties(properties);
        application.run(args);

        System.exit(0);
    }
}
