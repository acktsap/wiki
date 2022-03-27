import java.util.concurrent.TimeUnit;

// Cooperative thread termination with a volatile field
// The actions of the synchronized methods in StopThread would be atomic
// even without synchronization. In other words, the synchronization on these
// methods is used solely for its communication effects, not for mutual exclusion
public class FixedStopThread2 {
    private static volatile boolean stopRequested;

    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
        });
        backgroundThread.start();

        TimeUnit.MILLISECONDS.sleep(500);
        stopRequested = true;
    }
}
