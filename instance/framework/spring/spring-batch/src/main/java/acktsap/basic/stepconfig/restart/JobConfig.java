package acktsap.basic.stepconfig.restart;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemReader;
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

    // @Bean
    public Job footballJob() {
        Job job = this.jobBuilderFactory.get("footballJob")
            .start(playerLoad())
            .next(gameLoad())
            .next(playerSummarization())
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
        }, 3000L, 2000L, TimeUnit.MILLISECONDS);

        return job;
    }

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

    @Bean
    public Step playerLoad() {
        return this.stepBuilderFactory.get("playerLoad")
            .<String, String>chunk(3)
            .reader(reader())
            .writer(players -> System.out.printf("Load players: %s%n", players))
            // .allowStartIfComplete(false) // default is false. so, doesn't run on 2nd try
            .build();
    }

    @Bean
    public Step gameLoad() {
        return this.stepBuilderFactory.get("gameLoad")
            .<String, String>chunk(3)
            .reader(reader())
            .writer(games -> System.out.printf("Load games: %s%n", games))
            .allowStartIfComplete(true) // always run
            .build();
    }

    @Bean
    public Step playerSummarization() {
        return this.stepBuilderFactory.get("playerSummarization")
            .tasklet((contribution, chunkContext) -> {
                throw new IllegalStateException("Fails to summarize player");
            })
            // not run after 2nd try
            // might be used when need a manual fix to restart
            // default values is Integer.MAX_VALUE
            .startLimit(2)
            .build();
    }

    private ItemReader<String> reader() {
        return new ItemReader<>() {
            private final AtomicBoolean hasRead = new AtomicBoolean(false);

            @Override
            public String read() {
                if (hasRead.get()) {
                    hasRead.set(false);
                    return null;
                }

                hasRead.set(true);
                return "item";
            }
        };
    }

}
