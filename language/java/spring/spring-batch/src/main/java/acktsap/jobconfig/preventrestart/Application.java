package acktsap.jobconfig.preventrestart;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/*
    Built-in metrics

    https://docs.spring.io/spring-batch/docs/current/reference/html/monitoring-and-metrics.html#monitoring-and-metrics
 */
@EnableBatchProcessing
@SpringBootApplication
public class Application {

    @Bean
    public Job testJob(
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory
    ) {
        return jobBuilderFactory.get("testJob")
            .preventRestart()
            .start(
                stepBuilderFactory.get("testStep1")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("testStep1 always run");
                        return RepeatStatus.FINISHED;
                    })
                    .allowStartIfComplete(true)
                    .build()
            )
            .next(
                stepBuilderFactory.get("testStep2")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("testStep2 always run");
                        return RepeatStatus.FINISHED;
                    })
                    .allowStartIfComplete(true)
                    .build()
            )
            .next(
                stepBuilderFactory.get("testStep3")
                    .tasklet((contribution, chunkContext) -> {
                        throw new IllegalStateException();
                    })
                    .build()
            )
            .build();
    }


    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        Properties properties = new Properties();
        properties.put("spring.batch.job.enabled", "false");
        application.setDefaultProperties(properties);
        ApplicationContext applicationContext = application.run(args);

        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        Job job = applicationContext.getBean("testJob", Job.class);
        JobParameters jobParameter = new JobParametersBuilder()
            .toJobParameters();
        JobExecution jobExecution1 = jobLauncher.run(job, jobParameter);
        System.out.println("1th result: " + jobExecution1.getExitStatus());

        JobExecution jobExecution2 = jobLauncher.run(job, jobParameter);
        System.out.println("2nd result: " + jobExecution2.getExitStatus());

        System.exit(0);
    }
}
