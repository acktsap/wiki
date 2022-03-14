package acktsap.testing.item;

import acktsap.testing.TestBatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@link SpringBatchTest} enables
 * <p>
 * - {@link org.springframework.batch.test.JobScopeTestExecutionListener}
 * - {@link org.springframework.batch.test.StepScopeTestExecutionListener}
 * <p>
 * See also
 * - {@link org.springframework.batch.test.context.BatchTestContextCustomizerFactory}
 * <p>
 * bad...
 */
@SpringBatchTest
@SpringBootTest(properties = {
    "spring.batch.job.names=footballJob"
}, classes = TestBatchConfig.class)
class ScopedTestUsingListener {

    // This component is defined step-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private ItemReader<String> chunkReader;

    // the context for the test method, as if that execution were active in a Step at runtime
    // The factory method is detected by its signature (it must return a StepExecution
    public StepExecution getStepExecution() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("prefix", "tt")
            .toJobParameters();

        ExecutionContext executionContext = new ExecutionContext();
        executionContext.putString("meta", "mmm");

        return MetaDataInstanceFactory.createStepExecution(jobParameters, executionContext);
    }

    @Test
    void testChunkReader() throws Exception {
        String readed = chunkReader.read();
        System.out.println(readed); // mmm:tt:1
    }

}
