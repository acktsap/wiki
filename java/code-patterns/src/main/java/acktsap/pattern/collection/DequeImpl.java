/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class DequeImpl {

  static List<Integer> data = Arrays.asList(new Integer[] {12, 2, 33, 4, 75, 62});

  public static void main(String[] args) {
    /**
     * Resizable-array implementation of the {@link Deque} interface.
     * 
     * Array deques have no capacity restrictions; they grow as necessary to support usage.
     * 
     * They are not thread-safe.
     * 
     * This class is likely to be faster than {@link Stack} when used as a stack, and faster than
     * {@link LinkedList} when used as a queue.
     */
    Deque<Integer> arrayDeque = new ArrayDeque<>(data);
    System.out.println("ArrayDeque: " + arrayDeque);

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
    System.out.println("LinkedList: " + linkedList);
  }

}
