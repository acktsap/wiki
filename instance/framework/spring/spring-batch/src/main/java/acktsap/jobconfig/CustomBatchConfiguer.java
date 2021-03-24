package acktsap.jobconfig;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;

// Just for snippets
// @Component
public class CustomBatchConfiguer extends DefaultBatchConfigurer {

    // Tx configuration for jobRepository
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
        return factory.getObject();
    }
     */

    // Changing batch meta table prefix (default: BATCH_)
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix("TEST_");
        return factory.getObject();
    }
     */

    // Im-Memory Repository
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(transactionManager);
        return factory.getObject();
    }
     */

    // Non-standard Database Types in a Repository
    /*
    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setDatabaseType("db2");
        factory.setTransactionManager(transactionManager);
        return factory.getObject();
    }
     */

    // Set async taskExecutor to JobLauncher
    /*
    @Override
    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
     */
}
