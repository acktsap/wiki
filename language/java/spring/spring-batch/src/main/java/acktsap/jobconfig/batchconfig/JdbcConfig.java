package acktsap.jobconfig.batchconfig;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Register datasource for batch metadata.
 */
@Configuration
public class JdbcConfig {

    /*
      localhost:8080/h2-console
        user : "sa"
        pw : ""

      들어가서 table 값 확인해봐
     */
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
            .username("sa")
            .password("")
            .build();
    }
}
