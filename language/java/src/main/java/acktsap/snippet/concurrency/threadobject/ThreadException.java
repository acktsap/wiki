package acktsap.snippet.concurrency.threadobject;

public class ThreadException {
    public static void main(String[] args) {
        var thread = new Thread(() -> {
            throw new RuntimeException();
        });

        thread.start();

        System.out.println("finished");
    }
}
