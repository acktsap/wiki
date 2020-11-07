/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

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
        .collect(toList());
    System.out.println("Collected by toList: " + simpleCollected);

    // Collect with groupingBy to List.
    Map<Integer, List<Integer>> groupingByCollected = collections.stream()
        .filter(i -> 1 != i)
        .map(i -> 3 * i)
        .collect(groupingBy(i -> i % 2));
    System.out.println("Collected by groupingByCollected: " + groupingByCollected);

    // Paged List
    int[] raw = IntStream.range(0, new Random().nextInt(30)).toArray();
    int pageSize = 5;
    Map<Integer, List<Integer>> pagedList = IntStream.range(0, raw.length)
        .mapToObj(i -> new Pair<>(i, raw[i]))
        .collect(groupingBy(i -> i.left / pageSize, mapping(j -> j.right, toList())));
    System.out.println("Paged list: " + pagedList);

    // Paged Array
    List<Integer[]> pagedArray = pagedList.values().stream()
        .map(l -> l.toArray(new Integer[0]))
        .collect(toList());
    System.out.print("Paged array: [");
    pagedArray.forEach(p -> System.out.print(Arrays.toString(p)));
    System.out.println("]");

    // Grouping by with enum
    Map<TestEnum, List<Pair<TestEnum, Integer>>> groupingByEnum = IntStream
        .range(0, 15)
        .mapToObj(i -> new Pair<>(i % 2 == 0 ? TestEnum.One : TestEnum.Two, i))
        .collect(groupingBy(Pair::getLeft, toList()));
    for (TestEnum testEnum : TestEnum.values()) {
      // best practice using getOrDefault since value of groupingBy is null if classifier target is null
      // System.out.printf("Enum: %s, values: %s%n", testEnum, groupingByEnum.get(testEnum));
      System.out.printf("Enum: %s, values: %s%n", testEnum,
          groupingByEnum.getOrDefault(testEnum, emptyList()));
    }
  }

  static class Pair<T, R> {

    protected final T left;
    protected final R right;

    public Pair(T left, R right) {
      this.left = left;
      this.right = right;
    }

    public T getLeft() {
      return left;
    }

    public R getRight() {
      return right;
    }

    @Override
    public String toString() {
      return "Pair{" +
          "left=" + left +
          ", right=" + right +
          '}';
    }
  }

  enum TestEnum {
    None,
    One,
    Two
  }

}
