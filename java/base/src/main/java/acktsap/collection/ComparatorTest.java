package acktsap.collection;

import java.util.Arrays;

// Comparator : 정렬기준을 제시
// Comparable : 기본 정렬기준을 제시
public class ComparatorTest {

    public static void main(String[] args) {
        String[] strArr = {"cat", "Dog", "lion", "tiger"};

        // default Comparable implementation of String
        Arrays.sort(strArr);
        System.out.println("strArr=" + Arrays.toString(strArr));

        // case insensitive order
        Arrays.sort(strArr, String.CASE_INSENSITIVE_ORDER);
        System.out.println("strArr=" + Arrays.toString(strArr));

        // custom order (reverse order)
        Arrays.sort(strArr, (o1, o2) -> {
            if (o1 instanceof Comparable && o2 instanceof Comparable) {
                Comparable c1 = (Comparable) o1;
                Comparable c2 = (Comparable) o2;
                return c2.compareTo(c1);
            }
            return -1;
        });
        System.out.println("strArr=" + Arrays.toString(strArr));
    }
}

