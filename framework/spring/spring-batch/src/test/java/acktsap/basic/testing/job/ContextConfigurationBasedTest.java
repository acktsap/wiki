package acktsap.basic.testing.job;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import acktsap.basic.testing.JobConfig;
import acktsap.basic.testing.TestBatchConfig;

/**
 *
 * Spring batch에서 추천하는 방법. 개인적으로는 이게 맞지 싶으나 Job bean이 의존하고 있는 configuration을 다 넣어주는건 좀 힘들거같음.
 * 그냥 {@link org.springframework.boot.test.context.SpringBootTest} 사용.
 *
 */
@SpringBatchTest
@TestPropertySource(properties = {
    "spring.batch.job.names=footballJob"
})
@ContextConfiguration(classes = {JobConfig.class, TestBatchConfig.class})  /* 이거 bean별로 다해줘야함.. 번거로움.. */
class ContextConfigurationBasedTest {

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
}
