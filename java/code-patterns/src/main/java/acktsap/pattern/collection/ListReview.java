/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ListReview {

  static List<Integer> data = Arrays.asList(new Integer[] {12, 2, 33, 4, 75, 62});

  /**
   * ArrayList vs LinkedList.
   * 
   * - ArrayList stores as an array whereas LinkedList stores as Node.
   * 
   * - ArrayList occasionally expand capacity on add operation whereas LinkedList doesn't
   * 
   * - ArrayList can get/remove at constant time whereas LinkedList occasionally takes time as list
   * size.
   */
  public static void main(String[] args) {
    /**
     * Synchronized version of ArrayList.
     * 
     * Can define capacity increment ratio.
     * 
     * See {@link Vector#add}, {@link Vector#remove}.
     */
    List<Integer> vector = new Vector<Integer>(data);
    System.out.println("Vector: " + vector);

    /**
     * Resizable-array implementation
     * 
     * {@link List} interface implementation. Also, {@link RandomAccess}.
     * 
     * The {@code size}, {@code isEmpty}, {@code get}, {@code set}, {@code iterator}, and
     * {@code listIterator} operations run in constant time. Especially, {@code get}.
     * 
     * Store as an array internally.
     * 
     * An application can increase the capacity of an {@code ArrayList} instance before adding a
     * large number of elements using the {@code ensureCapacity} operation (up to 1/2 of old
     * capacity). This may reduce the amount of incremental reallocation.
     * 
     * Not synchronized. Wrapper is {@link Collections#synchronizedList
     * Collections.synchronizedList}.
     *
     * See {@link ArrayList#add}, {@link ArrayList#remove}.
     */
    List<Integer> arrayList = new ArrayList<>(data);
    System.out.println("ArrayList: " + arrayList);

    /**
     * Doubly-linked list implementation.
     * 
     * {@link List} and {@link Deque} interfaces implementation. Also {@link Queue} implementation.
     * 
     * Uses {@link LinkedList#Node} internally.
     * 
     * Operations that index into the list (like {@code get}, {@code remove}) will traverse the list
     * from the beginning or the end.
     * 
     * Not synchronized. Wrapper is {@link Collections#synchronizedList
     * Collections.synchronizedList}.
     *
     * See {@link LinkedList#add}, {@link LinkedList#remove}.
     */
    List<Integer> linkedList = new LinkedList<>(data);
    System.out.println("LinkedList: " + linkedList);
  }

}
