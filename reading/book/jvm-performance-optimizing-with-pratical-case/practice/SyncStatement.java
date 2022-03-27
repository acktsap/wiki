public class SyncStatement {

  public static Object Lock = new Object();

  protected static class Never extends Thread {
    public void run() {
      while (true) {
        synchronized (SyncStatement.Lock) { // wait for SyncStatement.Lock to be unlocked
          System.out.println(Thread.currentThread().getName() + " action");
        }
      }
    }
  }

  protected static class Infinity extends Thread {
    public void run() {
      System.out.println("Never ending story");
      synchronized (SyncStatement.Lock) {
        // lock intinitely
        while (true) { }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    new Infinity().start();
    Thread.sleep(10);

    new Never().start();
    new Never().start();
    new Never().start();
  }

}