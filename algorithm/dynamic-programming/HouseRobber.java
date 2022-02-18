import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/house-robber/
 *
 * You are a professional robber planning to rob houses along a street.
 *
 * Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the police.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 2:
 * 
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 *
 *
 *
 * Review
 *
 * 간단하다.
 *
 */
class HouseRobber {
  /*
    Overlapping subproblem -> dynamic programming
 
    dp[n] : max until nums[0:n]
    
    dp[n] = max(dp[n - 1], dp(n - 2) + nums[n])
 
    - time:  O(n)
    - space: O(n)
  */
  public int rob(int[] nums) {
    int[] dp = new int[nums.length + 1];
    dp[0] = 0; // trick
    dp[1] = nums[0];
    
    for (int i = 2; i <= nums.length; ++i) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
    }
    
    return dp[nums.length];
  }

  /*
    Overlapping subproblem -> dynamic programming
 
    dp[n] : max until nums[0:n]
    
    dp[n] = max(dp[n - 1], dp(n - 2) + nums[n])
 
    - time:  O(n)
    - space: O(1)
  */
  public int robSpaceOptimal(int[] nums) {
    if (nums.length == 1) {
      return nums[0];
    }
    
    int beforeTwo = nums[0];
    int beforeOne = Math.max(beforeTwo, nums[1]);
    
    for (int i = 2; i < nums.length; ++i) {
      int next = Math.max(beforeOne, beforeTwo + nums[i]);
      beforeTwo = beforeOne;
      beforeOne = next;
    }
    
    return beforeOne;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 3, 1 },
        4,
      },
      {
        new int[] { 2, 7, 9, 3, 1 },
        12,
      },
      {
        new int[] { 2, 1, 1, 100 },
        102,
      },
      {
        new int[] { 2, 1, 1, 100, 2, 1 },
        103,
      },
    };

    var solution = new HouseRobber();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var expected = (int) parameter[1];

      {
        var actual = solution.rob(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

      {
        var actual = solution.robSpaceOptimal(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
