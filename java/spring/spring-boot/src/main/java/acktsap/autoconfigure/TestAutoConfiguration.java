package acktsap.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@AutoConfiguration
public class TestAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public String testBean() {
        return "testBean";
    }
}
