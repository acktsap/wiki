package acktsap.spring.boot.autoconfigure.lib;

import acktsap.spring.lib.Library;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class LibraryAutoConfiguration {
    @Bean
    public Library library() {
        return new Library();
    }
}
