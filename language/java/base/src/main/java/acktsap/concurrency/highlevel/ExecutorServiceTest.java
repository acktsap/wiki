package acktsap.concurrency.highlevel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // submit with value (value does nothing. just return value when complete)
        Future<Integer> future1 = executorService.submit(() -> {
            System.out.println("run thread");
        }, 111);
        System.out.println(future1.get()); // 111

        executorService.shutdownNow();
        executorService.awaitTermination(3000L, TimeUnit.SECONDS);
    }
}
