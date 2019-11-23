/*
 * @copyright defined in LICENSE.txt
 */

package api.concurrency.liveness;

public class LiveLock {

  protected static class Friend {

    private final String name;

    private boolean responsed = false;

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public void bow(final Friend bower) {
      System.out.format("%s: %s has bowed to me!%n", this.name, bower.getName());

      while (!bower.hasResponsed());

      // this never called since argument 'bower' is waiting for 'this' to response
      bower.bowBack(this);
    }

    public void bowBack(final Friend bower) {
      System.out.format("%s: %s has bowed back to me!%n", this.name, bower.getName());
      this.responsed = true;
    }

    public boolean hasResponsed() {
      return this.responsed;
    }

  }

  public static void main(String[] args) throws InterruptedException {
    final Friend alphonse = new Friend("Alphonse");
    final Friend gaston = new Friend("Gaston");
    final Thread alphonseThread = new Thread(() -> alphonse.bow(gaston));
    final Thread gastonThread = new Thread(() -> gaston.bow(alphonse));

    alphonseThread.start();
    gastonThread.start();

    final long wait = 1500L;
    alphonseThread.join(wait);
    gastonThread.join(wait);

    System.out.println("Stop by pressing stop button or check with 'jcmd <pid> Thread.print'");
  }

}
