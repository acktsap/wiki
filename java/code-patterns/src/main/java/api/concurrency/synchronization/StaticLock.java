/*
 * @copyright defined in LICENSE.txt
 */

package api.concurrency.synchronization;

import java.util.stream.IntStream;

public class StaticLock {

  protected static class Counter {

    private static int c = 0;

    public synchronized static void increment() {
      c++;
    }

    public synchronized static int staticValue() {
      return c;
    }

    public int value() {
      return c;
    }

  }

  /*
   * The thread acquires the intrinsic lock for the Class object associated with the class. Thus
   * access to class's static fields is controlled by a lock that's distinct from the lock for any
   * instance of the class
   */
  public static void main(String[] args) throws InterruptedException {
    final int tryCount = 500;
    final Runnable runnable = () -> IntStream.range(0, tryCount).forEach(i -> Counter.increment());
    final Thread thread1 = new Thread(runnable);
    final Thread thread2 = new Thread(runnable);

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    System.out.format("After execution: %d (%d expected)%n", Counter.staticValue(), 2 * tryCount);
  }

}
