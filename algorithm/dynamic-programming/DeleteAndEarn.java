import java.util.Objects;
import java.util.Map;
import java.util.HashMap;

/*
 * Descryption
 *
 * https://leetcode.com/problems/delete-and-earn/
 *
 * You are given an integer array nums.
 * You want to maximize the number of points you get by performing the following operation any number of times:
 * 
 * Pick any nums[i] and delete it to earn nums[i] points.
 * Afterwards, you must delete every element equal to nums[i] - 1 and every element equal to nums[i] + 1.
 * Return the maximum number of points you can earn by applying the above operation some number of times.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [3,4,2]
 * Output: 6
 * Explanation: You can perform the following operations:
 * - Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].
 * - Delete 2 to earn 2 points. nums = [].
 * You earn a total of 6 points.
 *
 * Example 2:
 * 
 * Input: nums = [2,2,3,3,3,4]
 * Output: 9
 * Explanation: You can perform the following operations:
 * - Delete a 3 to earn 3 points. All 2's and 4's are also deleted. nums = [3,3].
 * - Delete a 3 again to earn 3 points. nums = [3].
 * - Delete a 3 once more to earn 3 points. nums = [].
 * You earn a total of 9 points.
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 2 * 10^4
 * 1 <= nums[i] <= 10^4
 *
 *
 *
 * Review
 *
 * 사고의 절차를 다 적어보자.. 좋네
 *
 *
 */
class DeleteAndEarn {
  /*
    Idea
    
    Brute force takes too long (~O(n^n))
    
    한개 잡아서 얻으면 +1, -1인 녀석들 다 죽임
    이걸 계속 반복
    3,3,3,4 -> 3을 취하고 4를 죽여
    3,4 -> 4를 취하고 3을 죽여
    
    죽이고 +1, -1 set으로 저장
    
    f(nums) = nums[0]   n == 1
            = max(nums[0], nums[1])   n == 2
            = max(
                nums[0] + f(nums, trim nums[0] related),
                nums[1] + f(nums, trim nums[1] related),
                nums[2] + f(nums, trim nums[2] related),
              )   n == 3
     
     umm.. 이건 좀 별로야. 차라리 map으로 저장하고 greedy?
     
     3 set
     
     - a
     - a + 1
     - a - 1
     
     일단 큰거 먼저 죽이는데 큰거보다 1 작은게 제일 큰거의 수보다 1개 많으면 작은거 먼저 죽임
     그런데 만약 큰거보다 2 작은게 많다면?
     
     f(a) : a까지 취했을 때 max값
     f(a) = max(a * count(a) + f(a - 2), f(a - 1))
     
     적용

     f(4) = max(4 * 1 + f(2), f(3))
     f(3) = max(3 * 3 + f(1), f(2))
     f(2) = max(2 * 2 + f(0), f(1))
     f(1) = 0
     f(0) = 0
     
     overlapping subproblem & optimal substructure -> dp
     10^4로 제약, 그냥 그걸로 가자
     아니야 maxValue 알 수 있으니까 그걸로
     
     Let n be nums.length, maxValue be maximum value of it.
     - time: O(n + maxValue)
     - space: O(n + maxValue)
  */
  public int deleteAndEarn(int[] nums) {
    Map<Integer, Integer> num2Count = new HashMap<>();
    int maxValue = 0;
    for (int num : nums) {
      int count = num2Count.getOrDefault(num, 0);
      num2Count.put(num, count + 1);
      maxValue = Math.max(num, maxValue);
    }
    
    int[] dp = new int[maxValue + 1];
    dp[1] = num2Count.getOrDefault(1, 0);
    for (int i = 2; i <= maxValue; ++i) {
      dp[i] = Math.max(i * num2Count.getOrDefault(i, 0) + dp[i - 2], dp[i - 1]);
    }
    
    return dp[maxValue];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 3, 4, 2 },
        6
      },
      {
        new int[] { 2, 2, 3, 3, 3, 4 },
        9
      },
    };

    var solution = new DeleteAndEarn();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var expected = (int) parameter[1];

      {
        var actual = solution.deleteAndEarn(nums);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
