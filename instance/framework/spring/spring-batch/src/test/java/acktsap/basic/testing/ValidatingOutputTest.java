package acktsap.basic.testing;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=writeJob"
}, classes = TestBatchConfig.class)
class ValidatingOutputTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Test
    void testWriteJob() throws Exception {
        JobParameters jobParameter = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameter);

        FileSystemResource expected = new FileSystemResource("src/test/resources/data/input.txt");
        FileSystemResource actual = new FileSystemResource("out/output.txt");
        then(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        AssertFile.assertFileEquals(expected, actual);
    }

}
