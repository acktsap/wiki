package acktsap.basic.testing;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * {@link SpringBatchTest} enables
 *
 * - {@link org.springframework.batch.test.StepScopeTestExecutionListener}
 * - {@link org.springframework.batch.test.JobScopeTestExecutionListener}
 *
 */
@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=footballJob"
}, classes = TestBatchConfig.class)
public class StepScopedTest {

    // This component is defined step-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private Tasklet playerLoadTasklet;

    @Test
    void testPlayerLoadTasklet() throws Exception {
        // TODO: https://docs.spring.io/spring-batch/docs/current/reference/html/testing.html#testing-step-scoped-components
        // not works
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        execution.getExecutionContext().putString("player", "context from utils player");
        StepScopeTestUtils.doInStepScope(execution, () -> {
            playerLoadTasklet.execute(null, null);
            return null;
        });
    }
}
