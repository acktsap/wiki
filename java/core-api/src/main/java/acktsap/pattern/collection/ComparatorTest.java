package acktsap.pattern.collection;

import java.util.Arrays;

// Comparator : 정렬기준을 제시
// Comparable : 기본 정렬기준을 제시
public class ComparatorTest {

  public static void main(String[] args) {
    String[] strArr = {"cat", "Dog", "lion", "tiger"};

    // String의 Comparable구현에 의한 정렬
    Arrays.sort(strArr);
    System.out.println("strArr=" + Arrays.toString(strArr));

    // 대소문자 구분안함
    Arrays.sort(strArr, String.CASE_INSENSITIVE_ORDER);
    System.out.println("strArr=" + Arrays.toString(strArr));

    // 역순정렬
    Arrays.sort(strArr, (o1, o2) -> {
      if (o1 instanceof Comparable && o2 instanceof Comparable) {
        Comparable c1 = (Comparable) o1;
        Comparable c2 = (Comparable) o2;
        return c1.compareTo(c2) * -1; // -1을 곱해서 기본 정렬방식의 역으로 변경한다.
        // 또는 c2.compareTo(c1)와 같이 순서를 바꿔도 된다.
      }
      return -1;
    });
    System.out.println("strArr=" + Arrays.toString(strArr));
  }
}

