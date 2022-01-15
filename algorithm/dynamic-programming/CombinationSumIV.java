import java.util.Arrays;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/combination-sum-iv/
 *
 * Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.
 * 
 * The test cases are generated so that the answer can fit in a 32-bit integer.
 * 
 *
 * Example 1:
 * 
 * Input: nums = [1,2,3], target = 4
 * Output: 7
 * Explanation:
 * The possible combination ways are:
 *
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * Note that different sequences are counted as different combinations.
 *
 * Example 2:
 * 
 * Input: nums = [9], target = 3
 * Output: 0
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 1000
 * All the elements of nums are unique.
 * 1 <= target <= 1000
 *  
 * Follow up: What if negative numbers are allowed in the given array?
 * How does it change the problem? What limitation we need to add to the question to allow negative numbers?
 *
 *
 *
 * Approach & Proof 
 *
 * 1. dp vabulation
 *
 * dp[n] : n에 대한 경우의 수
 * dp[n] = dp[n - m_1] + dp[n - m_2] + ...    where m_x is in nums
 *       = 1     if n == 1
 *
 * completixy
 *
 * - Time  : O(n * nums.length)
 * - Space : O(n)
 *
 *
 * 2. dp memoization
 *
 * todo
 *
 * 3. brute force
 *
 * todo
 *
 *
 *
 * Review
 *
 * dp 짱
 *
 *
 */
class CombinationSumIV {
  public int combinationSum4DpTabulation(int[] nums, int target) {
    int[] dp = new int[target + 1];
    Arrays.fill(dp, 0);
    dp[0] = 1;
    
    for (int i = 1; i <= target; ++i) {
      int next = 0;
      for (int num : nums) {
        if ((i - num) < 0) {
          continue;
        }
        next += dp[i - num];
      }
      dp[i] = next;
    }
    
    return dp[target];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 3 },
        4,
        7,
      },
      {
        new int[] { 9 },
        3,
        0,
      },
    };

    var solution = new CombinationSumIV();
    for (Object[] parameter : parameters) {
      int[] nums = (int[]) parameter[0];
      int target = (int) parameter[1];
      int expected = (int) parameter[2];

      {
        int actual = solution.combinationSum4DpTabulation(nums, target);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
