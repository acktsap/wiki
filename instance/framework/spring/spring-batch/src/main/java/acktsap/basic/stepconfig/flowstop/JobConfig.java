package acktsap.basic.stepconfig.flowstop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

/**
 *
 *  If the Step ends with ExitStatus FAILED, then the BatchStatus and ExitStatus of the Job are both FAILED.
 *
 *  Otherwise, the BatchStatus and ExitStatus of the Job are both COMPLETED.
 *
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Bean
    public Job endJob() {
        return this.jobBuilderFactory.get("endJob")
            .start(step1())
            .next(step2()).on("FAILED").end() // finish job successfully
            .from(step2()).on("*").to(step3())
            .end()
            .build();
    }

    @Bean
    public Job failJob() {
        return this.jobBuilderFactory.get("failJob")
            .start(step1())
            .next(step2()).on("FAILED").fail() // fail job
            .from(step2()).on("*").to(step3())
            .end()
            .build();
    }

    @Bean
    public Job stopJob() {
        Job job = this.jobBuilderFactory.get("stopJob")
            // stop on COMPLETED & run step3 on restart
            .start(step1()).on("COMPLETED").stopAndRestart(step3())
            .end()
            .build();

        // to re-trigger
        executorService.schedule(() -> {
            try {
                JobParameters jobParameters = new JobParametersBuilder()
                    .toJobParameters();
                jobLauncher.run(job, jobParameters);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }, 2000L, TimeUnit.MILLISECONDS);

        return job;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - step1%n", Thread.currentThread().getName(), jobName);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - step2%n", Thread.currentThread().getName(), jobName);

                contribution.setExitStatus(ExitStatus.FAILED);

                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - step3%n", Thread.currentThread().getName(), jobName);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
