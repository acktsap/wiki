import java.util.concurrent.*;

// Simple framework for timing concurrent execution 327
// Easier then using "wait and notify"

// All of the worker threads ready themselves to run the action before the timer thread
// starts the clock. When the last worker thread is ready to run the action, the timer
// thread “fires the starting gun,” allowing the worker threads to perform the action.
// As soon as the last worker thread finishes performing the action, the timer thread
// stops the clock.
public class ConcurrentTimer {
    private ConcurrentTimer() { } // Noninstantiable

    public static long time(Executor executor, int concurrency,
                            Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done    = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();    // Tell timer we're done
                }
            });
        }

        // NOTE: use System.nanotime() for internal timing
        ready.await();     // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off!
        done.await();        // Wait for all workers to finish
        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) throws Exception {
        int poolSize = 4;
        // int poolSize = 2;  // never finishes : "thread starvation deadlock"
        int concurrency = 4;

        ExecutorService executors = Executors.newFixedThreadPool(poolSize);
        Runnable action = () -> System.out.println("Thread=" + Thread.currentThread());
        long elapsed = time(executors, concurrency, action);
        System.out.printf("Elapsed time: %dns\n", elapsed);
        executors.shutdown();
    }

}
