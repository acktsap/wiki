/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class CollectionInterface {

  /*
   * A {@code Collection} is simply an object that groups multiple elements into a single unit.
   * Collections are used to store, retrieve, manipulate, and communicate aggregate data. It
   * contains the followings:
   *
   * - Interfaces: These are abstract data types that represent collections.
   *
   * - Implementations: These are the concrete implementations of the collection interfaces.
   *
   * - Algorithms: These are the methods that perform useful computations, such as searching and
   * sorting, on objects that implement collection interfaces. This is polymorphic: that is, the
   * same method can be used on many different implementations of the appropriate collection
   * interface.
   *
   *
   * The Collection interface contains methods that perform basic operations, such as int size(),
   * boolean isEmpty(), boolean contains(Object element), boolean add(E element), boolean
   * remove(Object element), and Iterator<E> iterator().
   *
   * In JDK 8 and later, the Collection interface also exposes methods Stream<E> stream() and
   * Stream<E> parallelStream(), for obtaining sequential or parallel streams from the underlying
   * collection.
   */
  public static void main(String[] args) {
    Collection<Integer> collections = new ArrayList<>(asList(12, 2, 33, 4, 75, 62));

    /*
     * Traverse with for-each construct.
     */
    System.out.print("Traverse with for-each: ");
    for (Integer collection : collections) {
      System.out.println(collection + " ");
    }

    /*
     * Traverse with iterator.
     */
    System.out.print("Traverse with iterator: ");
    Iterator<Integer> it = collections.iterator();
    while (it.hasNext()) {
      System.out.print(it.next() + " ");
    }
    System.out.println();

    /*
     * Bulk operations (xxxAll).
     */
    collections.removeAll(Collections.singleton(12));
    System.out.println("After removeAll: " + collections);
    collections.addAll(asList(1, 2, 3));
    System.out.println("After addAll: " + collections);

    /*
     * Convert to Array.
     */
    Object[] objects = collections.toArray();
    Integer[] integers = collections.toArray(new Integer[]{});
    System.out.println("Converted Object array: " + Arrays.toString(objects));
    System.out.println("Converted Integer array: " + Arrays.toString(integers));
  }

}
