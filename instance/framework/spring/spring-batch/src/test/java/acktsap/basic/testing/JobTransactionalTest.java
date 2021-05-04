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
    "spring.batch.job.names=footballJob"
}, classes = TestBatchConfig.class)
class JobTransactionalTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private JdbcOperations jdbcOperations;

    @Autowired
    void setDataSource(@Qualifier("testDataSource") DataSource dataSource) {
        this.jdbcOperations = new JdbcTemplate(dataSource);
    }

    @Transactional
    @Test
    void testFootballJob() throws Exception {
        this.jdbcOperations.update("INSERT INTO CUSTOMER VALUES (?, ?, ?)", 1, "cat", 200);

        JobParameters jobParameter = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameter);
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Transactional
    @Test
    void testPlayerLoadStep() throws Exception {
        this.jdbcOperations.update("INSERT INTO CUSTOMER VALUES (?, ?, ?)", 1, "cat", 200);

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("playerLoadStep");
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
