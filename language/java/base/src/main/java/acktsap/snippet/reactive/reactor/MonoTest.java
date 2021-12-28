package acktsap.snippet.reactive.reactor;

import acktsap.Block;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonoTest {

    public static void main(String[] args) {
        Block.dt("Mono list run in parallel", () -> {
            System.out.println("start");
            List<Mono<Integer>> monos = IntStream.range(0, 3).boxed()
                .map(i -> Mono.fromSupplier(() -> {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.printf("[%s] %d%n", Thread.currentThread().getName(), i);
                        return i;
                    })
                )
                .collect(Collectors.toList());

            // monos.stream()
            //     .map(i -> i.publishOn(Schedulers.boundedElastic()));
            // System.out.println("mono created");
            //
            // Mono.zip(monos, Arrays::asList)
            //     .block();

            Flux.fromIterable(monos)
                .flatMap(i -> i)
                .publishOn(Schedulers.boundedElastic())
                .collectList()
                .block();
            // List<Integer> result = Flux.concat(monos)
            //     .subscribeOn(Schedulers.boundedElastic())
            //     // .parallel()
            //     // .runOn(Schedulers.boundedElastic())
            //     // .sequential()
            //     .collectList()
            //     .block();
        });
    }
}
