/*
 * @copyright defined in LICENSE.txt
 */

package api.concurrency.threadobject;

public class MakingThread {

  // recommanded, can be replaced with lambda
  protected static class ThreadImplements implements Runnable {

    @Override
    public void run() {
      System.out.format("Running %s%n", getClass().getName());
    }

  }

  protected static class ThreadExtends extends Thread {

    @Override
    public void run() {
      System.out.format("Running %s%n", getClass().getName());
    }

  }

  public static void main(String[] args) throws InterruptedException {
    final Thread implementsThread = new Thread(new ThreadImplements());
    final ThreadExtends extendsThread = new ThreadExtends();
    final Thread lambdaThread = new Thread(
        () -> System.out.format("Running %s%n", Thread.currentThread().getClass().getName()));

    implementsThread.start();
    extendsThread.start();
    lambdaThread.start();

    // main thread waits for threads to complete
    implementsThread.join();
    extendsThread.join();
    lambdaThread.join();

    // static methods of Thread contains methods which provide information about, or affect the
    // status of, the thread invoking the method
    System.out.format("Main thread %s finished%n", Thread.currentThread());
  }

}
