package acktsap.reactor;

import acktsap.Block;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class NullableStreamTest {
    public static void main(String[] args) {
        Block.d("null on flux", () -> {
            Flux<Object> flux = Flux.create(sink -> {
                sink.next(1);
                sink.next(2);
                sink.next(null); // 에러던짐
                sink.complete();
            });
        });

        Block.d("null on Mono", () -> {
            Mono<Integer> mono = Mono.create(sink -> sink.success(null));
            System.out.println(mono.block());

            Mono<Integer> emtpy = Mono.empty();
            System.out.println(emtpy.block());
        });
    }
}
