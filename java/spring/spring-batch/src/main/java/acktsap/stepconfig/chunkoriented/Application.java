package acktsap.stepconfig.chunkoriented;

import lombok.RequiredArgsConstructor;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@EnableBatchProcessing
@SpringBootApplication
@RequiredArgsConstructor
public class Application {
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
        // transaction 1 : 3 read, 3 process, 3 write
        // transaction 2 : 3 read, 3 process, 3 write
        // transaction 3 : 2 read, 2 process, 2 write
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

                    List<Integer> datas = IntStream.range(1, 4).boxed()
                        .map(v -> v * count)
                        .collect(toList());
                    ++count;

                    System.out.printf("[Tx: %s] Read %s%n", getCallBackMethod(), datas);

                    return datas;
                }
            })
            .processor((ItemProcessor<? super List<Integer>, ? extends List<String>>) datas -> {
                List<String> processed = datas.stream()
                    .map(v -> String.format("'%d'", v))
                    .collect(toList());
                System.out.printf("[Tx: %s] Process %s to %s%n", getCallBackMethod(), datas, processed);
                return processed;
            })
            .writer(data -> {
                List<String> flattened = data.stream()
                    .flatMap(Collection::stream)
                    .collect(toList());
                System.out.printf("[Tx: %s] Write %s%n", getCallBackMethod(), flattened);
            })
            .build();
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%08x", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }

    // db: localhost:8080/h2-console (user : "sa", pw: "")
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.exit(0);
    }
}
