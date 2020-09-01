package acktsap.pattern.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CompletableFutureApi {

    public static void main(String[] args) throws Exception {
        // waiting for all tasks to complete
        long start = System.currentTimeMillis();
        CompletableFuture<?>[] futures = IntStream
            .range(0, Runtime.getRuntime().availableProcessors())
            .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.printf("[%s] started: %d%n", Thread.currentThread(),
                        System.currentTimeMillis());
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                    System.out.printf("[%s] finished: %d%n", Thread.currentThread(),
                        System.currentTimeMillis());
                    return "";
                } catch (Exception e1) {
                    throw new IllegalStateException(e1);
                }
            }))
            .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).get(1500L, TimeUnit.MILLISECONDS);
        System.out.println("Elasped time: " + (System.currentTimeMillis() - start));
    }

}
