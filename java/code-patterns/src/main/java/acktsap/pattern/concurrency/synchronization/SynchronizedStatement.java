/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.synchronization;

import java.util.stream.IntStream;

public class SynchronizedStatement {

  protected static class MultiCounter {

    private int c1 = 0;
    private int c2 = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
      synchronized (lock1) {
        c1++;
      }
    }

    public void inc2() {
      synchronized (lock2) {
        c2++;
      }
    }

    public int get1() {
      synchronized (lock1) {
        return c1;
      }
    }

    public int get2() {
      synchronized (lock2) {
        return c2;
      }
    }

  }

  /*
   * Instead of using synchronized methods or otherwise using the lock associated with this, we
   * create two objects solely to provide locks.
   */
  public static void main(String[] args) throws InterruptedException {
    final int tryCount = 1000;
    final MultiCounter multiCounter = new MultiCounter();

    final Thread c1Incrementer =
        new Thread(() -> IntStream.range(0, tryCount).forEach(i -> multiCounter.inc1()));
    final Thread c2Incrementer =
        new Thread(() -> IntStream.range(0, tryCount).forEach(i -> multiCounter.inc2()));

    c1Incrementer.start();
    c2Incrementer.start();

    c1Incrementer.join();
    c2Incrementer.join();

    System.out.format("After operation c1=%d, c2=%d (both %d expected)%n", multiCounter.get1(),
        multiCounter.get2(), tryCount);
  }

}
