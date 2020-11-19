package acktsap.core.future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

  public static void main(String[] args) throws Exception {
    /* creation */

    /* api */

    // exceptionally
    CompletableFuture<Object> exceptionally = CompletableFuture.supplyAsync(() -> {
      throw new UnsupportedOperationException("Something failed");
    }).exceptionally(actual -> {
      // cause에 담겨있음
      System.out.println("Exception: " + actual);
      System.out.println("Exception cause: " + actual.getCause());
      return "TT";
    });
    System.out.println("Value on exception : " + exceptionally.get());

    // join
    CompletableFuture<String> joinFuture = CompletableFuture.supplyAsync(() -> "joined");
    System.out.println("joinFuture.join() : " + joinFuture.join());

    // join stack trace
    CompletableFuture<String> joinError = CompletableFuture.supplyAsync(() -> {
      throw new IllegalStateException("Join error");
    });
    try {
      joinError.join();
    } catch (Exception e) {
      System.out.println("== Join error ==");
      // java.util.concurrent.CompletionException 제일 위. 실제 에러는 cause에 있음
      // 그런데 java.base/java.util.concurrent.CompletableFuture.encodeThrowable가 제일 위에 찍힘. join 이 아니고
      // java.util.concurrent.CompletableFuture.reportJoin 여기서
      // worker 결과에 CompletionException가 있으면 그대로 던져서 stack trace가 이어지지 않음
      // 뭔가 이상하지만 스펙임
      e.printStackTrace(System.out);
    }

    // get
    CompletableFuture<String> getFuture = CompletableFuture.supplyAsync(() -> "getted");
    System.out.println("getFuture.join() : " + getFuture.join());

    // get stack trace
    CompletableFuture<String> getError = CompletableFuture.supplyAsync(() -> {
      throw new IllegalStateException("Get error");
    });
    try {
      getError.get();
    } catch (Exception e) {
      System.out.println("== Get error ==");
      // java.util.concurrent.ExecutionException가 제일 위. cause에 있음
      e.printStackTrace(System.out);
    }
  }

}
