package acktsap.basic.scaling.parallelstep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.RequiredArgsConstructor;

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
    public Job parallelFlowJob() {
        return this.jobBuilderFactory.get("parallelFlowJob")
            .start(splitFlow()).on("FAILED").fail()
            .next(step("step4", () -> System.out.printf("[%s] step4%n", Thread.currentThread().getName())))
            .build()   // builds FlowJobBuilder instance
            .build();  // builds Job instance
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<Flow>("splitFlow")
            .split(taskExecutor()) // set parallel executor
            .add(flow1(), flow2()) // flow1, flow2 run in parallel
            .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<Flow>("flow1")
            .start(step("step1", () -> System.out.printf("[%s] flow1 - step1%n", Thread.currentThread().getName())))
            .next(step("step2", () -> System.out.printf("[%s] flow1 - step2%n", Thread.currentThread().getName())))
            .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
            .start(this.stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

                    int count = -999;
                    if (executionContext.containsKey("count")) {
                        // localhost:8080/job?name=parallelFlowJob 로 재시작 시
                        count = executionContext.getInt("count");
                    }

                    if (count == -999) {
                        // 값이 없을경우 값을 set
                        // localhost:8080/job?name=parallelFlowJob
                        // 이를 통해 재시작 시 이 block을 타지 않게 됨
                        executionContext.putInt("count", 10);
                        throw new IllegalStateException("count is not set in context");
                    }

                    executionContext.putInt("count", count + 10);

                    System.out.printf("[%s] flow2 - step3%n", Thread.currentThread().getName());

                    return RepeatStatus.FINISHED;
                }).build())
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
