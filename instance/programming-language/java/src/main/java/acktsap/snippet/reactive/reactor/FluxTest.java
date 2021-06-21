package acktsap.snippet.reactive.reactor;

import acktsap.Block;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxTest {
    public static void main(String[] args) {
        Block.d("Flux filter", () -> {
            Flux<Integer> flux = Flux.range(1, 5)
                .publishOn(Schedulers.immediate());
            flux.filter(i1 -> i1 % 2 == 0)
                .publishOn(Schedulers.immediate())
                .subscribe(i -> System.out.printf("[%s] even: %d%n", Thread.currentThread(), i));
            flux.filter(i1 -> i1 % 2 == 1)
                .publishOn(Schedulers.immediate())
                .subscribe(i -> System.out.printf("[%s] odd: %d%n", Thread.currentThread(), i));
        });
    }
}
