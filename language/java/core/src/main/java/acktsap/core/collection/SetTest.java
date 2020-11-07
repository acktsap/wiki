/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.collection;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetTest {

  static List<Integer> data = asList(12, 2, 33, 4, 75, 62);

  /**
   * A Set is a Collection that cannot contain duplicate elements.
   *
   * Set also adds a stronger contract on the behavior of the {@code equals} and {@code hashCode}
   * operations, allowing Set instances to be compared meaningfully even if their implementation
   * types differ
   */
  public static void main(String[] args) {
    /**
     * Hash Table implementation.
     *
     * A {@link Set} implementation based on a {@link HashMap}.
     *
     * This implementation offers constant time performance for the basic operations ({@code add},
     * {@code remove},{@code contains} and {@code size}).
     *
     * Not synchronized. Wrapped one is {@link Collections#synchronizedSet
     * Collections.synchronizedSet}.
     */
    Set<Integer> hashSet = new HashSet<>(data);
    System.out.println("HashSet: " + hashSet); // do not keep order

    /**
     * Balanced Tree implementation.
     *
     * A {@link NavigableSet} implementation based on a {@link TreeMap}.
     *
     * This implementation provides guaranteed log(n) time cost for the basic operations
     * ({@code add}, {@code remove} and {@code contains}).
     *
     * Not synchronized. Wrapped one is {@link Collections#synchronizedSortedSet
     * Collections.synchronizedSortedSet}.
     */
    Set<Integer> treeSet = new TreeSet<>((l, r) -> r.compareTo(l));
    treeSet.addAll(data);
    System.out.println("TreeSet: " + treeSet); // reverse order

    /**
     * HashTable + Linked List implementation.
     *
     * Hash table and linked list implementation of the {@code Set} interface, with predictable
     * iteration order. Based on {@link LinkedHashMap}.
     *
     * Differs from {@code HashSet} in that it maintains a doubly-linked list running through all of
     * its entries.
     *
     * Not synchronized. Wrapped one is {@link Collections#synchronizedSet
     * Collections.synchronizedSet}.
     *
     */
    Set<Integer> linkedHashSet = new LinkedHashSet<>(data);
    System.out.println("LinkedHashSet: " + linkedHashSet); // insertion order
  }
}
