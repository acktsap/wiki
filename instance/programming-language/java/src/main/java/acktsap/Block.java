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

    public interface DangerousRunnable {
        void run() throws Exception;
    }

    private Block() {

    }
}
