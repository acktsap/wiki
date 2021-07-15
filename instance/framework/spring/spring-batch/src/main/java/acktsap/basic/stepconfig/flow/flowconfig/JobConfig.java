package acktsap.basic.stepconfig.flow.flowconfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Bean
    public Job sequentialFlowJob() {
        return this.jobBuilderFactory.get("sequentialFlowJob")
            .start(step1())
            .next(step2())
            .next(step3())
            .build();
    }

    // "*" matches zero or more characters
    // "?" matches exactly one character
    // eg. c*t" matches "cat" and "count", while "c?t" matches "cat" but not "count
    // the framework orders transitions most specific to least specific
    @Bean
    public Job conditionalFlowJob() {
        Job job = this.jobBuilderFactory.get("conditionalFlowJob")
            .start(stepA()).on("COMPLETED").end() // success job
            .from(stepA()).on("FAILED").fail() // fail job
            .from(stepA()).on("HELL").to(stepB()) // success job
            .from(stepA()).on("*").to(stepC()) // success job
            .end()
            .build();

        // to re-trigger
        executorService.scheduleAtFixedRate(new Runnable() {
            private int count = 1;

            @Override
            public void run() {
                if (count >= 4) {
                    executorService.shutdownNow();
                    return;
                }

                try {
                    ++count;
                    JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters();
                    jobLauncher.run(job, jobParameters);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }, 3000L, 2000L, TimeUnit.MILLISECONDS);

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

    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("stepA")
            .tasklet(new Tasklet() {
                private int count = 1;

                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
                    String jobName = chunkContext.getStepContext().getJobName();
                    System.out.printf("[%s] %s - stepA%n", Thread.currentThread().getName(), jobName);

                    ExitStatus exitStatus;
                    if (count == 1) {
                        exitStatus = ExitStatus.COMPLETED;
                    } else if (count == 2) {
                        exitStatus = ExitStatus.FAILED;
                    } else if (count == 3) {
                        exitStatus = new ExitStatus("HELL");
                    } else {
                        exitStatus = new ExitStatus("hello~");
                    }
                    ++count;

                    System.out.printf("[%s] exit status: %s%n", Thread.currentThread().getName(), exitStatus);
                    contribution.setExitStatus(exitStatus);

                    return RepeatStatus.FINISHED;
                }
            })
            .build();
    }

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("stepB")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - stepB%n", Thread.currentThread().getName(), jobName);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step stepC() {
        return stepBuilderFactory.get("stepC")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - stepC%n", Thread.currentThread().getName(), jobName);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
