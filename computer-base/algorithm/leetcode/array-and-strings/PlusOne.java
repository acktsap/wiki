import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/plus-one/
 *
 * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
 *
 * The digits are stored such that the most significant digit is at the head of the list,
 * and each element in the array contain a single digit.
 *
 * You may assume the integer does not contain any leading zero, except the number 0 itself.
 * 
 * Example 1:
 * 
 * Input: [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 * 
 * Example 2:
 * 
 * Input: [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 * 
 *
 * Approach & Proof 
 *
 * 1더한다음에 overflow낮은자리에서부터 스캔하면서 넘기자
 * 그러려면.. 앞에 0을 추가한 array를 만들고 1 더하고 process overflow
 *
 * loop invariant
 *
 * - index보다 작은 곳은 아직 처리가 안됨, 큰거는 처리
 * - 0 < index
 * - ret[index] == 10 or < 10
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(n)
 *
 *
 * Review
 *
 * Arrays.copyOfRange 잊지마셈 (arr, startInclusive, endExclusive)
 *
 */
class PlusOne {
  public int[] plusOne(final int[] digits) {
    final int[] ret = new int[digits.length + 1];
    for (int i = 1; i < ret.length; ++i) {
      ret[i] = digits[i - 1];
    }

    ret[ret.length - 1] += 1;
    int index = ret.length - 1;
    while (0 < index) {
      if (ret[index] < 10) {
        break;
      }
      ret[index - 1] += 1;
      ret[index] = ret[index] % 10;
      --index;
    }

    return ret[0] == 1 ? ret : Arrays.copyOfRange(ret, 1, ret.length);
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new int[] { 0 },
        new int[] { 1 },
      },
      {
        new int[] { 1, 2, 3 },
        new int[] { 1, 2, 4 },
      },
      {
        new int[] { 9, 9, 8 },
        new int[] { 9, 9, 9 },
      },
      {
        new int[] { 9, 9, 9 },
        new int[] { 1, 0, 0, 0 },
      },
    };
    final PlusOne solution = new PlusOne();
    for (final Object[] parameter : parameters) {
      final int[] input = (int[]) parameter[0];
      final int[] expected = (int[]) parameter[1];
      final int[] actual = solution.plusOne(input);
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
