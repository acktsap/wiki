package acktsap.basic.scaling.multithreadstep;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
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
    // private final int poolSize = 3;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(poolSize);
        taskExecutor.setMaxPoolSize(poolSize);
        return taskExecutor;
    }

    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
        return this.jobBuilderFactory.get("sampleJob")
            .start(sampleStep)
            .build();
    }

    @Bean
    public Step sampleStep(TaskExecutor taskExecutor) {
        return this.stepBuilderFactory.get("sampleStep")
            .<List<Integer>, List<String>>chunk(3)
            .reader(new ItemReader<>() {
                private int cursor = 1;

                // 동시성 제어를 위해 synchronized
                @Override
                public synchronized List<Integer> read() throws
                    Exception,
                    UnexpectedInputException,
                    ParseException,
                    NonTransientResourceException {
                    // repeat until return null

                    if (cursor > 8) {
                        return null;
                    }

                    List<Integer> datas = IntStream.range(1, 4).boxed()
                        .map(v -> v * cursor)
                        .collect(toList());
                    ++cursor;

                    System.out.printf("[%s %s] Read %s%n", Thread.currentThread().getName(), getCallBackMethod(), datas);

                    return datas;
                }
            })
            .processor((ItemProcessor<? super List<Integer>, ? extends List<String>>)datas -> {
                List<String> processed = datas.stream()
                    .map(v -> String.format("'%d'", v))
                    .collect(toList());
                System.out.printf("[%s %s] Process %s to %s%n", Thread.currentThread().getName(), getCallBackMethod(), datas, processed);
                return processed;
            })
            .writer(data -> {
                List<String> flattened = data.stream()
                    .flatMap(Collection::stream)
                    .collect(toList());
                System.out.printf("[%s %s] Write %s%n", Thread.currentThread().getName(), getCallBackMethod(), flattened);
            })
            .taskExecutor(taskExecutor) // chunk 단위로 (tx 단위) thread에 맡김
            /*
                throttleLimit : default : 4, 이 사이즈만큼 pool이 동시에 실행됨

                동작 (이 과정이 tx 한개임)

                1. throttleLimit 만큼 pool에 task를 던짐.
                2. task를 받은 pool은 reader에서 chunk size만큼 읽음 (또는 null이 리턴될때까지)
                3. 각각 읽은 것들을 가지고 processor -> writer 탐
             */
            .throttleLimit(poolSize)
            /*
                이걸로 하면 결과가 다름.
                poolSize로 하면 여러개가 동시에 실행되지만 3으로 하면 max 3개가 실행되서
                3개 thread가 각각 3, 3, 2개 처리하게 됨.

                위에 설정으로 하면 poolSize가 8보다 큰 경우 각각 1개씩 처리하는 방식으로 됨
                왜냐하면 위에서는 chunk size만큼 읽기 전에 null을 리턴받기 때문

                반대로 throttleLimit == 1로 하면 사실상 single thread의 효과가 남
             */
            // .throttleLimit(3)
            .build();
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%s", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }

}
