package acktsap.basic.stepconfig.flow.flowconfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class JdbcConfig {

    /*
        localhost:8080/h2-console
            user : "sa"
            pw   : ""

        들어가서 table 값 확인해봐
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setName("testdb")
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:sql/schema-h2-custom.sql") // init table with custom prefix
            .ignoreFailedDrops(true)
            .build();
    }
}
