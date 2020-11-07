/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.highlevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws Exception {
        int nCore = Runtime.getRuntime().availableProcessors();
        // ExecutorService executors = Executors.newFixedThreadPool(nCore * 2);
           ExecutorService executors = Executors.newFixedThreadPool(nCore);
        Callable<Integer> task = () -> {
            for (int i = 0; i < 100_000; ++i) {
                System.out.println("IO");
            }
            return 0;
        };
        long start = System.nanoTime();
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < nCore * 2; ++i) {
            tasks.add(task);
        }
        executors.invokeAll(tasks);
        System.out.printf("Running: %dms%n", (System.nanoTime() - start) / 1_000_000);
        executors.shutdown();
    }

}
