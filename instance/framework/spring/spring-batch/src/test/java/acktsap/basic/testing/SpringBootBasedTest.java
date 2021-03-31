package acktsap.basic.testing;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Recommanded way.
 *
 * 의존성 전부 주입시키기 힘들어서 어쩔 수 없이 {@link org.springframework.boot.test.context.SpringBootTest}를 쓰는 경우.
 *
 * {@link org.springframework.batch.test.context.SpringBatchTest} registers
 *
 * - JobLauncherTestUtils (requires a single Job bean)
 * - JobRepositoryTestUtils (requires a DataSource bean)
 *
 *
 * {@link org.springframework.boot.test.context.SpringBootTest}
 *
 * - properties : select job bean for test.
 * - load ApplicationProperties
 *
 */
@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=footballJob",
    "spring.batch.job.enabled=false"
}, classes = TestBatchConfig.class)
class SpringBootBasedTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Test
    void testFootballJob() throws Exception {
        JobParameters jobParameter = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameter);
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    void testPlayerLoadStep() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("playerLoadStep");
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
