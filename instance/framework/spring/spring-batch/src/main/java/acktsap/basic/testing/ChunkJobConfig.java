package acktsap.basic.testing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ChunkJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "chunkJob")
    public Job chunkJob() {
        return this.jobBuilderFactory.get("chunkJob")
            .start(this.stepBuilderFactory.get("chunkStep")
                .<String, String>chunk(3)
                .reader(reader())
                .writer(writer())
                .build()
            ).build();
    }

    @Bean
    public ItemReader<String> reader() {
        return new ItemReader<>() {
            private int count = 1;

            @Override
            public String read() {
                // repeat until return null
                if (count > 8) {
                    return null;
                }

                int next = count;
                count++;
                return Integer.toString(next);
            }
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return datas -> {
            System.out.printf("[%s] Write %s%n", getCallBackMethod(), datas);
        };
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%08x", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }
}
