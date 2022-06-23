package acktsap.spring.boot.autoconfigure.lib;

import acktsap.spring.lib.Library;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LibraryAutoConfiguration {
    @Bean
    public Library library() {
        return new Library();
    }
}
