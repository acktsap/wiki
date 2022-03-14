package acktsap.runningjob.web;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final JobLauncher jobLauncher;

    private final Job job;

    // should be POST. But it's just an example
    @GetMapping("/job")
    public void triggerJob() throws Exception {
        System.out.printf("[%s] job trigger request%n", Thread.currentThread().getName());
        // pass trigger time to distinguish job instance
        JobParameters jobParameters = new JobParametersBuilder()
            .addDate("triggerTime", Date.from(Instant.now()))
            .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
