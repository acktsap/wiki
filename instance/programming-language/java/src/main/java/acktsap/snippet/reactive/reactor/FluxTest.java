package acktsap.snippet.reactive.reactor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import acktsap.Block;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxTest {
    public static void main(String[] args) {
        Block.d("Flux filter", () -> {
            Flux<Integer> flux = Flux.range(1, 5)
                .publishOn(Schedulers.immediate());
            flux.filter(i1 -> i1 % 2 == 0)
                .subscribe(i -> System.out.printf("[%s] even: %d%n", Thread.currentThread(), i));
            flux.filter(i1 -> i1 % 2 == 1)
                .subscribe(i -> System.out.printf("[%s] odd: %d%n", Thread.currentThread(), i));
        });

        Block.d("Flux groupBy", () -> {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            Flux.range(1, 5)
                .publishOn(Schedulers.immediate())
                .groupBy(i -> i % 2, Function.identity())
                .subscribe(flux -> {
                    flux.subscribeOn(Schedulers.fromExecutor(executor))
                        .subscribe(item -> System.out.printf("[%s] item: %s%n", Thread.currentThread(), item));
                });

            executor.awaitTermination(3000L, TimeUnit.MILLISECONDS);
            executor.shutdownNow();
        });
    }
}
