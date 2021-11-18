package acktsap.basic.testing;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.step.NoWorkFoundStepExecutionListener;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * Mocking StepExecution
 *
 * {@link MetaDataInstanceFactory} provides mocking objects for spring batch domain objects.
 *
 * - createJobInstance
 * - createJobExecution
 * - createStepExecution
 *
 */
@SpringBootTest
class MockingDomainObjectsTest {

    @Test
    void testAfterStep() {
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        stepExecution.setExitStatus(ExitStatus.COMPLETED);
        stepExecution.setReadCount(0);

        StepExecutionListener stepExecutionListener = new NoWorkFoundStepExecutionListener();
        ExitStatus exitStatus = stepExecutionListener.afterStep(stepExecution);

        then(exitStatus.getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
    }
}
