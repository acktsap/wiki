package acktsap.stepconfig.restart.always_start;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
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
                Thread.sleep(1500L);
                return RepeatStatus.FINISHED;
            })
            // 이거 있으면 같은 job parameter로 들어와도 재실행됨. -> TODO: 이거 맞아..???
            .allowStartIfComplete(true)
            .build();

        Job job = this.jobBuilderFactory.get("allowStartIfCompleteOnRunningJob")
            .start(allowStartIfCompleteOnRunningStep)
            .build();

        // to simulate restart
        executorService.scheduleAtFixedRate(new Runnable() {
            private int count = 1;

            @Override
            public void run() {
                if (count > 3) {
                    executorService.shutdownNow();
                    return;
                }

                try {
                    System.out.printf("[%s] Try %s [count: %d]%n", Thread.currentThread().getName(), job.getName(), count);
                    JobParameters jobParameters = new JobParametersBuilder()
                        // TODO: parameter가 있으면 재시작 안됨.. 의도된건가..????
                        // .addString("p", "1")
                        .toJobParameters();
                    jobLauncher.run(job, jobParameters);
                } catch (Exception e) {
                    System.err.printf("[%s] %s [count: %d]%n", Thread.currentThread().getName(), e.getMessage(), count);
                } finally {
                    ++count;
                }
            }
        }, 500L, 1000L, TimeUnit.MILLISECONDS);

        return job;
    }
}
