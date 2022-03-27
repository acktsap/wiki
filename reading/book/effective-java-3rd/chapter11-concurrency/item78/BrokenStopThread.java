import java.util.concurrent.*;

// Broken! - How long would you expect this program to run?  (Page 312)
// It never stops!!! Check it with jcmd {pid} Thread.print
// Liveness failure: the program fails to make progress.
public class BrokenStopThread {
    private static boolean stopRequested;

    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
            /*
            Expression hoisting is a form of such optimization

            Because of hoisting, it converted to

            if (!stopRequested)
                while (true)
                    i++;
            */
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
