package acktsap.basic.testing.item;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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
 * - {@link org.springframework.batch.test.StepScopeTestUtils}.
 *
 */
@SpringBootTest(classes = TestBatchConfig.class)
class ScopedTestUsingUtils {

    // This component is defined step-scoped, so it cannot be injected unless
    // a step is active...
    @Autowired
    private ItemReader<String> chunkReader;

    @Autowired
    private ItemWriter<String> chunkWriter;

    @Test
    void testChunkReader() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("prefix", "tt")
            .toJobParameters();

        ExecutionContext executionContext = new ExecutionContext();
        executionContext.putString("meta", "mmm");

        StepExecution execution = MetaDataInstanceFactory.createStepExecution(jobParameters, executionContext);

        String readed = StepScopeTestUtils.doInStepScope(execution, () -> {
            return chunkReader.read();
        });
        System.out.println(readed); // mmm:tt:1
    }

    @Test
    void testChunkWriter() throws Exception {
        chunkWriter.write(List.of("test"));
    }
}
