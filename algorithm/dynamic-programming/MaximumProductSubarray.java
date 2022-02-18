import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/maximum-product-subarray/
 *
 *
 *
 * Review
 *
 * ...
 *
 */
class MaximumProductSubarray {
  // TODO: add max product good solution

  /*
    Brute force takes nC2 * n ~ n^3
    Brute force contains overlapping subproblem
    product of nums[i:j] = product of nums[i:j - 1] * nums[j]
    
    -> Dynamic programming
    
    dp[i][j] : product of nums[i:j]
    
    dp[i][j] = dp[i][j - 1] * nums[j]      if i < j
               nums[i]                     if i == j
    
    - time:  O(n^2)
    - space: O(n^2)
  */
  public int maxProductPoor(int[] nums) {
    int[][] dp = new int[nums.length][];
    for (int i = 0; i < nums.length; ++i) {
      dp[i] = new int[nums.length];
    }
    
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; ++i) {
      for (int j = i; j < nums.length; ++j) {
        if (i == j) {
          dp[i][j] = nums[i];
        } else {
          dp[i][j] = dp[i][j - 1] * nums[j];
        }
        max = Math.max(dp[i][j], max);
      }
    }
    
    return max;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 2, 3, -2, 4 },
        6
      },
      {
        new int[] { -2, 0, -1 },
        0
      },
      {
        new int[] { 1, -2 },
        1
      },
    };

    var solution = new MaximumProductSubarray();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var expected = (int) parameter[1];

      {
        var actual = solution.maxProductPoor(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
