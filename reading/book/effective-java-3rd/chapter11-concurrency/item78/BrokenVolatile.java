import java.util.concurrent.*;

// The problem is that the increment operator (++) is not atomic. It performs two
// operations on the nextSerialNumber field: first it reads the value, and then it
// writes back a new value, equal to the old value plus one. If a second thread reads
// the field between the time a thread reads the old value and writes back a new one,
// the second thread will see the same value as the first and return the same serial
// number
// Safety failure : the program computes the wrong results.
public class BrokenVolatile {
    private static volatile int nextSerialNumber = 0;

    public static int generateSerialNumber() {
        return nextSerialNumber++;
    }

    public static void main(String[] args)
            throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Runnable runnable = () -> generateSerialNumber();
        for (int i = 0; i < 1000; ++i) {
            service.submit(runnable);
        }

        Thread.sleep(2000);

        // do not print 1000
        System.out.println("Final result : " + nextSerialNumber);
        service.shutdown();
    }
}
