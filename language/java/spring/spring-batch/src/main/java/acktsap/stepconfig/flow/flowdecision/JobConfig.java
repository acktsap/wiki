package acktsap.stepconfig.flow.flowdecision;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Bean
    public Job deciderJob() {
        Job job = this.jobBuilderFactory.get("deciderJob")
            .start(step1())
            .next(decider()).on("FAILED").to(step2())
            .from(decider()).on("COMPLETED").to(step3())
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
    public Job stopDeciderJob() {
        JobExecutionDecider jobExecutionDecider = (j, s) -> FlowExecutionStatus.STOPPED;
        return this.jobBuilderFactory.get("stopDeciderJob")
            .start(step1())
            .next(jobExecutionDecider).on("FAILED").to(step2())
            .from(jobExecutionDecider).on("*").to(step3()) // never called. just stop the job
            .end()
            .build();
    }

    @Bean
    public Job deciderAfterFailStepJob() {
        return this.jobBuilderFactory.get("deciderAfterFailStepJob")
            .start(failStep())
            // decider never called
            .next(decider()).on("FAILED").to(step2())
            .from(decider()).on("*").to(step3())
            .end()
            .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new JobExecutionDecider() {
            boolean isFirst = true;

            @Override
            public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
                String jobName = jobExecution.getJobInstance().getJobName();
                System.out.printf("[%s] %s - decider%n", Thread.currentThread().getName(), jobName);
                if (isFirst) {
                    isFirst = false;
                    return new FlowExecutionStatus("FAILED");
                }

                return new FlowExecutionStatus("COMPLETED");
            }
        };
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
    public Step failStep() {
        return stepBuilderFactory.get("failStep")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - failStep%n", Thread.currentThread().getName(), jobName);
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
