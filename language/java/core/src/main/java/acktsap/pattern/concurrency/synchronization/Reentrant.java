/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.synchronization;

import java.util.stream.IntStream;

public class Reentrant {

    protected static class SynchronizedCounter {

        private int c = 0;

        public synchronized void dummy() {
            c++;
            decrement();
        }

        public synchronized void decrement() {
            c--;
        }

        public synchronized int value() {
            return c;
        }
    }

    /*
     * Recall that a thread cannot acquire a lock owned by another thread. But a thread can acquire a
     * lock that it already owns. Allowing a thread to acquire the same lock more than once enables
     * reentrant synchronization. This describes a situation where synchronized code, directly or
     * indirectly, invokes a method that also contains synchronized code, and both sets of code use
     * the same lock.
     */
    public static void main(String[] args) throws InterruptedException {
        final SynchronizedCounter counter = new SynchronizedCounter();
        final int tryCount = 1000;
        final Runnable runnable = () -> IntStream.range(0, tryCount).forEach(i -> counter.dummy());
        final Thread thread1 = new Thread(runnable);
        final Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.format("After %s times operation: %s (0 intended)%n", tryCount, counter.value());
    }

}
