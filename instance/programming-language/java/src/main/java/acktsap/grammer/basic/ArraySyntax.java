package acktsap.grammer.basic;

import java.util.Arrays;

public class ArraySyntax {

    public static void main(String[] args) {
        // declaration
        int[] arr1 = new int[10];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i + 1; // 1~10의 숫자를 순서대로 배열에 넣는다.
        }
        System.out.println(Arrays.toString(arr1));

        int[] arrWithInit = {100, 95, 80, 70, 60};
        System.out.println(Arrays.toString(arrWithInit));

        int[] zeroSizeArr = new int[0];
        System.out.println(Arrays.toString(zeroSizeArr));

        // multi array
        int[][] multiArr = {
            {100, 100, 100},
            {20, 20, 20},
            {30, 30, 30},
            {40, 40, 40}
        };
        for (int i = 0; i < multiArr.length; i++) {
            for (int j = 0; j < multiArr[i].length; j++) {
                System.out.printf("multiAarr[%d][%d]=%d%n", i, j, multiArr[i][j]);
            }
        }

        // foreach on array
        int sum = 0;
        for (int[] tmp : multiArr) {
            for (int i : tmp) {
                sum += i;
            }
        }
        System.out.println("sum=" + sum);
    }
}
