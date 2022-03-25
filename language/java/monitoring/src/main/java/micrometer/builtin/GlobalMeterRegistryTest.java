package micrometer.builtin;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class GlobalMeterRegistryTest {
    public static void main(String[] args) {
        SimpleMeterRegistry simple = new SimpleMeterRegistry();
        Metrics.addRegistry(simple); // add simple to global CompositeRegistry

        Counter counter = Metrics.counter("counter");
        counter.increment();

        System.out.println("global counter: " + Metrics.globalRegistry.get("counter").counter().count());
        System.out.println("simple counter: " + simple.get("counter").counter().count());
    }
}
