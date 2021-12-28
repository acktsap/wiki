package acktsap;

import java.time.Duration;
import java.time.LocalTime;

public final class Block {
    public static void d(String description, DangerousRunnable dangerousRunnable) {
        try {
            System.out.printf("== %s ==%n", description);
            dangerousRunnable.run();
            System.out.println();
        } catch (Exception e) {
            RuntimeException throughPass = new RuntimeException(e.getMessage());
            throughPass.setStackTrace(e.getStackTrace());
            throw throughPass;
        }
    }

    public static void dt(String description, DangerousRunnable dangerousRunnable) {
        LocalTime start = LocalTime.now();
        try {
            System.out.printf("== %s ==%n", description);
            System.out.printf("-- start time: %s%n", start);
            dangerousRunnable.run();
        } catch (Exception e) {
            RuntimeException throughPass = new RuntimeException(e.getMessage());
            throughPass.setStackTrace(e.getStackTrace());
            throw throughPass;
        } finally {
            LocalTime end = LocalTime.now();
            System.out.printf("-- end time: %s (elapsed: %sms)%n", end, Duration.between(start, end).toMillis());
            System.out.println();
        }
    }

    public interface DangerousRunnable {
        void run() throws Exception;
    }

    private Block() {

    }
}
