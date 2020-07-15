package acktsap.sample;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PersonFormatter {

  public static void main(String[] args) {
    final List<Integer> ret = asList(1, 2, 3);
    ret.stream().forEach(i -> System.out.println(i));
    Integer a = ret.stream().reduce(10, (acc, curr) -> {
      System.out.printf("acc: %d, curr:%d%n", acc, curr);
      return acc + curr;
    });
    System.out.println(a);
  }

}
