import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/move-zeroes/
 *
 * Given an array nums, write a function to move all 0's to the end of it
 * while maintaining the relative order of the non-zero elements.
 * 
 * Example:
 * 
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * 
 * Note:
 * 
 * 1. You must do this in-place without making a copy of the array.
 * 2. Minimize the total number of operations.
 *
 *
 * Approach & Proof 
 *
 * Two pointer approach
 *
 * - i : for write
 * - j : for read
 *
 * loop invariant
 *
 * - i 이전까지는 0이 없고 상대적인 순서가 보장되어 있음
 * - i < j
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(1) (in place)
 *
 *
 * Review
 *
 * two pointer에서 while문 안에서 while문 쓰지 마셈..
 * 그리고 invariant를 i, j기준으로 잡아
 *
 */
class MoveZeros {
  public void moveZeroes(final int[] nums) {
    int i = 0; // to write
    int j = 1; // to read
    while (j < nums.length) {
      if (nums[i] != 0) {
        ++i;
        ++j;
      } else {
        if (nums[j] == 0) {
          ++j;
        } else {
          nums[i] = nums[j];
          nums[j] = 0;
          ++i;
          ++j;
        }
      }
    }
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new int[] { 0, 1, 0, 3, 12 },
        new int[] { 1, 3, 12, 0, 0 },
      },
      {
        new int[] { 0, 1 },
        new int[] { 1, 0 },
      },
      {
        new int[] { 1 },
        new int[] { 1 },
      },
    };
    final MoveZeros solution = new MoveZeros();
    for (final Object[] parameter : parameters) {
      final int[] input = (int[]) parameter[0];
      final int[] expected = (int[]) parameter[1];
      solution.moveZeroes(input);
      if (!Arrays.equals(expected, input)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(input));
      }
    }
  }
}
