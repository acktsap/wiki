package acktsap.basic.testing.step;

import static org.assertj.core.api.BDDAssertions.then;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import acktsap.basic.testing.TestBatchConfig;

@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=footballJob"
}, classes = TestBatchConfig.class)
class SingleTaskletStepTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private JdbcOperations jdbcOperations;

    @Autowired
    void setDataSource(@Qualifier("testDataSource") DataSource dataSource) {
        this.jdbcOperations = new JdbcTemplate(dataSource);
    }

    @Test
    void testPlayerPreStep() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("action", "in")
            .toJobParameters();

        ExecutionContext jobExecutionContext = new ExecutionContext();
        jobExecutionContext.putString("player", "lazy player");

        // player: lazy player, action: in
        // since it uses JobExecutionContext
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("playerPreStep", jobParameters, jobExecutionContext);

        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    void testPlayerLoadStep() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("action", "in")
            .toJobParameters();

        ExecutionContext jobExecutionContext = new ExecutionContext();
        jobExecutionContext.putString("player", "lazy player");

        // player: null, action: in
        // since it uses StepExecutionContext
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("playerLoadStep", jobParameters, jobExecutionContext);

        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Transactional
    @Test
    void testPlayerPreStepTransaction() throws Exception {
        this.jdbcOperations.update("INSERT INTO CUSTOMER VALUES (?, ?, ?)", 1, "cat", 200);

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("action", "in")
            .toJobParameters();

        ExecutionContext jobExecutionContext = new ExecutionContext();
        jobExecutionContext.putString("player", "lazy player");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("playerPreStep", jobParameters, jobExecutionContext);

        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
