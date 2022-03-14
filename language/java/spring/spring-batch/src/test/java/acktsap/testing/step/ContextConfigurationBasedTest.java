package acktsap.testing.step;

import acktsap.testing.JobConfig;
import acktsap.testing.TestBatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Spring batch에서 추천하는 방법. 개인적으로는 이게 맞지 싶으나 Job bean이 의존하고 있는 configuration을 다 넣어주는건 좀 힘들거같음.
 * 그냥 {@link org.springframework.boot.test.context.SpringBootTest} 사용하는게..
 */
@SpringBatchTest
@TestPropertySource(properties = {
    "spring.batch.job.names=footballJob"
})
@ContextConfiguration(classes = {JobConfig.class, TestBatchConfig.class})  /* 이거 bean별로 다해줘야함 */
class ContextConfigurationBasedTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Test
    void testPlayerLoadStep() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("playerLoadStep");
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
