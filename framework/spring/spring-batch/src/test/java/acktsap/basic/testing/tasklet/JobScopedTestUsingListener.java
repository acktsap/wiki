package acktsap.basic.testing.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import acktsap.basic.testing.TestBatchConfig;

/**
 *
 * {@link SpringBatchTest} enables
 *
 * - {@link org.springframework.batch.test.JobScopeTestExecutionListener}
 * - {@link org.springframework.batch.test.StepScopeTestExecutionListener}
 *
 * bad...
 *
 */
@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=footballJob"
}, classes = TestBatchConfig.class)
class JobScopedTestUsingListener {

    // This component is defined job-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private Tasklet playerPreTasklet;

    // the context for the test method, as if that execution were active in a Job at runtime
    // The factory method is detected by its signature (it must return a JobExecution
    public JobExecution getJobExecution() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("action", "go")
            .toJobParameters();

        JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution("ttJob", 0L, 0L, jobParameters);
        jobExecution.getExecutionContext().putString("player", "job scope using factory player");

        return jobExecution;
    }

    @Test
    void testPlayerPreTasklet() throws Exception {
        playerPreTasklet.execute(null, null);
    }

}
