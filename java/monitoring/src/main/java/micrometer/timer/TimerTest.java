package micrometer.timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;

public class TimerTest {
    public static void main(String[] args) {
        Metrics.globalRegistry.add(new SimpleMeterRegistry());
        MeterRegistry registry = Metrics.globalRegistry;

        Timer timer = Timer
            .builder("my.timer")
            .description("a description of what this timer does") // optional
            .tags("region", "test") // optional
            .register(registry);

        timer.record(() -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException ignored) {
            }
        });
        System.out.printf("totalTime: %s, snapshot: %s%n", timer.totalTime(TimeUnit.MILLISECONDS), timer.takeSnapshot());

        Runnable wrappped = timer.wrap(() -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException ignored) {
            }
        });
        System.out.printf("totalTime: %s, snapshot: %s%n", timer.totalTime(TimeUnit.MILLISECONDS), timer.takeSnapshot());

        wrappped.run();
        System.out.printf("totalTime: %s, snapshot: %s%n", timer.totalTime(TimeUnit.MILLISECONDS), timer.takeSnapshot());
    }
}
