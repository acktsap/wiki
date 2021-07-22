package acktsap.basic.jobconfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;

    @Bean
    public Job footballJob(Step playerLoad) {
        return this.jobBuilderFactory.get("footballJob")
            .start(playerLoad)
            .build();
    }

    @Bean
    public Job noRestartJob(Step playerLoad) {
        Job noRestartJob = this.jobBuilderFactory.get("noRestartJob")
            .preventRestart()
            .start(playerLoad)
            .build();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {
            try {
                jobLauncher.run(noRestartJob, new JobParameters());
            } catch (Exception e) {
                e.printStackTrace(System.out);
                executorService.shutdownNow();
            }
        }, 3000L, TimeUnit.MILLISECONDS);

        return noRestartJob;
    }

    @Bean
    public Job jobListenerJob(Step playerLoad) {
        return this.jobBuilderFactory.get("jobListenerJob")
            .listener(new JobExecutionListener() {
                @Override
                public void beforeJob(JobExecution jobExecution) {
                    System.out.printf("%s starts at %s (status: %s)%n", jobExecution.getJobInstance(), jobExecution.getStartTime().toInstant(),
                        jobExecution.getStatus());
                }

                @Override
                public void afterJob(JobExecution jobExecution) {
                    System.out.printf("%s finished at %s (status: %s)%n", jobExecution.getJobInstance(), jobExecution.getEndTime().toInstant(),
                        jobExecution.getStatus());
                }
            })
            .start(playerLoad)
            .build();
    }

    @Bean
    public Job jobParameterValidatorJob(Step playerLoad) {
        return this.jobBuilderFactory.get("jobParameterValidatorJob")
            .validator(new JobParametersValidator() {
                @Override
                public void validate(JobParameters parameters) throws JobParametersInvalidException {
                    String value = parameters.getString("kk");
                    // print twice.. why??
                    System.out.println("validate (kk: " + value + ")");
                }
            })
            .start(playerLoad)
            .build();
    }

    @Bean
    public Step playerLoad() {
        return this.stepBuilderFactory.get("playerLoad")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - %s%n", Thread.currentThread().getName(), jobName, "playerLoad");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
