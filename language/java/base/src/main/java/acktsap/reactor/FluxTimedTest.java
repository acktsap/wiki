package acktsap.reactor;

import acktsap.Block;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Timed;
import reactor.util.function.Tuple2;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static acktsap.Block.print;

public class FluxTimedTest {
    public static void main(String[] args) {
        Block.d("elapsed", () -> {
            AtomicInteger count = new AtomicInteger(0);
            Flux<Tuple2<Long, Integer>> flux = Flux.<Integer>generate(sink -> {
                    if (count.get() < 3) {
                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextLong(10, 100));
                        } catch (InterruptedException ignored) {
                        }
                        sink.next(1);
                    } else {
                        sink.complete();
                    }
                    count.incrementAndGet();
                })
                .elapsed();
            flux.subscribe(i -> print("next: %s", i));
        });

        Block.d("timed", () -> {
            AtomicInteger count = new AtomicInteger(0);
            Flux<Timed<Integer>> flux = Flux.<Integer>generate(sink -> {
                    if (count.get() < 3) {
                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextLong(10, 100));
                        } catch (InterruptedException ignored) {
                        }
                        sink.next(1);
                    } else {
                        sink.complete();
                    }
                    count.incrementAndGet();
                })
                .timed();
            flux.subscribe(i -> print("next: %s", i));
        });

        Block.d("timestamp", () -> {
            AtomicInteger count = new AtomicInteger(0);
            Flux<Tuple2<Long, Integer>> flux = Flux.<Integer>generate(sink -> {
                    if (count.get() < 3) {
                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextLong(10, 100));
                        } catch (InterruptedException ignored) {
                        }
                        sink.next(1);
                    } else {
                        sink.complete();
                    }
                    count.incrementAndGet();
                })
                .timestamp();
            flux.subscribe(i -> print("next: %s", i));
        });
    }
}
