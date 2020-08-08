/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.liveness;

public class Deadlock {

  protected static class Friend {

    private final String name;

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public synchronized void bow(final Friend bower) {
      System.out.format("%s: %s has bowed to me!%n", this.name, bower.getName());

      // this never called since argument 'bower' is locked by calling 'bow' method
      bower.bowBack(this);
    }

    public synchronized void bowBack(final Friend bower) {
      System.out.format("%s: %s has bowed back to me!%n", this.name, bower.getName());
    }
  }

  public static void main(String[] args) throws InterruptedException {
    final Friend alphonse = new Friend("Alphonse");
    final Friend gaston = new Friend("Gaston");
    final Thread alphonseThread = new Thread(() -> alphonse.bow(gaston));
    final Thread gastonThread = new Thread(() -> gaston.bow(alphonse));

    alphonseThread.start();
    gastonThread.start();

    final long wait = 100L;
    alphonseThread.join(wait);
    gastonThread.join(wait);

    System.out.println("Stop by pressing stop button or check with 'jcmd <pid> Thread.print'");
  }

}
