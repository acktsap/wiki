package acktsap.runningjob.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job footballJob() {
        return this.jobBuilderFactory.get("footballJob")
            .start(playerLoad())
            .build();
    }

    @Bean
    public Step playerLoad() {
        return this.stepBuilderFactory.get("playerLoad")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - %s%n", Thread.currentThread().getName(), jobName, "playerLoad");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
