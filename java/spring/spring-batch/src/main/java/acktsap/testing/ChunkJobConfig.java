package acktsap.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Configuration
@RequiredArgsConstructor
public class ChunkJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "chunkJob")
    public Job chunkJob() {
        return this.jobBuilderFactory.get("chunkJob")
            .start(chunkStep())
            .build();
    }

    @Bean
    public Step chunkStep() {
        return this.stepBuilderFactory.get("chunkStep")
            .<String, String>chunk(3)
            .reader(chunkReader(null, null))
            .writer(chunkWriter())
            .build();
    }

    @StepScope
    @Bean
    public ItemReader<String> chunkReader(@Value("#{stepExecutionContext['meta']}") String meta, @Value("#{jobParameters['prefix']}") String prefix) {
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

                return String.format("%s:%s:%s%n", meta, prefix, next);
            }
        };
    }

    @Bean
    public ItemWriter<String> chunkWriter() {
        return datas -> {
            System.out.printf("[%s] Write %s%n", getCallBackMethod(), datas);
        };
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%08x", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }
}
