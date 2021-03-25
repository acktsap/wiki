package acktsap.jobconfig;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguerConfig {

    // customizing beans
    @Bean
    public BatchConfigurer batchConfigurer(DataSource dataSource) {
        return new DefaultBatchConfigurer() {
            @Override
            protected JobLauncher createJobLauncher() throws Exception {
                System.out.println("using custom JobLauncher");
                SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
                jobLauncher.setJobRepository(getJobRepository());
                jobLauncher.afterPropertiesSet();
                return jobLauncher;
            }

            @Override
            protected JobRepository createJobRepository() throws Exception {
                System.out.println("using custom JobRepository");
                JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
                factory.setDataSource(dataSource); // getDataSource 왜 없음??
                factory.setTransactionManager(getTransactionManager());
                factory.afterPropertiesSet();
                return factory.getObject();
            }

            @Override
            protected JobExplorer createJobExplorer() throws Exception {
                System.out.println("using custom JobExplorer");
                JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
                jobExplorerFactoryBean.setDataSource(dataSource); // getDataSource 왜 없음??
                jobExplorerFactoryBean.afterPropertiesSet();
                return jobExplorerFactoryBean.getObject();
            }

            @Override
            public PlatformTransactionManager getTransactionManager() {
                // 이건 왜 createTransactionManager 없음?
                System.out.println("using custom PlatformTransactionManager");
                return super.getTransactionManager();
            }
        };
    }
}
