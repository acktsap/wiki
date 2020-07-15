/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.highlevel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoin {

  protected static class TestAction extends RecursiveAction {

    protected final int count;

    public TestAction(int count) {
      this.count = count;
    }

    @Override
    protected void compute() {
      if (0 < count) {
        System.out.printf("[%s] Split task (count: %d)%n", Thread.currentThread(), count);
        TestAction action1 = new TestAction(count - 1);
        TestAction action2 = new TestAction(count - 1);
        invokeAll(action1, action2);
      }
      System.out.printf("[%s] Task completed%n", Thread.currentThread());
    }
  }

  // One such implementation, introduced in Java SE 8, is used by the java.util.Arrays class for its
  // parallelSort() methods. These methods are similar to sort(), but leverage concurrency via the
  // fork/join framework.
  //
  // Another implementation of the fork/join framework is used by methods in the java.util.streams
  // package, which is part of Project Lambda scheduled for the Java SE 8 release
  public static void main(String[] args) {
    final ForkJoinPool forkJoinPool =
        new ForkJoinPool(2 * Runtime.getRuntime().availableProcessors());
    System.out.println("Parallelism level size: " + forkJoinPool.getParallelism());
    forkJoinPool.invoke(new TestAction(3));
  }

}
