package acktsap.jobconfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

/**
 *{{@link EnableBatchProcessing} enables
 *
 * - JobRepository: bean name "jobRepository"
 * - JobLauncher: bean name "jobLauncher"
 * - JobRegistry: bean name "jobRegistry"
 * - PlatformTransactionManager: bean name "transactionManager"
 * - JobBuilderFactory: bean name "jobBuilders"
 * - StepBuilderFactory: bean name "stepBuilders"
 *
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job footballJob(Step playerLoad) {
        return this.jobBuilderFactory.get("footballJob")
            .start(playerLoad)
            .build();
    }

    @Bean
    public Job noRestartJob(Step playerLoad) {
        return this.jobBuilderFactory.get("noRestartJob")
            .preventRestart()
            .start(playerLoad)
            .build();
    }

    @Bean
    public Job jobListenerJob(Step playerLoad) {
        return this.jobBuilderFactory.get("jobListenerJob")
            .listener(new JobExecutionListener() {
                @Override
                public void beforeJob(JobExecution jobExecution) {
                    System.out.printf("%s starts at %s (status: %s)%n", jobExecution.getJobInstance(), jobExecution.getStartTime().toInstant(),
                        jobExecution.getStatus());
                }

                @Override
                public void afterJob(JobExecution jobExecution) {
                    System.out.printf("%s finished at %s (status: %s)%n", jobExecution.getJobInstance(), jobExecution.getEndTime().toInstant(),
                        jobExecution.getStatus());
                }
            })
            .start(playerLoad)
            .build();
    }

    @Bean
    public Job jobParameterValidatorJob(Step playerLoad) {
        return this.jobBuilderFactory.get("jobParameterValidatorJob")
            .validator(new JobParametersValidator() {
                @Override
                public void validate(JobParameters parameters) throws JobParametersInvalidException {
                    String value = parameters.getString("kk");
                    // print twice.. why??
                    System.out.println("kk: " + value);
                }
            })
            .start(playerLoad)
            .build();
    }

    @Bean
    public Step playerLoad() {
        return this.stepBuilderFactory.get("playerLoad")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.println(jobName + " - playerLoad");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
