package acktsap.basic.stepconfig.listener;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.RequiredArgsConstructor;

/**
 * {@link org.springframework.batch.core.StepListener} : mark interface for listener.
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job testJob() {
        return this.jobBuilderFactory.get("testJob")
            .start(stepExecutionListenerStep())
            .next(chunkListenerStep())
            .build();
    }

    @Bean
    public Job testFailureJob() {
        return this.jobBuilderFactory.get("testFailureJob")
            .start(stepExecutionListenerOnFailureStep()).on("FAILED").fail()
            .next(stepExecutionListenerStep()) // not called since exit code of previous one is FAILED
            .end()
            .build();
    }

    @Bean
    public Step stepExecutionListenerStep() {
        return this.stepBuilderFactory.get("stepExecutionListenerStep")
            .listener(new StepExecutionListener() {
                @Override
                public void beforeStep(StepExecution stepExecution) {
                    String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
                    String stepName = stepExecution.getStepName();

                    System.out.printf("[%s - %s] beforeStep (result: %s)%n", jobName, stepName, stepExecution.getExitStatus());
                }

                @Override
                public ExitStatus afterStep(StepExecution stepExecution) {
                    String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
                    String stepName = stepExecution.getStepName();

                    System.out.printf("[%s - %s] afterStep (result: %s)%n", jobName, stepName, stepExecution.getExitStatus());

                    return stepExecution.getExitStatus();
                }
            })
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                String stepName = chunkContext.getStepContext().getStepName();

                System.out.printf("[%s - %s] run step%n", jobName, stepName);

                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step stepExecutionListenerOnFailureStep() {
        return this.stepBuilderFactory.get("stepExecutionListenerOnFailureStep")
            .listener(new StepExecutionListener() {
                @Override
                public void beforeStep(StepExecution stepExecution) {
                    String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
                    String stepName = stepExecution.getStepName();

                    System.out.printf("[%s - %s] beforeStep %n", jobName, stepName);
                }

                @Override
                public ExitStatus afterStep(StepExecution stepExecution) {
                    String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
                    String stepName = stepExecution.getStepName();

                    System.out.printf("[%s - %s] afterStep (result: %s)%n", jobName, stepName, stepExecution.getExitStatus().getExitCode());

                    if (stepExecution.getExitStatus() == ExitStatus.FAILED) {
                        System.out.printf("[%s - %s] afterStep (exceptions: %s)%n", jobName, stepName, stepExecution.getFailureExceptions());
                    }

                    // Give the listener a change to modify return type of afterStep.
                    // In this case, even return type of origin one is COMPLETED,
                    // since step exit status is computed by combining with FAILED,
                    // final exit status is 'ExitStatus.COMPLETED.and(ExitStatus.FAILED)'
                    // which is ExitStatus.FAILED
                    ExitStatus adjustedExitStatus = ExitStatus.FAILED;

                    return adjustedExitStatus;
                }
            })
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                String stepName = chunkContext.getStepContext().getStepName();

                System.out.printf("[%s - %s] run step%n", jobName, stepName);

                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step chunkListenerStep() {
        return this.stepBuilderFactory.get("chunkListenerStep")
            .<List<Integer>, List<String>>chunk(2)
            .reader(new ItemReader<>() {
                private int count = 1;

                @Override
                public List<Integer> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                    // repeat until return null
                    if (count > 5) {
                        return null;
                    }

                    List<Integer> datas = IntStream.range(1, 3).boxed()
                        .map(v -> v * count)
                        .collect(toList());
                    ++count;

                    System.out.printf("[Tx: %s] read %s%n", getTxName(), datas);

                    return datas;
                }
            })
            .processor((ItemProcessor<? super List<Integer>, ? extends List<String>>)datas -> {
                List<String> processed = datas.stream()
                    .map(v -> String.format("'%d'", v))
                    .collect(toList());
                System.out.printf("[Tx: %s] Process %s to %s%n", getTxName(), datas, processed);
                return processed;
            })
            .writer(items -> System.out.printf("[Tx: %s] write %s%n", getTxName(), items))
            .listener(new ItemReadListener<>() {
                @Override
                public void beforeRead() {
                    System.out.printf("[Tx: %s] beforeRead%n", getTxName());
                }

                @Override
                public void afterRead(List<Integer> item) {
                    System.out.printf("[Tx: %s] afterRead: %s%n", getTxName(), item);
                }

                @Override
                public void onReadError(Exception ex) {
                    System.out.printf("[Tx: %s] onReadError: %s%n", getTxName(), ex);
                }
            })
            .listener(new ItemProcessListener<>() {
                @Override
                public void beforeProcess(List<Integer> item) {
                    System.out.printf("[Tx: %s] beforeProcess: %s%n", getTxName(), item);
                }

                @Override
                public void afterProcess(List<Integer> item, List<String> result) {
                    System.out.printf("[Tx: %s] afterProcess: %s to %s%n", getTxName(), item, result);
                }

                @Override
                public void onProcessError(List<Integer> item, Exception e) {
                    System.out.printf("[Tx: %s] onProcessError: %s, error: %s%n", getTxName(), item, e);
                }
            })
            .listener(new ItemWriteListener<>() {
                @Override
                public void beforeWrite(List<? extends List<String>> items) {
                    System.out.printf("[Tx: %s] beforeWrite: %s%n", getTxName(), items);
                }

                @Override
                public void afterWrite(List<? extends List<String>> items) {
                    System.out.printf("[Tx: %s] afterWrite: %s%n", getTxName(), items);
                }

                @Override
                public void onWriteError(Exception exception, List<? extends List<String>> items) {
                    System.out.printf("[Tx: %s] onWriteError: %s, error: %s%n", getTxName(), items, exception);
                }
            })
            .listener(new ChunkListener() {
                // called after tx is started
                @Override
                public void beforeChunk(ChunkContext context) {
                    String jobName = context.getStepContext().getJobName();
                    String stepName = context.getStepContext().getStepName();

                    System.out.printf("[%s - %s, Tx: %s] beforeChunk%n", jobName, stepName, getTxName());
                }

                // called after tx is commited
                @Override
                public void afterChunk(ChunkContext context) {
                    String jobName = context.getStepContext().getJobName();
                    String stepName = context.getStepContext().getStepName();

                    System.out.printf("[%s - %s, Tx: %s] afterChunk%n", jobName, stepName, getTxName());
                }

                // called on chunk failure
                @Override
                public void afterChunkError(ChunkContext context) {
                    String jobName = context.getStepContext().getJobName();
                    String stepName = context.getStepContext().getStepName();

                    System.out.printf("[%s - %s, Tx: %s] afterChunkError%n", jobName, stepName, getTxName());
                }
            })
            .build();
    }

    private String getTxName() {
        try {
            TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
            return String.format("%s@%s", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
        } catch (Exception e) {
            return null;
        }
    }
}
