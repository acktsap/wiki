package acktsap.pattern.dbread;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class JdbcConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setName("testdb")
            .setType(EmbeddedDatabaseType.H2)
            // spring batch automatically init meta sql for embeded db
            // .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
            .addScript("classpath:sql/test.sql")
            .build();
    }
}
