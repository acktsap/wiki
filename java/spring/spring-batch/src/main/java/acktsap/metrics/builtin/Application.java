package acktsap.metrics.builtin;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    Built-in metrics

    https://docs.spring.io/spring-batch/docs/current/reference/html/monitoring-and-metrics.html#monitoring-and-metrics
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // register at least one registry
        Metrics.addRegistry(new SimpleMeterRegistry());

        SpringApplication.run(Application.class, args);

        MeterRegistry meterRegistry = Metrics.globalRegistry;
        meterRegistry.getMeters()
            .forEach(meter -> System.out.printf("id: %s, mersures: %s%n", meter.getId(), meter.measure()));
    }
}
