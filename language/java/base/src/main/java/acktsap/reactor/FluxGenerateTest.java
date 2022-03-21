package acktsap.reactor;

import acktsap.Block;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static acktsap.Block.threadName;

public class FluxGenerateTest {
    public static void main(String[] args) {
        Block.d("Create", () -> {
            Flux<Integer> flux = Flux.create(emitter -> {
                IntStream.range(1, 4).forEach(i -> {
                    System.out.printf("[%s] emit %d%n", threadName(), i); // run on main
                    emitter.next(i);
                });
                System.out.printf("[%s] emit complete%n", threadName()); // run on main
                emitter.complete();
            });

            flux.subscribeOn(Schedulers.boundedElastic())
                    .subscribe(i -> System.out.printf("[%s] subscribe %d%n", threadName(), i)); // run on pool
            Thread.sleep(1000L);
        });

        Block.d("Create on another thread", () -> {
            ExecutorService executorService = Executors.newWorkStealingPool();

            Flux<Integer> flux = Flux.<Integer>create(emitter -> {
                executorService.submit(() -> {
                    IntStream.range(1, 4).forEach(i -> {
                        System.out.printf("[%s] emit %d%n", threadName(), i);
                        emitter.next(i);
                    });
                    System.out.printf("[%s] emit complete%n", threadName());
                    emitter.complete();
                });

            });

            flux.subscribe(i -> System.out.printf("[%s] subscribe %d%n", threadName(), i));
            Thread.sleep(1000L);
        });

        Block.d("Generate", () -> {
            AtomicInteger count = new AtomicInteger(1);
            Flux<Integer> flux = Flux.generate(generator -> {
                int next = count.get();
                if (next <= 3) {
                    System.out.printf("[%s] next %d%n", threadName(), next); // run on main
                    generator.next(next);
                } else {
                    System.out.printf("[%s] completed%n", threadName()); // run on main
                    generator.complete();
                }
                count.incrementAndGet();
            });

            flux.subscribeOn(Schedulers.boundedElastic())
                    .subscribe(i -> System.out.printf("[%s] subscribe %d%n", threadName(), i)); // run on pool
            Thread.sleep(1000L);
        });

        Block.d("Generate with state", () -> {
            Flux<String> flux = Flux.generate(() -> 1, (state, sink) -> {
                if (state <= 3) {
                    String next = state.toString();
                    System.out.printf("[%s] next '%s'%n", threadName(), next); // run on main
                    sink.next(next);
                } else {
                    System.out.printf("[%s] completed%n", threadName()); // run on main
                    sink.complete();
                }
                return state + 1;
            });

            flux.publishOn(Schedulers.boundedElastic())
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe(i -> System.out.printf("[%s] subscribe '%s'%n", threadName(), i)); // run on pool
            Thread.sleep(1000L);
        });

        Block.d("Generate bypass", () -> {
            Flux<String> flux = Flux.generate(() -> 1, (state, sink) -> {
                if (state <= 3) {
                    String next = state.toString();
                    System.out.printf("[%s] next '%s'%n", threadName(), next); // run on main
                    sink.next(next);
                } else {
                    System.out.printf("[%s] completed%n", threadName()); // run on main
                    sink.complete();
                }
                return state + 1;
            });

            Flux<String> fluxAdaptor = Flux.create(sink -> {
                flux.doOnComplete(() -> {
                    sink.complete();
                }).doOnError(e -> {
                    sink.error(e);
                }).doOnCancel(() -> {
                    sink.complete();
                }).subscribe(next -> {
                    System.out.printf("[%s] next '%s'%n", threadName(), next); // run on main
                    sink.next(next);
                });
            });
            fluxAdaptor.publishOn(Schedulers.boundedElastic())
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe(i -> System.out.printf("[%s] subscribe '%s'%n", threadName(), i)); // run on pool
            Thread.sleep(1000L);
        });
    }
}
