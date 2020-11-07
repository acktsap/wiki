/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.liveness;

public class Starvation {

  public static void main(String[] args) throws InterruptedException {
    final Object resource = new Object();

    final int tryCount = 5;
    final Runnable greedyRun = () -> {
      for (int i = 0; i < tryCount; ++i) {
        synchronized (resource) {
          try {
            Thread.sleep(1000L);
            System.out.format("Greedy operation complete (thread: %s)%n",
                Thread.currentThread().getName());
          } catch (InterruptedException e) {
          }
        }
      }
    };
    final Runnable nonGreedyRun = () -> {
      for (int i = 0; i < tryCount; ++i) {
        synchronized (resource) {
          System.out.format("Non-Greedy operation complete (thread: %s)%n",
              Thread.currentThread().getName());
        }
      }
    };

    final Thread greedy = new Thread(greedyRun);
    final Thread nonGreedy1 = new Thread(nonGreedyRun);
    final Thread nonGreedy2 = new Thread(nonGreedyRun);

    greedy.start();
    nonGreedy1.start();
    nonGreedy2.start();

    greedy.join();
    nonGreedy1.join();
    nonGreedy2.join();
  }

}
