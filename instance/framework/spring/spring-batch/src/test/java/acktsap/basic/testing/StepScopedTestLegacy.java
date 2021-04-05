package acktsap.basic.testing;

import static org.mockito.Mockito.mock;

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
 * bad...
 *
 */
@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=footballJob"
}, classes = TestBatchConfig.class)
public class StepScopedTestLegacy {

    // This component is defined step-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private Tasklet playerLoadTasklet;

    // the context for the test method, as if that execution were active in a Step at runtime
    // The factory method is detected by its signature (it must return a StepExecution
    public StepExecution getStepExecution() {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        execution.getExecutionContext().putString("player", "context from factory player");
        return execution;
    }

    @Test
    void testPlayerLoadTasklet() throws Exception {
        // The playerLoadTasklet is initialized and bound to the input data
        playerLoadTasklet.execute(null, null);
    }

}
