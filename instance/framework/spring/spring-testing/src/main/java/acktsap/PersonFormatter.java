package acktsap;

import static java.util.Arrays.asList;

import java.util.List;

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
