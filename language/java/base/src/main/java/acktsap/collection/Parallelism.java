/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Parallelism {

    public static void main(String[] args) {
        List<Integer> collections = asList(1, 2, 3, 4, 5, 6, 7, 8);

        /*
         * Concurrent Reduction.
         */
        // ordered
        Map<Integer, List<Integer>> serialReduction = collections.stream()
            .collect(Collectors.groupingBy(i -> i % 2));
        System.out.println("Serial reduction: " + serialReduction);

        // keep order
        ConcurrentMap<Integer, List<Integer>> parallelReduction = collections.parallelStream()
            .collect(Collectors.groupingByConcurrent(i -> i % 2));
        System.out.println("Parallel reduction: " + parallelReduction);

        System.out.println();


        /*
         * Ordering
         */
        System.out.print("Sorted in reverse order: ");
        Comparator<Integer> normal = Integer::compare;
        Comparator<Integer> reversed = normal.reversed();
        Collections.sort(collections, reversed);
        collections.stream().forEach(e -> System.out.print(e + " "));
        System.out.println();

        // parallelStream uses common forkjoinpool internally
        // no order
        System.out.print("Parallel stream: ");
        collections.parallelStream().forEach(e -> System.out.print(e + " "));
        System.out.println();
        System.out.print("Another parallel stream: ");
        collections.parallelStream().forEach(e -> System.out.print(e + " "));
        System.out.println();

        // keep order
        System.out.print("With forEachOrdered: ");
        collections.parallelStream().forEachOrdered(e -> System.out.print(e + " "));
        System.out.println();


        /*
         * Laziness: Intermediate operations are lazy because they do not start processing the contents
         * of the stream until the terminal operation commences.
         *
         * Terminal operations: reduce, average, ...
         */


        /*
         * Avoid interference.
         */
        try {
            List<String> listOfStrings = asList("one", "two");

            // This will fail as the peek operation will attempt to add the
            // string "three" to the source after the terminal operation has
            // commenced.

            String concatenatedString = listOfStrings.stream()
                // Don't do this! Interference occurs here.
                .peek(s -> listOfStrings.add(s))
                .reduce((acc, curr) -> acc + " " + curr)
                .orElseThrow(() -> new IllegalStateException());
            System.out.println("Concatenated string: " + concatenatedString);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }

        System.out.println();


        /*
         * Avoid Stateful Lambda Expression.
         */
        List<Integer> serialStorage = new ArrayList<>();
        System.out.println("Serial stream:");
        collections.stream()
            // Don't do this! It uses a stateful lambda expression.
            .map(e -> {
                serialStorage.add(e);
                return e;
            })
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        // keeps order
        serialStorage.stream().forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Parallel stream:");
        List<Integer> parallelStorage = Collections.synchronizedList(new ArrayList<>());
        collections.parallelStream()
            // Don't do this! It uses a stateful lambda expression.
            .map(e -> {
                parallelStorage.add(e);
                return e;
            })
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        // order is differ even for 'forEachOrdered'
        parallelStorage.stream().forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");
    }

}
