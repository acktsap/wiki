/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.highlevel;

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
        Block.d("Thread safe upsert", () -> {
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
                        concurrentMap.computeIfPresent(key, (k, v) -> v + 1);
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
