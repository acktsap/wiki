import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/third-maximum-number/
 *
 * Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.
 * 
 * Example 1:
 * 
 * Input: nums = [3,2,1]
 * Output: 1
 * Explanation:
 * The first distinct maximum is 3.
 * The second distinct maximum is 2.
 * The third distinct maximum is 1.
 * 
 * Example 2:
 * 
 * Input: nums = [1,2]
 * Output: 2
 * Explanation:
 * The first distinct maximum is 2.
 * The second distinct maximum is 1.
 * The third distinct maximum does not exist, so the maximum (2) is returned instead.
 * 
 * Example 3:
 * 
 * Input: nums = [2,2,3,1]
 * Output: 1
 * Explanation:
 * The first distinct maximum is 3.
 * The second distinct maximum is 2 (both 2's are counted together since they have the same value).
 * The third distinct maximum is 1.
 * 
 * Constraints:
 * 
 *     1 <= nums.length <= 104
 *     -231 <= nums[i] <= 231 - 1
 * 
 * Follow up: Can you find an O(n) solution?
 * 
 *
 * Approach & Proof 
 *
 * iterating 하면서 3개 값 계속 갱신하면 될거같음.
 * Integer.MIN_VALUE 써서 general하게 알고리즘을 설계 할 수 있지 않을까?
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(candidate_size)
 *
 *
 * Review
 *
 * Flag는 필요.. Integer.MIN_VALUE에 복병이 있었음.
 * 왜 dislike가 많은지 알거같음.
 *
 *
 */
class ThirdMaximumNumber {
  public int thirdMax(int[] nums) {
    int size = 3;

    int[] grades = new int[size];
    boolean[] present = new boolean[size];
    for (int i = 0; i < size; ++i) {
      present[i] = false;
    }

    for (int next : nums) {
      int candidate = next;
      boolean candidatePresent = true;
      for (int i = 0; i < grades.length; ++i) {
        if (!candidatePresent) {
          break;
        }

        if (present[i]) {
          int previous = grades[i];
          int max = Math.max(previous, candidate);
          int min = Math.min(previous, candidate);
          grades[i] = max;
          candidate = min;

          if (max == min) {
            candidatePresent = false;
          }
        } else {
          grades[i] = candidate;
          candidatePresent = false;
        }
        present[i] = true;
      }
    }

    return present[size  - 1] ? grades[size - 1] : grades[0];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 3, 2, 1 },
        1
      },
      {
        new int[] { 2, 1 },
        2
      },
      {
        new int[] { 2, 2, 3, 1 },
        1
      },
      {
        new int[] { -2147483648 }, 
        -2147483648
      },
      {
        new int[] { 1, 1 }, 
        1
      },
      {
        new int[] { 1, 1, 1 }, 
        1
      },
      {
        new int[] { 1, 2, -2147483648 }, 
        -2147483648
      },
    };

    var solution = new ThirdMaximumNumber();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      int expected = (int) parameter[1];

      int actual = solution.thirdMax(input);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual + "(input: " + Arrays.toString(input) + ")");
      }
    }
  }
}
