import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Simple test of ObservableSet - Page 319
// When we run this program, we don't get an exception; we get a deadlock. The
// background thread calls s.removeObserver, which attempts to lock observers,
// but it can't acquire the lock, because the main thread already has the lock. All the
// while, the main thread is waiting for the background thread to finish removing the
// observer, which explains the deadlock
//
// check : jcmd <pid> Thread.print
public class Test3 {
    public static void main(String[] args) {
        ObservableSet<Integer> set =new ObservableSet<>(new HashSet<>());

        // enter deadlock
        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        Future<Boolean> future = exec.submit(() -> s.removeObserver(this));
                        future.get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });
        for (int i = 0; i < 100; i++)
            set.add(i);
    }
}
