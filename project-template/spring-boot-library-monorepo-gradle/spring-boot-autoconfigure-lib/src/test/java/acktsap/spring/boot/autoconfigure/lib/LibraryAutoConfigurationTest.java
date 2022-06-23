package acktsap.spring.boot.autoconfigure.lib;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LibraryAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void load() {
        contextRunner.withConfiguration(AutoConfigurations.of(LibraryAutoConfiguration.class)).run(context -> {
        });
    }
}
