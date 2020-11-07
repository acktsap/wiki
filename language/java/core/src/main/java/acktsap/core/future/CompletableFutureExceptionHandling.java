package acktsap.core.future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandling {

  public static void main(String[] args) throws Exception {
    // exceptionally 예제
    CompletableFuture<Object> interruptFuture = CompletableFuture.supplyAsync(() -> {
      throw new UnsupportedOperationException("Something failed");
    }).exceptionally(actual -> {
      // cause에 담겨있음
      System.out.println("Exception: " + actual);
      System.out.println("Exception cause: " + actual.getCause());
      return 111; // default
    });
    System.out.println(interruptFuture.get()); // 111
  }

}
