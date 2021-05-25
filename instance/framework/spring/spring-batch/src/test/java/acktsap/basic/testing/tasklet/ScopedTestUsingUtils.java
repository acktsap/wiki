package acktsap.basic.testing.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import acktsap.basic.testing.TestBatchConfig;

/**
 *
 * Recommanded way.
 *
 * Scoped test using
 *
 * - {@link StepScopeTestUtils}.
 *
 */
@SpringBootTest(classes = TestBatchConfig.class)
class ScopedTestUsingUtils {

    // This component is defined step-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private Tasklet playerLoadTasklet;

    @Test
    void testPlayerLoadTasklet() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("action", "come")
            .toJobParameters();

        ExecutionContext executionContext = new ExecutionContext();
        executionContext.putString("player", "step scope using utils player");

        StepExecution execution = MetaDataInstanceFactory.createStepExecution(jobParameters, executionContext);

        StepScopeTestUtils.doInStepScope(execution, () -> {
            RepeatStatus result = playerLoadTasklet.execute(null, null);
            return result;
        });
    }
}
