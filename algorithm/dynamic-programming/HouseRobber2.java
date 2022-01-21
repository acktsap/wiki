import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/house-robber-ii/
 *
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed.
 * All houses at this place are arranged in a circle.
 * That means the first house is the neighbor of the last one.
 * Meanwhile, adjacent houses have a security system connected,
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * 
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 *
 * Example 2:
 * 
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 3:
 * 
 * Input: nums = [1,2,3]
 * Output: 3
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 *
 *
 *
 * Approach & Proof 
 *
 * 1. dp tabulation
 *
 * Let a_n be n-th nums (nums[n - 1).
 *
 * dp[n] = max(dp[n - 1], dp(n - 2) + a_n)    n >= 2
 *       = a_n    n == 1
 *       = 0      n == 0
 *
 * Then final solution is
 *
 * f(nums[1:n]) = max(f(nums[1:n-1]), f(nums[2:n]))
 * 
 * completixy
 *
 * - Time  : O(2n)
 * - Space : O(n)
 *
 *
 *
 * Review
 *
 * 이걸 이렇게 분해할 수 있다고?!
 *
 *
 */
class HouseRobber2 {
  public int rob(int[] nums) {
    if (nums.length == 1) {
      return nums[0];
    }
    
    return Math.max(rob(nums, 0, nums.length - 1), rob(nums, 1, nums.length));
  }
  
  protected int rob(int[] nums, int fromInclusive, int toExclusive) {
    int[] dp = new int[toExclusive - fromInclusive + 1];
    dp[0] = 0;
    dp[1] = nums[fromInclusive];
    for (int i = 2; i < dp.length; ++i) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[fromInclusive + i - 1]);
    }
    
    return dp[dp.length - 1];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 2, 3, 2 },
        3,
      },
      {
        new int[] { 1, 2, 3, 1 },
        4,
      },
      {
        new int[] { 1, 2, 3 },
        3,
      },
      {
        new int[] { 2, 1, 1, 100 },
        101,
      },
      {
        new int[] { 2, 1, 1, 100, 2, 3 },
        104,
      },
    };

    var solution = new HouseRobber2();
    for (Object[] parameter : parameters) {
      int[] nums = (int[]) parameter[0];
      int expected = (int) parameter[1];

      {
        int actual = solution.rob(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }

}
