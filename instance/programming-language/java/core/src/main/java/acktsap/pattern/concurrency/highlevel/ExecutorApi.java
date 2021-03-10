/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.highlevel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExecutorApi {

    // Executor, a simple interface that supports launching new tasks.
    //
    // ExecutorService, a subinterface of Executor, which adds features that help manage the
    // lifecycle, both of the individual tasks and of the executor itself.
    //
    // ScheduledExecutorService, a subinterface of ExecutorService, supports future and/or periodic
    // execution of tasks.
    public static void main(String[] args) throws InterruptedException {
        int count = 5;
        long scheduledWait = 1500L;
        Object[][] parameters = {
            // single thread
            {Executors.newSingleThreadExecutor(), "newSingleThreadExecutor()"},

            // create on demand
            {Executors.newCachedThreadPool(), "newCachedThreadPool()"},

            // fixed pool
            // An important advantage of the fixed thread pool is that applications using it degrade
            // gracefully (prevent too many threads blocking entire system).
            {Executors.newFixedThreadPool(2), "newFixedThreadPool(2)"},
            {Executors.newFixedThreadPool(4), "newFixedThreadPool(4)"},

            // fixed pool with scheduling api
            {Executors.newScheduledThreadPool(2), "newScheduledThreadPool(2)"},
        };

        for (Object[] parameter : parameters) {
            ExecutorService executorService = (ExecutorService) parameter[0];
            String serviceName = (String) parameter[1];
            System.out.format("--- Running with %s ---%n", serviceName);

            // make runnable
            CountDownLatch latch = new CountDownLatch(count);
            Runnable task = () -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                }
                System.out.format("[Thread: %s] Countdown (current count: %d)%n",
                    Thread.currentThread().getName(),
                    latch.getCount());
                latch.countDown();
            };

            Stream<Runnable> taskStream = IntStream.range(0, count).mapToObj(i -> task);
            if (executorService instanceof ScheduledExecutorService) {
                System.out.format(
                    "ExecutorService is ScheduledExecutorService (each starts with delay (%dms)%n",
                    scheduledWait);
                ScheduledExecutorService scheduledExecutorService =
                    (ScheduledExecutorService) executorService;
                taskStream.forEach(
                    t -> scheduledExecutorService
                        .schedule(t, scheduledWait, TimeUnit.MILLISECONDS));
            } else {
                taskStream.forEach(t -> executorService.submit(t));
            }

            // wait for all the tasks to complete
            latch.await();

            // shut down
            executorService.shutdown();
        }

        System.out.println("Done");
    }

}
