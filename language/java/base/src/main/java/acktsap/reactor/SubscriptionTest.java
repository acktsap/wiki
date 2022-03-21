package acktsap.reactor;

import acktsap.Block;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

import static acktsap.Block.threadName;

public class SubscriptionTest {
    public static void main(String[] args) {
        Block.d("Subscribe", () -> {
            Flux<Integer> ints = Flux.range(1, 3);
            ints.subscribe(i -> System.out.printf("[%s] subscribe %d%n", threadName(), i));
        });

        Block.d("Subscribe with errorConsumer", () -> {
            Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 2) return i;
                    throw new RuntimeException("Got to 3");
                });
            ints.subscribe(
                i -> System.out.printf("[%s] subscribe %d%n", threadName(), i),
                e -> System.out.printf("[%s] error: %s%n", threadName(), e),
                () -> System.out.printf("[%s] done%n", threadName())
            );
        });

        Block.d("Subscribe with complete completeConsumer", () -> {
            Flux<Integer> ints = Flux.range(1, 3);
            ints.subscribe(
                i -> System.out.printf("[%s] subscribe %d%n", threadName(), i),
                e -> System.out.printf("[%s] error: %s%n", threadName(), e),
                () -> System.out.printf("[%s] done%n", threadName())
            );
        });

        Block.d("toIterable", () -> {
            Flux<Integer> ints = Flux.range(1, 3);
            ints.toIterable(1)
                .forEach(i -> System.out.printf("[%s] next %d%n", threadName(), i));
        });

        Block.d("Dispose", () -> {
            // print nothing
            Flux<Integer> ints = Flux.range(1, 3);
            Disposable disposable = ints
                .subscribeOn(Schedulers.boundedElastic())
                .doOnTerminate(() -> System.out.printf("[%s] terminated%n", threadName()))
                .doOnCancel(() -> System.out.printf("[%s] canceled%n", threadName()))
                .subscribe(i -> System.out.printf("[%s] subscribe %d%n", threadName(), i));
            disposable.dispose();
        });

        Block.d("Dispose on multiple subscription", () -> {
            Flux<Integer> ints = Flux.create(emitter -> {
                    IntStream.range(1, 4).forEach(i -> {
                        System.out.printf("[%s] next %d%n", threadName(), i);
                        emitter.next(i);
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            throw new IllegalArgumentException(e);
                        }
                    });
                    emitter.complete();
                }
            );

            Disposable disposable1 = ints
                .subscribeOn(Schedulers.boundedElastic())
                .doOnTerminate(() -> System.out.printf("[%s] terminated (1)%n", threadName()))
                .doOnCancel(() -> System.out.printf("[%s] canceled (1)%n", threadName()))
                .subscribe(i -> System.out.printf("[%s] subscribe %d (1)%n", threadName(), i));

            Disposable disposable2 = ints
                .subscribeOn(Schedulers.boundedElastic())
                .doOnTerminate(() -> System.out.printf("[%s] terminated (2)%n", threadName()))
                .doOnCancel(() -> System.out.printf("[%s] canceled (2)%n", threadName()))
                .subscribe(i -> System.out.printf("[%s] subscribe %d (2)%n", threadName(), i));

            // subscription 1 prints 1 only
            Thread.sleep(500L);
            disposable1.dispose();

            // subscription 2 prints all
            Thread.sleep(5000L);
        });
    }
}
