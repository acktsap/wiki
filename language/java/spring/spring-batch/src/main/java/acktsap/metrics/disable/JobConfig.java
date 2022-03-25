package acktsap.metrics.disable;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job sampleJob() {
        return this.jobBuilderFactory.get("sampleJob")
            .start(sampleStep())
            .build();
    }

    @Bean
    public Job sampleJob2() {
        return this.jobBuilderFactory.get("sampleJob2")
            .start(sampleStep())
            .build();
    }

    @Bean
    public Step sampleStep() {
        return this.stepBuilderFactory.get("sampleStep")
            .<List<Integer>, List<String>>chunk(3)
            .reader(itemReader())
            .processor((ItemProcessor<? super List<Integer>, ? extends List<String>>) datas -> {
                List<String> processed = datas.stream().map(v -> String.format("'%d'", v)).collect(toList());
                System.out.printf("[Tx: %s] Process %s to %s%n", getCallBackMethod(), datas, processed);
                return processed;
            }).writer(data -> {
                List<String> flattened = data.stream().flatMap(Collection::stream).collect(toList());
                System.out.printf("[Tx: %s] Write %s%n", getCallBackMethod(), flattened);
            }).build();
    }

    @Bean
    @StepScope // to re-use itemReader among steps
    public ItemReader<List<Integer>> itemReader() {
        return new ItemReader<>() {
            private int count = 1;

            @Override
            public List<Integer> read() {
                if (count > 8) {
                    return null;
                }

                List<Integer> datas = IntStream.range(1, 4).boxed().map(v -> v * count).collect(toList());
                ++count;

                System.out.printf("[Tx: %s] Read %s%n", getCallBackMethod(), datas);

                return datas;
            }
        };
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%08x", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }
}
