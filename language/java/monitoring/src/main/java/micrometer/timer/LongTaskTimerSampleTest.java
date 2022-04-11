package micrometer.timer;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LongTaskTimerSampleTest {
    public static void main(String[] args) throws Exception {
        Metrics.globalRegistry.add(new SimpleMeterRegistry());
        MeterRegistry registry = Metrics.globalRegistry;

        LongTaskTimer scrapeTimer = LongTaskTimer.builder("scrape")
            .tags(List.of(Tag.of("wow", "her")))
            .register(registry);

        // same
        // LongTaskTimer scrapeTimer = registry.more()
        //     .longTaskTimer("scrape", List.of(Tag.of("wow", "her")));

        LongTaskTimer.Sample sample = scrapeTimer.start();

        Thread.sleep(100L);
        System.out.printf("[while1] active tasks: %s, duration: %sms (snapshot: %s)%n",
            scrapeTimer.activeTasks(), scrapeTimer.duration(TimeUnit.MILLISECONDS), scrapeTimer.takeSnapshot());

        Thread.sleep(500L);
        System.out.printf("[while2] active tasks: %s, duration: %sms (snapshot: %s)%n",
            scrapeTimer.activeTasks(), scrapeTimer.duration(TimeUnit.MILLISECONDS), scrapeTimer.takeSnapshot());

        long stop = sample.stop(); // stop recording
        System.out.printf("[returned stop] stop: %sns%n", stop);
        System.out.printf("[after stop] active tasks: %s, duration: %sms (snapshot: %s)%n",
            scrapeTimer.activeTasks(), scrapeTimer.duration(TimeUnit.MILLISECONDS), scrapeTimer.takeSnapshot()); // prints 0 since it's stopped
    }
}
