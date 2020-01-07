/*
 * @copyright defined in LICENSE.txt
 */

package api.concurrency.synchronization;

import java.util.stream.IntStream;

public class Interference {

  protected static class Counter {

    private int c = 0;

    public void increment() {
      c++;
    }

    public void decrement() {
      c--;
    }

    public int value() {
      return c;
    }

  }

  public static void main(String[] args) throws InterruptedException {
    final Counter counter = new Counter();
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
