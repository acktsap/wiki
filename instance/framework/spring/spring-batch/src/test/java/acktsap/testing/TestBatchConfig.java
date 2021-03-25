package acktsap.testing;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@TestConfiguration
public class TestBatchConfig {

    // datasource for test. should be separated with real one
    @Bean
    public DataSource testDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setName("testdb")
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
            .addScript("classpath:sql/test.sql")
            .build();
    }

    // turn off an AutoConfiguration of DefaultBatchConfigurer
    @Bean
    public BatchConfigurer batchConfigurer(@Qualifier("testDataSource") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource) {
            @Override
            protected JobRepository createJobRepository() throws Exception {
                JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
                factory.setDataSource(dataSource);
                factory.setTransactionManager(getTransactionManager());
                // JobExecution이 생성되었을때 tx가 있는지 체크하는 것을 disable
                // test method에서 Transactional을 사용할 수 있게 함
                factory.setValidateTransactionState(false);
                factory.afterPropertiesSet();
                return factory.getObject();
            }
        };
    }
}
