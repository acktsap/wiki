package acktsap.basic.scaling.partitioning;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    public Job job() {
        return this.jobBuilderFactory.get("job")
            .start(step1Manager())
            .build();
    }

    @Bean
    public Step step1Manager() {
        return this.stepBuilderFactory.get("step1:manager")
            .partitioner("step1", partitioner())
            // PartitionHandler 없으면 내부적으로 TaskExecutorPartitionHandler 만들어서 gridSize, step, taskExecutor 할당
            // but 명시적으로 하는게 좋음
            .partitionHandler(partitionHandler())
            .build();
    }

    // Partitioner : generate execution contexts as input parameters for new step executions only
    @Bean
    public Partitioner partitioner() {
        return gridSize -> {
            Map<String, ExecutionContext> map = new HashMap<>();
            for (int i = 0; i < gridSize; ++i) {
                ExecutionContext context = new ExecutionContext();
                context.put("target", i);

                // key : partition name
                // value : context value for the partition
                map.put("partition" + i, context);
            }
            return map;
        };
    }

    // PartitionHandler : controls the execution of a partitioned StepExecution
    // BATCH_STEP_EXECUTION in the meta db ensures that each partitioned step is executed once
    @Bean
    public PartitionHandler partitionHandler() {
        // TaskExecutorPartitionHandler : executes Step instances locally in separate threads
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setTaskExecutor(taskExecutor());
        partitionHandler.setGridSize(5);
        partitionHandler.setStep(step1());
        return partitionHandler;
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
            .tasklet(tasklet(null))
            .build();
    }

    @Bean
    @StepScope // to use lazy binding of parameter
    public Tasklet tasklet(@Value("#{stepExecutionContext['target']}") Integer target) {
        return (contribution, chunkContext) -> {
            // use 'target' in stepExecutionContext
            System.out.printf("[%s %s] step1 - process %d%n", Thread.currentThread().getName(), getCallBackMethod(), target);
            return RepeatStatus.FINISHED;
        };
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%s", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }

}
