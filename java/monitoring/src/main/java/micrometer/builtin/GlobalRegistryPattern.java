package micrometer.builtin;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class GlobalRegistryPattern {
    static class MyComponent {
        Counter featureCounter = Metrics.counter("feature", "region", "test"); // keep meters in a field

        // wrap meter related operations
        void feature() {
            featureCounter.increment();
        }

        // wrap meter related operations
        double fetch() {
            return featureCounter.count();
        }

        // wrap meter related operations
        void feature2(String type) {
            Metrics.counter("feature.2", "type", type).increment();
        }
    }

    public static void main(String[] args) {
        Metrics.addRegistry(new SimpleMeterRegistry());

        var myComponent = new MyComponent();
        myComponent.feature();

        System.out.println("count: " + myComponent.fetch());
    }
}
