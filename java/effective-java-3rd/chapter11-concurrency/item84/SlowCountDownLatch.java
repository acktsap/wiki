import java.util.concurrent.CountDownLatch;

// Awful CountDownLatch implementation - busy-waits incessantly!  - Pages 336-7
public class SlowCountDownLatch {
    private int count;

    public SlowCountDownLatch(int count) {
        if (count < 0)
            throw new IllegalArgumentException(count + " < 0");
        this.count = count;
    }

    public void await() {
        while (true) {
            synchronized(this) {
                if (count == 0)
                    return;
            }
        }
    }
    public synchronized void countDown() {
        if (count != 0)
            count--;
    }

    public static void main(String[] args) {
        int count = 100;

        SlowCountDownLatch slow = new SlowCountDownLatch(count);
        long slowStart = System.nanoTime();
        for (int i = 0; i < count; ++i) {
            new Thread(() -> slow.countDown()).start();
        }
        slow.await();
        System.out.printf("Slow one: %dns\n", System.nanoTime() - slowStart);

        CountDownLatch fast = new CountDownLatch(count);
        long fastStart = System.nanoTime();
        for (int i = 0; i < count; ++i) {
            new Thread(() -> fast.countDown()).start();
        }
        System.out.printf("Faster one: %dns\n", System.nanoTime() - fastStart);
    }

}
