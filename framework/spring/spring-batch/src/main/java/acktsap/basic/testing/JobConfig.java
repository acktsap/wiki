package acktsap.basic.testing;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "footballJob")
    public Job footballJob() {
        return this.jobBuilderFactory.get("footballJob")
            .start(playerPreStep())
            .next(playerLoadStep())
            .build();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "writeJob")
    public Job writeJob() {
        return this.jobBuilderFactory.get("writeJob")
            .start(writeStep())
            .build();
    }

    @Bean
    public Step playerPreStep() {
        return this.stepBuilderFactory.get("playerPreStep")
            .tasklet(playerPreTasklet(null, null))
            .build();
    }

    @Bean
    public Step playerLoadStep() {
        return this.stepBuilderFactory.get("playerLoadStep")
            .tasklet(playerLoadTasklet(null, null))
            .build();
    }

    @JobScope
    @Bean
    public Tasklet playerPreTasklet(@Value("#{jobExecutionContext['player']}") String player, @Value("#{jobParameters['action']}") String action) {
        return (contribution, chunkContext) -> {
            System.out.printf("[%s] playerPreTasklet (player: %s, action: %s)%n", Thread.currentThread().getName(), player, action);
            return RepeatStatus.FINISHED;
        };
    }

    @StepScope
    @Bean
    public Tasklet playerLoadTasklet(@Value("#{stepExecutionContext['player']}") String player, @Value("#{jobParameters['action']}") String action) {
        return (contribution, chunkContext) -> {
            System.out.printf("[%s] playerLoadTasklet (player: %s, action: %s)%n", Thread.currentThread().getName(), player, action);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step writeStep() {
        return this.stepBuilderFactory.get("writeStep")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.println(jobName + " - writeStep");

                InputStream in = getClass().getClassLoader().getResourceAsStream("data/input.txt");
                OutputStream out = new FileOutputStream("out/output.txt");

                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);
                }

                in.close();
                out.close();

                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
