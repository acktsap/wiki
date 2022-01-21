/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.concurrency.highlevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import acktsap.Block;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        Block.d("Thread safe upsert with init value", () -> {
            ExecutorService executorService = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

            ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (int i = 0; i < 1000; ++i) {
                String key = Integer.toString(i % 2);
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        // insert if not exists
                        concurrentMap.putIfAbsent(key, 0);

                        // if present, update it
                        // compute로 해도 괜찮지만 warning이 나서 computeIfPresent 사용
                        // 이 라인 put보다 위로 가면 불규칙적으로 나옴
                        // 왜냐하면 두개 이상의 thread가 computeIfPresent를 동시에 넘어가게 되면
                        // putIfAbsent는 한개만 실행되게 되고 나머지 하나는
                        // computeIfPresent 가 실행되어야 하는데 미실행되어서임
                        concurrentMap.computeIfPresent(key, (k, v) -> v + 1);
                    }
                    , executorService);
                futures.add(future);
            }
            futures.forEach(CompletableFuture::join);

            concurrentMap.forEach((k, v) -> System.out.printf("key: %s, value: %s%n", k, v));

            executorService.shutdown();
        });

        Block.d("Thread safe upsert without init value", () -> {
            ExecutorService executorService = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

            ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (int i = 0; i < 1000; ++i) {
                String key = Integer.toString(i % 2);
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        int item = 1;
                        // putIfAbsent returns null if not present
                        if (concurrentMap.putIfAbsent(key, item) != null) {
                            concurrentMap.computeIfPresent(key, (k, v) -> v + 1);
                        }
                    }
                    , executorService);
                futures.add(future);
            }
            futures.forEach(CompletableFuture::join);

            concurrentMap.forEach((k, v) -> System.out.printf("key: %s, value: %s%n", k, v));

            executorService.shutdown();
        });
    }

}
