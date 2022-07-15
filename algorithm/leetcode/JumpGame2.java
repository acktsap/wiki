import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/jump-game-ii/
 *
 * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that position.
 * 
 * Your goal is to reach the last index in the minimum number of jumps.
 * 
 * You can assume that you can always reach the last index.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * 
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 * 
 *
 *
 * Review
 *
 * dp인데 O(n^2)면 느린거다..
 *
 */
class JumpGame2 {
  // TODO: greedy

  /*
    minimum? 누가봐도 dp
    
    overlapping subproblem : i까지 가는 최소의 수를 계속 계산
    optimal substructure : i까지 계산한거 기반으로 다음꺼 resolve 가능
    
    dp[i] : minimum number to reach i
    dp[i] = min(dp[j]) for all (i - j) <= nums[j] && j < i
    
    음.. 이러면 좀 느릴거같은데..

    Let n be size of nums. Then
    - time: O(n^2)
    - space: O(n)
  */
  public int jumpDp(int[] nums) {
    int[] dp = new int[nums.length];
    dp[0] = 0;
    
    for (int i = 1; i < nums.length; ++i) {
      int min = Integer.MAX_VALUE;
      for (int j = i - 1; j >= 0; --j) {
        if ((i - j) <= nums[j]) {
          min = Math.min(min, dp[j] + 1);
        }
      }
      dp[i] = min;
    }
    
    return dp[nums.length - 1];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 2, 3, 1, 1, 4 },
        2,
      },
      {
        new int[] { 2, 3, 0, 1, 4 },
        2,
      },
      {
        new int[] { 2, 3, 0, 1, 4, 0, 0, 1, 0 },
        3,
      },
      {
        new int[] { 2, 3, 0, 1, 5, 0, 0, 2, 0, 0 },
        3,
      },
    };

    var solution = new JumpGame2();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var expected = (int) parameter[1];

      {
        var actual = solution.jumpDp(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
