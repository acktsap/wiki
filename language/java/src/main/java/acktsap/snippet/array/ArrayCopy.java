package acktsap.snippet.array;

import java.util.Arrays;

public class ArrayCopy {

    // System.arrarycopy는 각 요소들이 연속으로 저장되어 있다는 특성을 이용해서 한번에 복사함
    // for문은 하나 하나 복사
    public static void main(String[] args) {
        char[] abc = {'A', 'B', 'C', 'D'};
        char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        System.out.println(Arrays.toString(abc));
        System.out.println(Arrays.toString(num));

        // 배열 abc와 num을 붙여서 하나의 배열(result)로 만든다.
        char[] result = new char[abc.length + num.length];
        System.arraycopy(abc, 0, result, 0, abc.length);
        System.arraycopy(num, 0, result, abc.length, num.length);
        System.out.println(Arrays.toString(result));

        // 배열 abc을 배열 num의 첫 번째 위치부터 배열 abc의 길이만큼 복사
        System.arraycopy(abc, 0, num, 0, abc.length);
        System.out.println(Arrays.toString(num));

        // number의 인덱스6 위치에 3개를 복사
        System.arraycopy(abc, 0, num, 6, 3);
        System.out.println(Arrays.toString(num));
    }
}
