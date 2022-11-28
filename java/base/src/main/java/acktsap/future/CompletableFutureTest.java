package acktsap.future;

import acktsap.Block;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CompletableFutureTest {

    public static void main(String[] args) {
        Block.d("Exceptionally", () -> {
            CompletableFuture<Object> exceptionally = CompletableFuture.supplyAsync(() -> {
                throw new UnsupportedOperationException("failed");
            }).exceptionally(actual -> {
                System.out.println("exception: " + actual); // CompletionException
                System.out.println("exception cause: " + actual.getCause()); // UnsupportedOperationException
                return "alter";
            });

            System.out.println("value : " + exceptionally.get());
        });

        Block.d("Join", () -> {
            CompletableFuture<String> joinFuture = CompletableFuture.supplyAsync(() -> "value");
            System.out.println("joinFuture.join() : " + joinFuture.join());
        });

        Block.d("Join error stace trace", () -> {
            CompletableFuture<String> joinError = CompletableFuture.supplyAsync(() -> {
                throw new IllegalStateException("error");
            });
            try {
                joinError.join();
            } catch (Exception e) {
                System.out.println("join error");
                // java.util.concurrent.CompletionException 제일 위. 실제 에러는 cause에 있음
                // 그런데 java.base/java.util.concurrent.CompletableFuture.encodeThrowable가 제일 위에 찍힘. join 이 아니고
                // java.util.concurrent.CompletableFuture.reportJoin 여기서
                // worker 결과에 CompletionException가 있으면 그대로 던져서 stack trace가 이어지지 않음
                // 뭔가 이상하지만 스펙임
                e.printStackTrace(System.out);
            }
        });

        Block.d("Get", () -> {
            CompletableFuture<String> getFuture = CompletableFuture.supplyAsync(() -> "value");
            System.out.println("getFuture.get() : " + getFuture.get());
        });

        Block.d("Get stace trace", () -> {
            try {
                CompletableFuture<String> getError = CompletableFuture.supplyAsync(() -> {
                    throw new IllegalStateException("error");
                });
                getError.get();
            } catch (Exception e) {
                // java.util.concurrent.ExecutionException가 제일 위. 에러는 cause에 있음
                e.printStackTrace(System.out);
            }
        });

        Block.d("Run in parallel and waiting for all tasks to complete", () -> {
            long allStartTime = System.currentTimeMillis();
            CompletableFuture<?>[] futures = IntStream.range(0, Runtime.getRuntime().availableProcessors())
                .mapToObj($ -> CompletableFuture.runAsync(() -> {
                    try {
                        long startTime = System.currentTimeMillis();
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
                        System.out.printf("[%s] finished (elapsed time : %d)%n", Thread.currentThread(),
                            System.currentTimeMillis() - startTime);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }))
                .toArray(CompletableFuture[]::new);

            CompletableFuture<Void> future = CompletableFuture.allOf(futures);
            future.get(1500L, TimeUnit.MILLISECONDS);
            System.out.printf("All elasped time : %d%n", System.currentTimeMillis() - allStartTime); // 다 1초내외
        });
    }

}
