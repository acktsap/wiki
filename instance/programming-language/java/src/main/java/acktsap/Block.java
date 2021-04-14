package acktsap;

public final class Block {
    private final String description;

    public static Block d(String description) {
        return new Block(description);
    }

    private Block(String description) {
        this.description = description;
    }

    public void p(DangerousRunnable pattern) {
        try {
            System.out.printf("== %s ==%n", description);
            pattern.run();
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
}
