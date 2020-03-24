/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection.aggregation;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aggregation {

  /*
   * Aggregate operations (Pipelines and Streams).
   *
   * - They use internal iteration: With external iteration, your application determines both what
   * collection it iterates and how it iterates it. However, external iteration can only iterate
   * over the elements of a collection sequentially. It can more easily take advantage of parallel
   * computing.
   *
   * - They process elements from a stream: Aggregate operations process elements from a stream, not
   * directly from a collection.
   *
   * - They support behavior as parameters: You can specify lambda expressions as parameters for
   * most aggregate operations. This enables you to customize the behavior of a particular aggregate
   * operation.
   */
  public static void main(String[] args) {
    Collection<Integer> collections = asList(12, 2, 33, 4, 75, 62);

    // average
    double average = collections.stream()
        .filter(i -> 12 != i)
        .mapToInt(Integer::new)
        .average()
        .orElseGet(() -> 0.0);
    System.out.println("Average: " + average);

    // Reduce without identity.
    String reducedDisplay = collections.stream()
        .filter(i -> 1 != i)
        .map(Object::toString)
        .reduce((acc, curr) -> acc + ", " + curr)
        .orElseThrow(() -> new IllegalArgumentException());
    System.out.println("Reduced display: " + reducedDisplay);

    // Reduce with identity.
    int reducedSum = collections.stream()
        .filter(i -> 1 != i)
        .map(i -> 2 * i)
        .reduce(0, (acc, curr) -> acc + curr);
    System.out.println("Reduced sum: " + reducedSum);

    // Collect with toList.
    List<String> simpleCollected = collections.stream()
        .filter(i -> 1 != i)
        .map(i -> 2 * i)
        .map(Object::toString)
        .collect(Collectors.toList());
    System.out.println("Collected by toList: " + simpleCollected);

    // Collect with groupingBy.
    Map<Integer, List<Integer>> groupByCollected = collections.stream()
        .filter(i -> 1 != i)
        .map(i -> 3 * i)
        .collect(groupingBy(i -> i % 2));
    System.out.println("Collected by groupingBy: " + groupByCollected);
  }
}
