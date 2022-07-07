package acktsap.spring.boot.autoconfigure.lib;

import acktsap.spring.lib.Library;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class LibraryAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Library library() {
        return new Library();
    }
}
