import java.util.concurrent.TimeUnit;

// Properly synchronized cooperative thread termination
// Use synchronized to stop requested
//
// Note that both the write method (requestStop) and the read method (stop-
// Requested) are synchronized. They should be both synchronized
public class FixedStopThread1 {
    private static boolean stopRequested;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested())
                i++;
        });
        backgroundThread.start();

        TimeUnit.MILLISECONDS.sleep(500);
        requestStop();
    }
}  
