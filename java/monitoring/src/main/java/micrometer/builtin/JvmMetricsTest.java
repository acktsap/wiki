package micrometer.builtin;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.jvm.*;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    See https://micrometer.io/docs/ref/jvm

    For metric name details, see bindTo method.
 */
public class JvmMetricsTest {
    public static void main(String[] args) {
        Metrics.addRegistry(new SimpleMeterRegistry());
        CompositeMeterRegistry registry = Metrics.globalRegistry;

        // Gauges loaded and unloaded classes.
        new ClassLoaderMetrics().bindTo(registry);
        double classLoaded = registry.get("jvm.classes.loaded")
            .gauge()
            .value();
        double classUnloaded = registry.get("jvm.classes.unloaded")
            .functionCounter()
            .count();
        System.out.println("== ClassLoaderMetrics ==");
        System.out.println("classLoaded: " + classLoaded);
        System.out.println("classUnloaded: " + classUnloaded);
        System.out.println();

        // Gauges buffer and memory pool utilization.
        new JvmMemoryMetrics().bindTo(registry);
        double bufferCount = registry.get("jvm.buffer.count")
            .gauge()
            .value();
        double bufferMemoryUsed = registry.get("jvm.buffer.memory.used")
            .gauge()
            .value();
        double bufferTotalCapacity = registry.get("jvm.buffer.total.capacity")
            .gauge()
            .value();
        double memoryUsed = registry.get("jvm.memory.used")
            .gauge()
            .value();
        double memoryCommitted = registry.get("jvm.memory.committed")
            .gauge()
            .value();
        double memoryMax = registry.get("jvm.memory.max")
            .gauge()
            .value();
        System.out.println("== JvmMemoryMetrics ==");
        System.out.println("bufferCount: " + bufferCount);
        System.out.println("bufferMemoryUsed: " + bufferMemoryUsed);
        System.out.println("bufferTotalCapacity: " + bufferTotalCapacity);
        System.out.println("memoryUsed: " + memoryUsed);
        System.out.println("memoryCommitted: " + memoryCommitted);
        System.out.println("memoryMax: " + memoryMax);
        System.out.println();

        // Gauges max and live data size, promotion and allocation rates, and times GC pauses (or concurrent phase time in the case of CMS).
        new JvmGcMetrics().bindTo(registry);
        // ...

        // Gauges current CPU total and load average.
        new ProcessorMetrics().bindTo(registry);
        // ...

        // Gauges thread peak, number of daemon threads, and live threads.
        new JvmThreadMetrics().bindTo(registry);
        // ...

        // Gauges thread peak, number of daemon threads, and live threads.
        ExecutorService executor = Executors.newFixedThreadPool(3);
        new ExecutorServiceMetrics(executor, "service", List.of()).bindTo(registry);
        double executorActive = registry.get("executor.active")
            .gauge()
            .value();
        System.out.println("== ExecutorServiceMetrics ==");
        System.out.println("executorActive: " + executorActive);
        System.out.println();
    }
}
