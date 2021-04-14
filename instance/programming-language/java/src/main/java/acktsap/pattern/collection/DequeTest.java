/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DequeTest {

    /**
     * Prefer Deque implementation instead over {@link java.util.Stack} since all of the method of
     * Stack is synchronized (extends Vector, also all synchronized).
     */
    public static void main(String[] args) {
        /* Types */

        /**
         * Resizable-array implementation of the {@link java.util.Deque} interface.
         *
         * - No capacity restrictions; they grow as necessary to support usage.
         * - Not thread-safe.
         * - faster than {@link java.util.Stack} when used as a stack,
         *   and faster than {@link java.util.LinkedList} when used as a queue.
         */
        Deque<Integer> arrayDeque = new ArrayDeque<>();

        /**
         * LinkedList: Doubly-linked list implementation of the {@link java.util.Deque} interface.
         */
        Deque<Integer> linkedList = new LinkedList<>();

        Deque<Integer> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    }

}
