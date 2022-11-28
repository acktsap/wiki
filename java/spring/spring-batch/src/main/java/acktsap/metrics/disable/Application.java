package acktsap.metrics.disable;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    Built-in metrics

    https://docs.spring.io/spring-batch/docs/current/reference/html/monitoring-and-metrics.html#monitoring-and-metrics
 */
@SpringBootApplication
public class Application {
    // db: localhost:8080/h2-console (user : "sa", pw: "")
    public static void main(String[] args) {
        // register at least one registry
        Metrics.addRegistry(new SimpleMeterRegistry());

        MeterRegistry meterRegistry = Metrics.globalRegistry;

        // disable spring batch meters
        meterRegistry.config().meterFilter(
            MeterFilter.denyNameStartsWith("spring.batch") // deny all
            // MeterFilter.denyNameStartsWith("spring.batch.item") // deny xxx.item only
        );

        SpringApplication.run(Application.class, args);

        // print nothing
        meterRegistry.getMeters()
            .forEach(meter -> System.out.printf("id: %s, measures: %s%n", meter.getId(), meter.measure()));
    }
}
