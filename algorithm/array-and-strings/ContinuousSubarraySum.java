import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/continuous-subarray-sum/
 *
 *
 * Given an integer array nums and an integer k,
 * return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.
 * 
 * An integer x is a multiple of k if there exists an integer n such that x = n * k.
 * 0 is always a multiple of k.
 *  
 * 
 * Example 1:
 * 
 * Input: nums = [23,2,4,6,7], k = 6
 * Output: true
 * Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.
 *
 * Example 2:
 * 
 * Input: nums = [23,2,6,4,7], k = 6
 * Output: true
 * Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
 * 42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.
 *
 * Example 3:
 * 
 * Input: nums = [23,2,6,4,7], k = 13
 * Output: false
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 * 0 <= sum(nums[i]) <= 2^31 - 1
 * 1 <= k <= 2^31 - 1
 * 
 * 
 *
 * Review
 *
 * 코너케이스~ 안하다 보면 코너케이스 보는 감을 잃는듯
 *
 *
 */
class ContinuousSubarraySum {

  /*
    부분합으로 해서 전체탐색? 그러면 O(n^2) (n-1 + n-2 + ... + 1)
    
    중요한건 두개 차 해서 k로 mod한 결과가 0인지임. 
    mod는 분배법칙이 성립하니까 그러면..
    
    (b - a) % k == 0인지를 알아야 함.
    즉, (b - a) / k = z -> b = kz + a
    
    여기서 kz + a를 hash로 한번에 알 수 있으면 O(n)으로 가능
    kz에서 문제는 z를 모른다는것.. mod 연산을 적용해보면
    
    b % k = z + a % k
    
    ?? 그냥 b % k - a % k == 0인거만 보면 되잖아? a % k를 저장해두면 되겠네.
    그런데 size가 최소 2여야 하네. buffer 한개 두고 나중에 처리하면 되겠지.
    
      23  2  6  4  7
    0 23 25 31 35 42
    0  5  1  1  5  7
    
      1 2 3 
    0 1 3 6
  */
  public boolean checkSubarraySumSet(int[] nums, int k) {
    if (nums.length == 1) {
      return false;
    }
    
    int[] prefixSum = new int[nums.length + 1];
    for (int i = 1; i < prefixSum.length; ++i) {
      prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
    }
    
    Set<Integer> leftSet = new HashSet<>();
    leftSet.add(0);
    
    int buffer = prefixSum[1] % k;
    for (int i = 2; i < prefixSum.length; ++i) {
      int next = prefixSum[i] % k;
      if (leftSet.contains(next)) {
        return true;
      }
      
      leftSet.add(buffer);
      buffer = next;
    }
    
    return false;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 23, 2, 4, 6, 7 },
        6,
        true,
      },
      {
        new int[] { 23, 2, 6, 4, 7 },
        6,
        true,
      },
      {
        new int[] { 23, 2, 6, 4, 7 },
        13,
        false,
      },
      {
        new int[] { 0 },
        1,
        false,
      },
      {
        new int[] { 1, 2, 3 },
        5,
        true,
      },
    };

    var solution = new ContinuousSubarraySum();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var k = (int) parameter[1];
      var expected = (boolean) parameter[2];

      {
        var actual = solution.checkSubarraySumSet(nums, k);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
