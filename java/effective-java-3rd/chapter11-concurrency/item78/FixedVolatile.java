import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

// Use atomic long in incrementational case
public class FixedVolatile {
    private static final AtomicLong nextSerialNum = new AtomicLong();

    public static long generateSerialNumber() {
        return nextSerialNum.getAndIncrement();
    }

    public static void main(String[] args)
            throws InterruptedException {
        Runnable runnable = () -> generateSerialNumber();
        for (int i = 0; i < 1000; ++i) {
            new Thread(runnable).start();
        }

        TimeUnit.MILLISECONDS.sleep(300);

        // prints 1000
        System.out.println("Final result : " + nextSerialNum.get());
    }
}
