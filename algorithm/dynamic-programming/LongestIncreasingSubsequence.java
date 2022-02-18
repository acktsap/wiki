import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/longest-increasing-subsequence/
 *
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * 
 * A subsequence is a sequence that can be derived from an array
 * by deleting some or no elements without changing the order of the remaining elements.
 *
 * For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 * 
 *
 * Example 1:
 * 
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Example 2:
 * 
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 *
 * Example 3:
 * 
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 *  
 * Constraints:
 * 
 * 1 <= nums.length <= 2500
 * -104 <= nums[i] <= 104
 *
 *
 *
 * Review
 *
 * 점화식으로 가볍게 풀었는데 이걸 O(n*log(n))으로 어떻게하지?
 *
 */
class LongestIncreasingSubsequence {
  /*
    dp[n] : size of longest increasing subsequence amoung nums[0:n]

    dp[n] = 1 + Max(dp[i])   where i < n && nums[i] < nums[n]

    - Time  : O(n^2)
    - Space : O(n)
  */
  public int lengthOfLIS(int[] nums) {
    int ret = 0;
    
    int[] dp = new int[nums.length];
    for (int i = 0; i < nums.length; ++i) {
      int candidate = 0;
      for (int j = i - 1; j >= 0; --j) {
        if (nums[j] < nums[i]) {
          candidate = Math.max(candidate, dp[j]);
        }
      }
      dp[i] = 1 + candidate;
      ret = Math.max(ret, dp[i]);
    }
    
    return ret;
  }

  public int lengthOfLISTimeOptimal(int[] nums) {
    // TODO: resolve by nlog(n)
    return 0;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 10, 9, 2, 5, 3, 7, 101, 18 },
        4
      },
      {
        new int[] { 0, 1, 0, 3, 2, 3, 5 },
        5
      },
      {
        new int[] { 7, 7, 7, 7, 7, 7 },
        1
      },
      {
        new int[] { 3 },
        1
      },
      {
        new int[] { 100, 101, 0, 1, 2, 3, 4, 5, 2, 3, 4, 7, 8, 9, 10, 5 },
        10
      },
    };

    var solution = new LongestIncreasingSubsequence();
    for (Object[] parameter : parameters) {
      int[] nums = (int[]) parameter[0];
      int expected = (int) parameter[1];

      {
        int actual = solution.lengthOfLIS(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
