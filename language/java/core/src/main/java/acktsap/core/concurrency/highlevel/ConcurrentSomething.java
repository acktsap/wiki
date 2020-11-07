/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.highlevel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentSomething {

    public static void main(String[] args) {
        // concurrent data structures
        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        ConcurrentMap<Object, Object> hashMap = new ConcurrentHashMap<>();
        ConcurrentNavigableMap<Object, Object> navigableMap = new ConcurrentSkipListMap<>();
        System.out.format("Queue: %s, HashMap: %s, TreeMap: %s%n", queue, hashMap, navigableMap);

        // Math.random use AtomicLong in a seed in a static 'Random' class instance
        // ThreadLocalRandom is faster than Math.random (if Math.random is shared)
        double slowRandom = (int) Math.random();
        double fastRandom = ThreadLocalRandom.current().nextDouble();
        System.out.format("SlowRandom: %s, FastRandom: %s%n", slowRandom, fastRandom);

        // All classes have get and set methods that work like reads and writes on volatile variables.
        // That is, a set has a happens-before relationship with any subsequent get on the same
        // variable. This is faster than synchronized keyword (monitoring lock). Atomic operations use
        // CAS (compare-and-swap). But it may be slower than synchronized on too many thread (too many
        // CAS operation).
        //
        // CAS : 현재 쓰레드에 저장된 값(CPU Cache)과 메인메모리에 저장된 값을 비교하여 일치하는경우 새로운 값으로 교체되고,
        // 일치하지않는다면 실패하고 재시도. CPU Cache에 의한 가시성 문제를 해결
        // 가시성 문제랑 Cache의 값과 Memory의 값이 다른것
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicLong atomicLong = new AtomicLong();
        System.out.format("AtomicInteger: %s, AtomicLong: %s%n", atomicInteger, atomicLong);
    }

}
