package acktsap.spring.boot.autoconfigure.lib;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import acktsap.spring.lib.Library;

@AutoConfiguration
public class LibraryAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Library library() {
        return new Library();
    }
}
