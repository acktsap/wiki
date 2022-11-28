package acktsap.stepconfig.taskletstep.context;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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

                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // need lock for concurrency
                        if (count == 0) {
                            return RepeatStatus.FINISHED;
                        }

                        System.out.printf("[%s - %s] process %s%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), count);
                        --count;

                        return RepeatStatus.CONTINUABLE;
                    }
                })
                .build())
            .build();
    }

    @Bean
    public Job chunkContextJob() {
        return this.jobBuilderFactory.get("chunkContextJob")
            .start(this.stepBuilderFactory.get("chunkContextStep")
                .tasklet((contribution, chunkContext) -> {

                    int count = 3;
                    if (chunkContext.hasAttribute("count")) {
                        count = (int) chunkContext.getAttribute("count");
                    }

                    if (count == 0) {
                        return RepeatStatus.FINISHED;
                    }

                    /*
                        localhost:8080/job?name=chunkContextJob
                        chunkContext에 저장해서 재시도해도 처음부터 다시 시작함
                     */
                    if (count == 1) {
                        throw new IllegalStateException("count is 1");
                    }

                    System.out.printf("[%s - %s] process %s%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), count);

                    chunkContext.setAttribute("count", count - 1);

                    return RepeatStatus.CONTINUABLE;
                })
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
                            count 1일때 처음에만 실패하고 이후에 실행하면 성공
                         */
                        if (count == 1) {
                            if (isFirst) {
                                isFirst = false;
                                throw new IllegalStateException("count is 1");
                            }
                        }

                        System.out.printf("[%s - %s] process %s%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), count);
                        --count;

                        // stepContext에 저장해서 재시도하면 거기서 가져옴
                        // 실패한 step_execution 은 그대로 두고 새로 생성 후 실행
                        // step_execution_context 도 다름
                        executionContext.putInt("count", count);

                        return RepeatStatus.CONTINUABLE;
                    }
                })
                .build())
            .build();
    }

    @Bean
    public Job executionContextJob2() {
        return this.jobBuilderFactory.get("executionContextJob2")
            .start(this.stepBuilderFactory.get("executionContextStep2")
                .tasklet(new Tasklet() {
                    private int failCount = 3;

                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

                        String first = "true";
                        if (executionContext.containsKey("first")) {
                            first = executionContext.getString("first");
                        } else {
                            executionContext.put("first", "false");
                            System.out.printf("[%s - %s] no count%n", chunkContext.getStepContext().getJobName(), getCallBackMethod());
                        }

                        System.out.printf("[%s - %s] process (first: %s)%n", chunkContext.getStepContext().getJobName(), getCallBackMethod(), first);

                        /*
                            localhost:8080/job?name=executionContextJob
                            처음에만 실패하고 이후에 실행하면 성공
                         */
                        if (failCount > 0) {
                            --failCount;
                            throw new IllegalStateException("failCount is " + failCount);
                        }

                        return RepeatStatus.FINISHED;
                    }
                })
                .build())
            .build();
    }

    @Bean
    public Job exitStatusJob() {
        return this.jobBuilderFactory.get("exitStatusJob")
            .start(this.stepBuilderFactory.get("exitStatusStep")
                .tasklet((contribution, chunkContext) -> {
                    // localhost:8080/h2-console 에서 확인해 보면 exitStatus만 FAILED임
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build())
            .build();
    }

    private String getCallBackMethod() {
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        return String.format("%s@%s", transactionSynchronization.getClass().getSimpleName(), transactionSynchronization.hashCode());
    }
}
