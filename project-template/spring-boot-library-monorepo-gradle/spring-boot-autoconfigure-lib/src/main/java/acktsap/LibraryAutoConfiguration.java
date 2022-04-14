package acktsap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LibraryAutoConfiguration {
    @Bean
    public Library library() {
        return new Library();
    }
}
