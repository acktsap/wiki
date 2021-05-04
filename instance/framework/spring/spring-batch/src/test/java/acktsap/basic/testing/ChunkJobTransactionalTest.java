package acktsap.basic.testing;

import static org.assertj.core.api.BDDAssertions.then;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=chunkJob"
}, classes = TestBatchConfig.class)
class ChunkJobTransactionalTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private JdbcOperations jdbcOperations;

    @Autowired
    void setDataSource(@Qualifier("testDataSource") DataSource dataSource) {
        this.jdbcOperations = new JdbcTemplate(dataSource);
    }

    // deadlock at `TaskletStep$ChunkTransactionCallback.doInTransaction - semaphore.acquire()`
    // see also https://github.com/spring-projects/spring-batch/issues/2021
    @Transactional
    @Test
    void testFootballJob() throws Exception {
        this.jdbcOperations.update("INSERT INTO CUSTOMER VALUES (?, ?, ?)", 1, "cat", 200);

        JobParameters jobParameter = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameter);
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    // deadlock at `TaskletStep$ChunkTransactionCallback.doInTransaction - semaphore.acquire()`
    // see also https://github.com/spring-projects/spring-batch/issues/2021
    @Transactional
    @Test
    void testPlayerLoadStep() throws Exception {
        this.jdbcOperations.update("INSERT INTO CUSTOMER VALUES (?, ?, ?)", 1, "cat", 200);

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("chunkStep");
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
