package acktsap.basic.jobconfig;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

/**
 *
 * {@link EnableBatchProcessing} enables
 *
 * - JobRepository: bean name "jobRepository"
 * - JobLauncher: bean name "jobLauncher"
 * - JobRegistry: bean name "jobRegistry"
 * - PlatformTransactionManager: bean name "transactionManager"
 * - JobBuilderFactory: bean name "jobBuilders"
 * - StepBuilderFactory: bean name "stepBuilders"
 *
 * The user should to provide a DataSource as a bean in the context,
 * or else implement BatchConfigurer in the configuration.
 * If a user does not provide a DataSource within the context,
 * a Map based JobRepository will be used.
 * If multiple DataSources are defined in the context,
 * the one annotated with {@link Primary} will be used.
 *
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig extends DefaultBatchConfigurer {
    private final DataSource dataSource;

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
}
