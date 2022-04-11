package micrometer.timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class TimerSampleTest {
    public static void main(String[] args) throws Exception {
        Metrics.globalRegistry.add(new SimpleMeterRegistry());
        MeterRegistry registry = Metrics.globalRegistry;

        Timer.Sample sample = Timer.start(registry);

        Thread.sleep(500L);

        long nanoseconds = sample.stop(registry.timer("my.timer", "region", "test"));
        System.out.println("nanoseconds: " + nanoseconds);
    }
}
