package acktsap.basic.stepconfig.chunkoriented;

import static java.util.stream.Collectors.toList;

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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
        return this.jobBuilderFactory.get("sampleJob")
            .repository(jobRepository) // default is "jobRepository" bean
            .start(sampleStep)
            .build();
    }

    @Bean
    public Step sampleStep(PlatformTransactionManager transactionManager) {
        return this.stepBuilderFactory.get("sampleStep")
            .transactionManager(transactionManager) // default is "transactionManager" bean
            .<List<Integer>, List<String>>chunk(3) // chunk : # of items to be processed before tx is commited
            .reader(new ItemReader<>() {
                private int count = 1;
                @Override
                public List<Integer> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                    // repeat until return null
                    if (count > 8) {
                        return null;
                    }

                    List<Integer> datas = IntStream.range(count, count + 3).boxed().collect(toList());
                    System.out.printf("[%s] Read %s%n", getCallBackMethod(), datas);
                    ++count;
                    return datas;
                }
            })
            .processor((ItemProcessor<? super List<Integer>, ? extends List<String>>)datas -> {
                List<String> processed = datas.stream()
                    .map(v -> String.format("'%d'", v))
                    .collect(toList());
                System.out.printf("[%s] Process %s to %s%n", getCallBackMethod(), datas, processed);
                return processed;
            })
            .writer(data -> {
                System.out.printf("[%s] Write %s%n", getCallBackMethod(), data);
            })
            .build();
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%s",transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }

}
