package acktsap.scaling.twoparallelstep;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final int poolSize = Runtime.getRuntime().availableProcessors() * 2;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(poolSize);
        taskExecutor.setMaxPoolSize(poolSize);
        return taskExecutor;
    }

    @Bean
    public Job twoParallelFlowJob() {
        return this.jobBuilderFactory.get("parallelFlowJob")
            .start(splitFlow())
            .build()   // builds FlowJobBuilder instance
            .build();  // builds Job instance
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<Flow>("splitFlow")
            .start(
                new FlowBuilder<Flow>("startFlow")
                    .split(taskExecutor()) // set parallel executor
                    .add(flow1(), flow2()) // flow1, flow2 run in parallel
                    .build()
            )
            .next(
                new FlowBuilder<Flow>("nextFlow")
                    .split(taskExecutor())
                    .add(flow3(), flow4())
                    .build()
            )
            .build();
    }

    @Bean
    public Flow flow5() {
        return new FlowBuilder<Flow>("flow5")
            .start(step("step5", () -> System.out.printf("[%s] flow5 - step5%n", Thread.currentThread().getName())))
            .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<Flow>("flow1")
            .start(step("step1", () -> System.out.printf("[%s] flow1 - step1%n", Thread.currentThread().getName())))
            .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
            .start(step("step2", () -> System.out.printf("[%s] flow2 - step2%n", Thread.currentThread().getName())))
            .build();
    }

    @Bean
    public Flow flow3() {
        return new FlowBuilder<Flow>("flow3")
            .start(step("step3", () -> System.out.printf("[%s] flow3 - step3%n", Thread.currentThread().getName())))
            .build();
    }

    @Bean
    public Flow flow4() {
        return new FlowBuilder<Flow>("flow4")
            .start(step("step4", () -> System.out.printf("[%s] flow4 - step4%n", Thread.currentThread().getName())))
            .build();
    }

    private Step step(String name, Runnable runnable) {
        return this.stepBuilderFactory.get(name)
            .tasklet((contribution, chunkContext) -> {
                runnable.run();
                return RepeatStatus.FINISHED;
            }).build();
    }
}
