package acktsap;

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
        long start = System.currentTimeMillis();
        try {
            System.out.printf("== %s ==%n", description);
            dangerousRunnable.run();
        } catch (Exception e) {
            RuntimeException throughPass = new RuntimeException(e.getMessage());
            throughPass.setStackTrace(e.getStackTrace());
            throw throughPass;
        } finally {
            long end = System.currentTimeMillis();
            System.out.printf("-- elapsed time: %s%n", end - start);
            System.out.println();
        }
    }

    public interface DangerousRunnable {
        void run() throws Exception;
    }

    private Block() {

    }
}
