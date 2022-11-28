package micrometer.naming;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.List;

public class CommonTagsTest {
    public static void main(String[] args) {
        Metrics.addRegistry(new SimpleMeterRegistry());
        MeterRegistry registry = Metrics.globalRegistry;

        // register common tags
        // In spring, use MeterRegistryCustomizer to set common tag
        registry.config().commonTags("stack", "prod", "region", "us-east-1");
        registry.config().commonTags(List.of(Tag.of("stack", "prod"), Tag.of("region", "us-east-1"))); // equivalently

        registry.counter("database.call", "db", "user1");
        registry.counter("http.requests", "url", "/api/users");

        registry.getMeters()
            .forEach(meter -> System.out.println(meter.getId()));
    }
}
