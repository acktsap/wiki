package micrometer.timer;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LongTaskTimerTest {
    public static void main(String[] args) throws Exception {
        Metrics.globalRegistry.add(new SimpleMeterRegistry());
        MeterRegistry registry = Metrics.globalRegistry;

        LongTaskTimer scrapeTimer = registry.more()
            .longTaskTimer("scrape", List.of(Tag.of("wow", "her")));

        scrapeTimer.record(() -> {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ignored) {
            }
            System.out.printf("[while1] active tasks: %s, duration: %sms (snapshot: %s)%n",
                scrapeTimer.activeTasks(), scrapeTimer.duration(TimeUnit.MILLISECONDS), scrapeTimer.takeSnapshot());

            try {
                Thread.sleep(500L);
            } catch (InterruptedException ignored) {
            }
            System.out.printf("[while2] active tasks: %s, duration: %sms (snapshot: %s)%n",
                scrapeTimer.activeTasks(), scrapeTimer.duration(TimeUnit.MILLISECONDS), scrapeTimer.takeSnapshot());
        });

        // active: 0, duration: 0.0ms
        System.out.printf("[after] active tasks: %s, duration: %sms (snapshot: %s)%n",
            scrapeTimer.activeTasks(), scrapeTimer.duration(TimeUnit.MILLISECONDS), scrapeTimer.takeSnapshot());
    }
}
