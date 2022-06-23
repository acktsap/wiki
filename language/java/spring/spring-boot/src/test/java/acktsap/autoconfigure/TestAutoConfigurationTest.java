package acktsap.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(TestAutoConfiguration.class));

    @Test
    void registerBean() {
        contextRunner.run(context -> {
            assertThat(context).hasBean("testBean");
            Object actual = context.getBean("testBean");
            assertThat(actual).isEqualTo("testBean");
        });
    }

    @Test
    void registerBeanWhenAlreadyRegisteredBean() {
        contextRunner
            .withUserConfiguration(CustomConfiguration.class)
            .run(context -> {
                assertThat(context).hasBean("testBean");
                Object actual = context.getBean("testBean");
                assertThat(actual).isEqualTo("customBean");
            });
    }

    @Configuration
    static class CustomConfiguration {
        @Bean
        public String testBean() {
            return "customBean";
        }
    }
}
