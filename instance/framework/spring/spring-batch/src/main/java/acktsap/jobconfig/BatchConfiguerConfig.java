package acktsap.jobconfig;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguerConfig {

    // customizing beans
    @Bean
    public BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer() {
            @Override
            public JobLauncher getJobLauncher() {
                System.out.println("using custom JobLauncher");
                return super.getJobLauncher();
            }

            @Override
            public JobRepository getJobRepository() {
                System.out.println("using custom JobRepository");
                return super.getJobRepository();
            }

            @Override
            public JobExplorer getJobExplorer() {
                System.out.println("using custom JobExplorer");
                return super.getJobExplorer();
            }

            @Override
            public PlatformTransactionManager getTransactionManager() {
                System.out.println("using custom PlatformTransactionManager");
                return super.getTransactionManager();
            }
        };
    }
}
