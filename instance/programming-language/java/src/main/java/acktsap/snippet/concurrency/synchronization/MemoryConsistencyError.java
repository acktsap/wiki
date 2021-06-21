/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.snippet.concurrency.synchronization;

import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryConsistencyError {

    protected static class Counter {

        private int c = 0;

        public void set() {
            c = 1;
        }

        public int value() {
            return c;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int tryCount = 0;
        while (true) {
            tryCount++;
            final Counter counter = new Counter();
            final AtomicBoolean flag = new AtomicBoolean();
            final Thread operator = new Thread(() -> counter.set());
            final Thread reader = new Thread(() -> {
                if (0 == counter.value()) {
                    flag.set(true);
                }
            });

            operator.start();
            reader.start();

            operator.join();
            reader.join();

            if (true == flag.get()) {
                System.out
                    .println("Memory consistency error occured (no happen-before relationships)");
                break;
            }
        }

        System.out.format("Finished with try count %s %n", tryCount);
    }

}
