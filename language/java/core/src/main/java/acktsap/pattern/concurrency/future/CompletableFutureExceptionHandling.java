package acktsap.pattern.concurrency.future;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandling {

    public static void main(String[] args) throws Exception {
        // exceptionally 예제
        UnsupportedOperationException expected = new UnsupportedOperationException();
        CompletableFuture<Object> interruptFuture = CompletableFuture.supplyAsync(() -> {
            throw expected;
        }).exceptionally(actual -> {
            // cause에 담겨있음
            then(actual.getCause()).isEqualTo(expected);
            return 111; // default
        });
        System.out.println(interruptFuture.get()); // 111
    }

}
