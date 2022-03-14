package acktsap.basic.stepconfig.flow.flowsplit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

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

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public Job sampleJob1() {
        return this.jobBuilderFactory.get("sampleJob1")
            .start(flow1())
            .split(taskExecutor()).add(flow2(), flow3()) // flow1, flow2, flow3 run in async executor
            .next(makeStep(("step5"))) // step 5 run in main
            .end()
            .build();
    }

    @Bean
    public Job sampleJob2() {
        return this.jobBuilderFactory.get("sampleJob2")
            .start(splitFlow())
            .next(makeStep(("step5"))) // step 5 run in main
            .end()
            .build();
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<Flow>("splitFlow")
            .split(taskExecutor()).add(flow1(), flow2(), flow3()) // flow1, flow2, flow3 run in async executor
            .build();
    }

    @Bean
    public Flow flow1() {
        FlowBuilder<Flow> next = new FlowBuilder<Flow>("flow1")
            .start(makeStep("step1"))
            .next(makeStep("step2"));
        return next
            .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
            .start(makeStep("step3"))
            .build();
    }

    @Bean
    public Flow flow3() {
        return new FlowBuilder<SimpleFlow>("flow3")
            .start(makeStep("step4"))
            .build();
    }

    protected Step makeStep(String name) {
        return stepBuilderFactory.get(name)
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                String stepName = chunkContext.getStepContext().getStepName();
                System.out.printf("[%s] %s - %s%n", Thread.currentThread().getName(), jobName, stepName);
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
