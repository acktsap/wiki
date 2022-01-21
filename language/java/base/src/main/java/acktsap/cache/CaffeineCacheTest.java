package acktsap.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.NonNull;

import acktsap.Block;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineCacheTest {
    /*
        Caffeine cache

        - guava cache에서 영감을 받은 in-memory 고성능 캐시
        - java 8로 작성
        - LRU 말고 TinyLFU를 사용
        - guava cache보다 빠름
    */
    public static void main(String[] args) {
        Block.d("Caffeine Loading cache without ttl", () -> {
            LoadingCache<Object, String> cache = Caffeine.newBuilder()
                .maximumSize(3)
                .executor(Runnable::run) // used when running async task like eviction. By default, ForkJoinPool.commonPool() is used
                .build(key -> {
                    System.out.println("load with " + key);
                    return key.toString();
                });
            System.out.println("should retrieve");
            cache.get("1");
            System.out.println("should retrieve");
            cache.get("2");
            System.out.println("should retrieve");
            cache.get("3");
            System.out.println("should hit");
            cache.get("1");
            System.out.println("should retrieve");
            cache.get("4");
            System.out.println("should retrieve");
            cache.get("3");
        });

        Block.d("Caffeine Loading cache getAll without ttl", () -> {
            LoadingCache<Object, String> cache = Caffeine.newBuilder()
                .maximumSize(3)
                .executor(Runnable::run)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(@NonNull Object key) throws Exception {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Map<?, ? extends String> loadAll(@NonNull Set<?> keys) throws Exception {
                        List<Object> list = new ArrayList<>();
                        keys.forEach(list::add);
                        System.out.println("load all with " + list);
                        return list.stream()
                            .collect(Collectors.toMap(Function.identity(), Object::toString));
                    }
                });
            System.out.println("should retrieve");
            cache.getAll(List.of(1, 2, 3));
            System.out.println("should hit");
            cache.getAll(List.of(1, 2, 3));
            System.out.println("should retrieve");
            cache.getAll(List.of(4));
            System.out.println("should retrieve any");
            cache.getAll(List.of(1, 2, 3));
        });
    }
}
