package acktsap.basic.testing.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobScopeTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import acktsap.basic.testing.TestBatchConfig;

/**
 *
 * Recommanded way.
 *
 * Scoped test using
 *
 * - {@link org.springframework.batch.test.JobScopeTestUtils}
 *
 */
@SpringBootTest(classes = TestBatchConfig.class)
class JobScopedTestUsingUtils {

    // This component is defined job-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private Tasklet playerPreTasklet;

    @Test
    void testPlayerPreTasklet() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("action", "come")
            .toJobParameters();

        JobExecution execution = MetaDataInstanceFactory.createJobExecution("ttJob", 0L, 0L, jobParameters);
        execution.getExecutionContext().putString("player", "job scope using utils player");

        JobScopeTestUtils.doInJobScope(execution, () -> {
            RepeatStatus result = playerPreTasklet.execute(null, null);
            return result;
        });
    }
}
