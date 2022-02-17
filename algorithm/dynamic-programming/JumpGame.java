import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/jump-game/
 *
 * You are given an integer array nums.
 *
 * You are initially positioned at the array's first index,
 * and each element in the array represents your maximum jump length at that position.
 * 
 * Return true if you can reach the last index, or false otherwise.
 *  
 * 
 * Example 1:
 * 
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * 
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 105
 *
 *
 *
 * Review
 *
 * O(n^2)라도 같은 O(n^2)가 아니다..
 *
 */
class JumpGame {
  /*
    overlapping subproblem -> dp
    
    dp tabulation
    
    dp[i] : can reach to it
    dp[i] = true, if nums[k] > 0 && dp[k] for any k < i
    
    - time:  O(n^2)
    - space: O(n)
  */
  public boolean canJumpDpFaster(int[] nums) {
    boolean[] dp = new boolean[nums.length];
    dp[0] = true;
    
    for (int i = 0; i < nums.length; ++i) {
      if (dp[i] == true && nums[i] > 0) {
        for (int j = i + 1; j <= i + nums[i] && j < dp.length; ++j) {
          dp[j] = true;
        }
      }
    }
    
    return dp[nums.length - 1];
  }
  /*
    overlapping subproblem -> dp
    
    dp tabulation
    
    dp[i] : can reach to it
    dp[i] = true, if nums[k] > 0 && dp[k] for any k < i
    
    - time:  O(n^2)
    - space: O(n)
  */
  public boolean canJumpDpSlow(int[] nums) {
    boolean[] dp = new boolean[nums.length];
    dp[0] = true;
    
    for (int i = 0; i < nums.length; ++i) {
      if (dp[i] == true && nums[i] > 0) {
        for (int j = i + 1; j <= i + nums[i] && j < dp.length; ++j) {
          dp[j] = true;
        }
      }
    }
    
    return dp[nums.length - 1];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 2, 3, 1, 1, 4 },
        true,
      },
      {
        new int[] { 3, 2, 1, 0, 4 },
        false,
      },
      {
        new int[] { 2, 0 },
        true,
      },
      {
        new int[] { 2, 0, 0 },
        true,
      },
      {
        new int[] { 2, 0, 0, 0 },
        false,
      },
      {
        new int[] { 0, 2, 3 },
        false,
      },
    };

    var solution = new JumpGame();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var expected = (boolean) parameter[1];

      {
        var actual = solution.canJumpDpFaster(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

      {
        var actual = solution.canJumpDpSlow(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
