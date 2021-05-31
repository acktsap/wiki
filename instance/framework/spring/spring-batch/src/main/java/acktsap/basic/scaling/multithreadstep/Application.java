package acktsap.basic.scaling.multithreadstep;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    @RestController
    public static class Test {
        @Autowired
        private List<Job> jobs;

        @Autowired
        private JobLauncher jobLauncher;

        /*
          localhost:8080/job?name=sampleJob
         */
        @GetMapping("/job")
        Object job(String name) throws Exception {
            Job job = jobs.stream()
                .filter(j -> j.getName().equals(name))
                .findFirst()
                .orElseThrow();
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            jobLauncher.run(job, jobParameters);
            return "success";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
