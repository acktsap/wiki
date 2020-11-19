package acktsap.core.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CompletableFuturePattern {

  public static void main(String[] args) throws Exception {
    // waiting for all tasks to complete
    long allStartTime = System.currentTimeMillis();
    CompletableFuture<?>[] futures = IntStream.range(0, Runtime.getRuntime().availableProcessors())
        .mapToObj(i -> CompletableFuture.runAsync(() -> {
          try {
            long startTime = System.currentTimeMillis();
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            System.out.printf("[%s] finished (elapsed time : %d)%n", Thread.currentThread(),
                System.currentTimeMillis() - startTime);
          } catch (Exception e) {
            throw new IllegalStateException(e);
          }
        }))
        .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).get(1500L, TimeUnit.MILLISECONDS);
    System.out.printf("All elasped time : %d%n", System.currentTimeMillis() - allStartTime);
  }

}
