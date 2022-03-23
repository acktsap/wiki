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
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 10^5
 *
 *
 *
 * Review
 *
 * O(n^2)라도 같은 O(n^2)가 아니다..
 * 그리고 dp인데 O(n^2)면 느린거다..
 *
 */
class JumpGame {
  /*
    뒤에서부터 시작해서 Greedy하게 해보자.

    [2,3,1,1,4]
    4 -> 1 -> 1로 가서 j를 빠르게 갱신하더라도 결국 4 -> 3 때문에 3으로 감

    [2,3,0,1,4]
    중간에 끊어져서 j를 조금 더 앞에 두더라도 결국 큰게 해당 range를 커버
    j가 1로 가더라도 3에서 어차피 1, 4 둘다 갈 수 있음 그래서 1로가도 ㄱㅊ

    i, j가 있다 i는 줄어들고 j는 고정
    if (j - i <= nums[i]) {
      j = i;
    }
    
    Let n be size of nums. Then,
    - time: O(n)
    - space: O(1)
  */
  public boolean canJumpGreedy(int[] nums) {
    int j = nums.length - 1;
    for (int i = nums.length - 2; i >= 0; --i) {
      if (j - i <= nums[i]) {
        j = i;
      }
    }

    return j == 0;
  }

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
    
    for (int i = 1; i < nums.length; ++i) {
      for (int j = i - 1; j >= 0; --j) {
        if (dp[j] && nums[j] >= (i - j)) {
          dp[i] = true;
          break;
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
        var actual = solution.canJumpGreedy(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

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
