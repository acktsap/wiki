/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.snippet.concurrency.threadobject;

public class InterruptHandling {

    protected static class ThreadSleep implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                // Pause for 4 seconds
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    // We've been interrupted: no more messages.
                    System.out.format("Thread for %s interrupted%n", getClass().getName());
                    return;
                }
            }
            System.out.format("Thread for %s done%n", getClass().getName());
        }

    }

    protected static class ThreadLong implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < Long.MAX_VALUE; i++) {
                // Thread.interrupted() clear the interrupt flag
                if (Thread.interrupted()) {
                    System.out.format("Thread for %s interrupted%n", getClass().getName());
                    return;
                }
            }
            System.out.format("Thread for %s done%n", getClass().getName());
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final Thread sleepThread = new Thread(new ThreadSleep());
        final Thread longThread = new Thread(new ThreadLong());
        sleepThread.start();
        longThread.start();

        // not exactly sleep for 1.5 seconds (depends on os)
        Thread.sleep(1500L);
        sleepThread.interrupt();
        longThread.interrupt();
    }

}
