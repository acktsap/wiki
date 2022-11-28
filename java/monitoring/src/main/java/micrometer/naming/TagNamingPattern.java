package micrometer.naming;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class TagNamingPattern {
    public static void main(String[] args) {
        Metrics.addRegistry(new SimpleMeterRegistry());
        MeterRegistry registry = Metrics.globalRegistry;

        // database와 http request를 분리, '.'으로 나눠서 일관성 있게 하는게 좋음
        registry.counter("database.call", "db", "user1");
        registry.counter("database.call", "db", "user1"); // same one with previous
        registry.counter("database.call", "db", "user2");
        registry.counter("http.requests", "url", "/api/users");

        registry.getMeters()
            .forEach(meter -> System.out.println(meter.getId()));
    }
}
