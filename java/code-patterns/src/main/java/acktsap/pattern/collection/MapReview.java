/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapReview {

  static List<Integer> keys = Arrays.asList(new Integer[] {12, 2, 33, 4, 75, 62});
  static String value = "v";

  public static void main(String[] args) {
    /**
     * Legacy Hash Table implementation of the {@code Map} interface.
     *
     * (The {@code Hashtable} class is roughly equivalent to {@code HashMap}, except that it is
     * synchronized.
     *
     * {@link ConcurrentHashMap} handles synchronization carefully (like synchronized only on hashed
     * index value) whereas {@link Hashtable} just uses synchronized keyword.
     * 
     * See {@link Hashtable#get} and {@link Hashtable#put}.
     */
    Map<Integer, String> hashTable = new Hashtable<>();
    keys.forEach(k -> hashTable.put(k, value));
    System.out.println("Hashtable: " + hashTable); // arbitary order

    /**
     * Hash Table implementation of the {@code Map} interface.
     *
     * (The {@code HashMap} class is roughly equivalent to {@code Hashtable}, except that it is
     * unsynchronized and permits nulls.
     *
     * This implementation provides constant-time performance for the basic operations ({@code get}
     * and {@code put}).
     *
     * Uses chaining of list for hash collision. On specific threshold, it changes to TreeNode.
     * {@link HashMap#putVal}.
     *
     * Not synchronized. Wrapped one is {@link Collections#synchronizedMap
     * Collections.synchronizedMap}.
     * 
     * See {@link HashMap#get} and {@link HashMap#put}.
     */
    Map<Integer, String> hashMap = new HashMap<>();
    keys.forEach(k -> hashMap.put(k, value));
    System.out.println("HashMap: " + hashMap); // arbitary order

    /**
     * A Red-Black tree based {@link NavigableMap} implementation.
     *
     * The map is sorted according to the {@linkplain Comparable natural ordering} of its keys, or
     * by a {@link Comparator} provided at map creation time, depending on which constructor is
     * used.
     *
     * This implementation provides guaranteed log(n) time cost for the {@code containsKey},
     * {@code get}, {@code put} and {@code remove} operations.
     *
     * Not synchronized. Wrapped one is {@link Collections#synchronizedSortedMap
     * Collections.synchronizedSortedMap}.
     *
     * See {@link TreeMap#get} and {@link TreeMap#put}.
     */
    Map<Integer, String> treeMap = new TreeMap<>((l, r) -> r.compareTo(l));
    keys.forEach(k -> treeMap.put(k, value));
    System.out.println("TreeMap: " + treeMap); // sorted order (desc)

    /**
     * Hash table and linked list implementation of the {@code Map} interface, with predictable
     * iteration order.
     * 
     * This implementation differs from {@code HashMap} in that it maintains a doubly-linked list
     * running through all of its entries. This linked list defines the iteration ordering,
     * 
     * Not synchronized. Wrapped one is {@link Collections#synchronizedMap
     * Collections.synchronizedMap}.
     */
    Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
    keys.forEach(k -> linkedHashMap.put(k, value));
    System.out.println("LinkedHashMap: " + linkedHashMap); // insertion order

    /**
     * A hash table supporting full concurrency of retrievals and high expected concurrency for
     * updates. This class obeys the same functional specification as {@link java.util.Hashtable}.
     *
     * Even though all operations are thread-safe, retrieval operations do <em>not</em> entail
     * locking, and there is <em>not</em> any support for locking the entire table in a way that
     * prevents all access.
     *
     * See {@link ConcurrentHashMap#put}.
     */
    Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
    keys.forEach(k -> concurrentHashMap.put(k, value));
    System.out.println("ConcurrentHashMap: " + linkedHashMap); // arbitary order
  }

}
