/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.synchronization;

import java.util.stream.IntStream;

public class SynchronizedMethod {

    protected static class SynchronizedCounter {

        private int c = 0;

        public synchronized void increment() {
            c++;
        }

        public synchronized void decrement() {
            c--;
        }

        public synchronized int value() {
            return c;
        }
    }

    /*
     * First, it is not possible for two invocations of synchronized methods on the same object to
     * interleave. When one thread is executing a synchronized method for an object, all other threads
     * that invoke synchronized methods for the same object block (suspend execution) until the first
     * thread is done with the object.
     *
     * Second, when a synchronized method exits, it automatically establishes a happens-before
     * relationship with any subsequent invocation of a synchronized method for the same object. This
     * guarantees that changes to the state of the object are visible to all threads.
     *
     * Synchronized method automatically acquires the intrinsic lock.
     */
    public static void main(String[] args) throws InterruptedException {
        final SynchronizedCounter counter = new SynchronizedCounter();
        final int tryCount = 1000;
        final Thread incrementer =
            new Thread(() -> IntStream.range(0, tryCount).forEach(i -> counter.increment()));
        final Thread decrementer =
            new Thread(() -> IntStream.range(0, tryCount).forEach(i -> counter.decrement()));

        incrementer.start();
        decrementer.start();

        incrementer.join();
        decrementer.join();

        System.out.format("After %s times operation: %s (0 intended)%n", tryCount,
            counter.value());
    }

}
