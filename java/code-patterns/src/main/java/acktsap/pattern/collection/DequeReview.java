/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class DequeReview {
  
  public static void main(String[] args) {
    // dummy data
    List<Integer> data = Arrays.asList(new Integer[] {12, 2, 33, 4, 75, 62});

    /**
     */
    Deque<Integer> arrayList = new ArrayDeque<>(data);
    System.out.println(arrayList);

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
     */
    List<Integer> linkedList = new LinkedList<>(data);
    System.out.println(linkedList);
  }

}
