public class WaitNotify {

  public static Object Lock = new Object();

  protected static class Wait extends Thread {
    public void run() {
      while (true) {
        synchronized (WaitNotify.Lock) {
          try {
            // wait for notify
            WaitNotify.Lock.wait();
          } catch (Exception e) { }

          System.out.println(Thread.currentThread().getName() + " action");
        }
      }
    }
  }

  protected static class Notify extends Thread {
    public void run() {
      while (true) {
        System.out.println("Doing long operation.. ");
        for (int i = 0; i < 90_000_000; ++i) { }

        synchronized (WaitNotify.Lock) {
          // notify any WAITING thread
          WaitNotify.Lock.notify();
        }

        System.out.println("done");
      }
    }
  }

  public static void main(String[] args) throws Exception {
    new Notify().start();
    Thread.sleep(10);
    new Wait().start();
    new Wait().start();
    new Wait().start();
  }

}
