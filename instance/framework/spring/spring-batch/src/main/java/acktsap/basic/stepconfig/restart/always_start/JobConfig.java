package acktsap.basic.stepconfig.restart.always_start;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Bean
    public Job allowStartIfCompleteOnRunningJob() {
        Step allowStartIfCompleteOnRunningStep = this.stepBuilderFactory.get("allowStartIfCompleteOnRunningStep")
            .tasklet((contribution, chunkContext) -> {
                String jobName = chunkContext.getStepContext().getJobName();
                System.out.printf("[%s] %s - allowStartIfCompleteOnRunningStep%n", Thread.currentThread().getName(), jobName);
                Thread.sleep(1000L);
                return RepeatStatus.FINISHED;
            })
            .allowStartIfComplete(true) // 이거 있으면 같은 job parameter로 들어와도 재실행됨. 의심되면 이거 주석하고 실행해볼것.
            .build();

        Job job = this.jobBuilderFactory.get("allowStartIfCompleteOnRunningJob")
            .start(allowStartIfCompleteOnRunningStep)
            .build();

        // to simulate restart
        executorService.scheduleAtFixedRate(new Runnable() {
            private int count = 1;

            @Override
            public void run() {
                if (count >= 3) {
                    executorService.shutdownNow();
                    return;
                }

                try {
                    System.out.printf("[%s] Try %s [count: %d]%n", Thread.currentThread().getName(), job.getName(), count);
                    ++count;
                    jobLauncher.run(job, new JobParameters());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }, 500L, 2000L, TimeUnit.MILLISECONDS);

        return job;
    }
}
