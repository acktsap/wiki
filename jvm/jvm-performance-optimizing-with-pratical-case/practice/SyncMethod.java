public class SyncMethod {

  public static LockObject Lock = new LockObject();

  protected static class LockObject {

    public synchronized void neverEndingStory() {
      while (true) {
      }
    }

    public synchronized void shouldWait() {}

  }

  protected static class Never extends Thread {

    public void run() {
      System.out.println("I have to wait forever..");
      SyncMethod.Lock.shouldWait();
      System.out.println("What? i don't have to wait?");
    }

  }

  protected static class Infinity extends Thread {

    public void run() {
      System.out.println("Never ending story");
      SyncMethod.Lock.neverEndingStory();
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
