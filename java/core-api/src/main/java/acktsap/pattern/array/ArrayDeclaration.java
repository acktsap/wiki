package acktsap.pattern.array;

import java.util.Arrays;

public class ArrayDeclaration {

  public static void main(String[] args) {
    int[] iArr1 = new int[10];
    int[] iArr2 = new int[10];
//		int[] iArr3 = new int[]{100, 95, 80, 70, 60};

    // 이렇게 가능
    int[] iArr3 = {100, 95, 80, 70, 60};
    char[] chArr = {'a', 'b', 'c', 'd'};
    int[][] score = {
        {100, 100, 100},
        {20, 20, 20},
        {30, 30, 30},
        {40, 40, 40}
    };

    for (int i = 0; i < iArr1.length; i++) {
      iArr1[i] = i + 1; // 1~10의 숫자를 순서대로 배열에 넣는다.
    }

    for (int i = 0; i < iArr2.length; i++) {
      iArr2[i] = (int) (Math.random() * 10) + 1; // 1~10의 값을 배열에 저장
    }

    // 배열에 저장된 값들을 출력한다.
    for (int i = 0; i < iArr1.length; i++) {
      System.out.print(iArr1[i] + ",");
    }
    System.out.println();
    System.out.println(Arrays.toString(iArr2));
    System.out.println(Arrays.toString(iArr3));
    System.out.println(Arrays.toString(chArr));
    System.out.println(iArr3);
    System.out.println(chArr);

    int sum = 0;
    for (int i = 0; i < score.length; i++) {
      for (int j = 0; j < score[i].length; j++) {
        System.out.printf("score[%d][%d]=%d%n", i, j, score[i][j]);
      }
    }
    for (int[] tmp : score) {
      for (int i : tmp) {
        sum += i;
      }
    }
    System.out.println("sum=" + sum);
  }
}