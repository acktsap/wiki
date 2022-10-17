package acktsap.spring.boot.autoconfigure.lib;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;

import acktsap.spring.lib.Library;

public class LibraryAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(LibraryAutoConfiguration.class));

    @Test
    void load() {
        contextRunner.run(context -> {
            assertThat(context).hasBean("library");
        });
    }

    @Test
    void testNotLoadWhenAlreadyRegistered() {
        contextRunner.withUserConfiguration(TestConfiguration.class).run(context -> {
            assertThat(context).hasBean("library");
        });
    }

    static class TestConfiguration {
        @Bean
        public Library library() {
            return new Library();
        }
    }
}
