package acktsap.basic.stepconfig.taskletstep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.AttributeAccessor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job continuableByFieldJob() {
        return this.jobBuilderFactory.get("continuableByFieldJob")
            .start(this.stepBuilderFactory.get("continuableByFieldStep")
                .tasklet(new Tasklet() {
                             private int count = 3;

                             @Override
                             public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                                 // need lock for concurrency
                                 if (count == 0) {
                                     return RepeatStatus.FINISHED;
                                 }

                                 System.out.printf("[%s - %s] process %s%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), count);
                                 --count;

                                 return RepeatStatus.CONTINUABLE;
                             }
                         }
                )
                .build())
            .build();
    }

    @Bean
    public Job chunkContextJob() {
        return this.jobBuilderFactory.get("chunkContextJob")
            .start(this.stepBuilderFactory.get("chunkContextStep")
                .tasklet((contribution, chunkContext) -> {
                        AttributeAccessor attributeAccessor = chunkContext;

                        Integer count = 3;
                        if (attributeAccessor.hasAttribute("count")) {
                            count = (Integer)attributeAccessor.getAttribute("count");
                        }

                        if (count == 0) {
                            return RepeatStatus.FINISHED;
                        }

                        if (count == 1) {
                            throw new IllegalStateException();
                        }

                        System.out.printf("[%s - %s] process %s%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), count);
                        --count;

                        // chunkContext에 저장해서 재시도해도 처음부터 다시 시작함
                        attributeAccessor.setAttribute("count", count);

                        return RepeatStatus.CONTINUABLE;
                    }
                )
                .build())
            .build();
    }

    @Bean
    public Job executionContextJob() {
        return this.jobBuilderFactory.get("executionContextJob")
            .start(this.stepBuilderFactory.get("executionContextStep")
                .tasklet(new Tasklet() {
                    private boolean isFirst = true;

                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

                        int count = 3;
                        if (executionContext.containsKey("count")) {
                            count = executionContext.getInt("count");
                        }

                        if (count == 0) {
                            return RepeatStatus.FINISHED;
                        }

                        /*
                            localhost:8080/job?name=executionContextJob
                            처음에만 실패하고 이후에 실행하면 성공
                         */
                        if (count == 1) {
                            if (isFirst) {
                                isFirst = false;
                                throw new IllegalStateException();
                            }
                        }

                        System.out.printf("[%s - %s] process %s%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), count);
                        --count;

                        // stepContext에 저장해서 재시도하면 거기서 가져옴
                        executionContext.putInt("count", count);

                        return RepeatStatus.CONTINUABLE;
                    }
                })
                .build())
            .build();
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%s", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }
}
