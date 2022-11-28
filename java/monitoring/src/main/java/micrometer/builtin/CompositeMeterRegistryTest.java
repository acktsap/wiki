package micrometer.builtin;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class CompositeMeterRegistryTest {
    public static void main(String[] args) {
        CompositeMeterRegistry composite = new CompositeMeterRegistry();

        Counter counter = composite.counter("counter");
        counter.increment(); // no registry in composite, so doesn't increment it
        System.out.println("composite counter (first increment): " + composite.get("counter").counter().count());

        SimpleMeterRegistry simple = new SimpleMeterRegistry();
        composite.add(simple); // counter is registered to simpleRegistry
        System.out.println("composite counter (simple registered): " + composite.get("counter").counter().count());
        System.out.println("simple counter (simple registered): " + simple.get("counter").counter().count());

        counter.increment();
        System.out.println("composite counter (last increment): " + composite.get("counter").counter().count());
        System.out.println("simple counter (last increment): " + simple.get("counter").counter().count());
    }
}
