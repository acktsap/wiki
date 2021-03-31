package acktsap.pattern.dbread;

import java.util.List;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.RequiredArgsConstructor;

// TODO: not finished. finish after study
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private JdbcOperations jdbcOperations;

    private final int poolSize = Runtime.getRuntime().availableProcessors() * 2;
    private final int chunkSize = 3;
    private final int pageSize = 3;
    private final Supplier<Integer> startCursorProvider = new Supplier<>() {
        private final Object lock = new Object();
        private int cursor = 1;

        @Override
        public Integer get() {
            int next = 0;
            // lock at cursor
            synchronized (lock) {
                next = cursor;
                cursor += (chunkSize);
            }
            return next;
        }
    };

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcOperations = new JdbcTemplate(dataSource);
    }

    @Bean
    public Job sampleJob(Step sampleStep) {
        return this.jobBuilderFactory.get("sampleJob")
            .start(sampleStep)
            .build();
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("runner");
        taskExecutor.setCorePoolSize(poolSize);
        taskExecutor.setMaxPoolSize(poolSize);
        return taskExecutor;
    }

    @Bean
    public Step sampleStep(PlatformTransactionManager transactionManager, TaskExecutor taskExecutor) {
        return this.stepBuilderFactory.get("sampleStep")
            .transactionManager(transactionManager)
            .<List<Customer>, List<Customer>>chunk(chunkSize)
            .reader(() -> {
                int cursor = startCursorProvider.get();
                String sql = String.format("SELECT * FROM CUSTOMER WHERE %d <= ID AND ID <= %d", cursor, cursor + (pageSize - 1));
                List<Customer> customers = jdbcOperations.query(sql, (rs, rowNum) -> {
                    long id = rs.getLong(1);
                    String name = rs.getString(2);
                    return new Customer(id, name);
                });

                if (customers.isEmpty()) {
                    return null;
                }

                System.out.printf("[%s] Read %s%n", Thread.currentThread().getName(), customers);
                return customers;
            })
            .processor((ItemProcessor<? super List<Customer>, ? extends  List<Customer>>)datas -> {
                System.out.printf("[%s] Process %s%n", Thread.currentThread().getName(), datas);
                return datas;
            })
            .writer(data -> {
                System.out.printf("[%s] Write (tx: %s) %s%n", Thread.currentThread().getName(),
                    TransactionSynchronizationManager.isActualTransactionActive(), data);
            })
            .taskExecutor(taskExecutor)
            .throttleLimit(poolSize)
            .build();
    }

}
