package acktsap.jobconfig.batchconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;

import javax.sql.DataSource;

// Just for snippets for case
// @Configuration
// @EnableBatchProcessing
@RequiredArgsConstructor
public class CustomBatchConfiguer extends DefaultBatchConfigurer {
    private final DataSource dataSource;

    // Tx configuration for jobRepository
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ"); // here
        return factory.getObject();
    }
     */

    // Changing batch meta table prefix (default: BATCH_)
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.setTablePrefix("TEST_"); // here
        return factory.getObject();
    }
     */

    // Im-Memory Repository
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(getTransactionManager());
        return factory.getObject();
    }
     */

    // Non-standard Database Types in a Repository
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setDatabaseType("db2"); // here
        factory.setTransactionManager(getTransactionManager());
        return factory.getObject();
    }
     */

    // Set async taskExecutor to JobLauncher
    /*
    @Override
    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); // here
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
     */
}
