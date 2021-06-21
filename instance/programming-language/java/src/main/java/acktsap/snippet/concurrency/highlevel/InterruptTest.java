package acktsap.snippet.concurrency.highlevel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class InterruptTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.range(0, 2).forEach(i -> {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(10000L);
                        throw new IllegalStateException("InterruptedException이 호출되어야함");
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " - Interrupted!");
                        latch.countDown();
                    }
                });
            }
        );

        // shutdownNow로 인해 sleep인 녀석이 interrupt걸림
        executorService.shutdownNow();

        // 둘다 interrupt걸려서 countdown 호출됨
        latch.await();

        System.out.println("Latch: " + latch.getCount());
    }
}
