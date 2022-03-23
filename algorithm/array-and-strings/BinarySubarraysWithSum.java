import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/binary-subarrays-with-sum/
 *
 * Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
 * 
 * A subarray is a contiguous part of the array.
 * 
 *
 * Example 1:
 * 
 * Input: nums = [1,0,1,0,1], goal = 2
 * Output: 4
 * Explanation: The 4 subarrays are bolded and underlined below:
 *
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 *
 * Example 2:
 * 
 * Input: nums = [0,0,0,0,0], goal = 0
 * Output: 15
 *  
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 3 * 10^4
 * nums[i] is either 0 or 1.
 * 0 <= goal <= nums.length
 *
 *
 *
 * Review
 *
 * prefix sum과 누적된 데이터를 map으로 저장...
 * array로 무식하게 세는 방식은 아름답지 못하다. 알고리즘은 깔끔해야 한다.
 *
 */
class BinarySubarraysWithSum {
  /*
    합... sliding window로 greedy하게 window 줄였다가 늘렸다가 해?
    애매한데... prefixSum?
    
    [1,0,1,0,1] 2
    [0,1,1,2,2,3]
    
    [0,0,0,0,0]
    [0,0,0,0,0,0]
    
    sum이 goal이라는 것은 두개의 pointer로 해서 difference가 goal이라는 뜻
    b - a = goal
    b = goal + a
    prefix sum을 iterating 하면서 (goal + next) to count를 넣고 이걸 이용해서 개수 추정
    
    loop invariant
    
    - remaining2Count: (goal + next)를 key로 하고 지금까지의 누적된 count를 value로 함
    - count: 누적된 count
    
    [1,0,1,0,1] 2
    [0,1,1,2,2,3]
    
    0 : (2 = 1)
    2 : (2 = 1, 3 = 2)
    4 : (2 = 1, 3 = 2, 4 = 2)
    5 : (2 = 1, 3 = 2, 4 = 2, 5 = 1)
    
    [0,0,0,0,0] 0
    [0,0,0,0,0,0]
    
    0 : (0 = 1)
    1 : (0 = 2)
    2 : (0 = 3)
    3 : (0 = 4)
    4 : (0 = 5)
    5 : (0 = 6)
    
    Let n be size of nums. Then
    - time: O(n)
    - space: O(n)
  */
  public int numSubarraysWithSum(int[] nums, int goal) {
    int[] prefixSum = new int[nums.length + 1];
    for (int i = 1; i <= nums.length; ++i) {
      prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
    }
    
    int sumCount = 0;
    Map<Integer, Integer> remaining2Count = new HashMap<>();
    for (int next : prefixSum) {
      sumCount += remaining2Count.getOrDefault(next, 0);
      int nextCount = remaining2Count.getOrDefault(goal + next, 0);
      remaining2Count.put(goal + next, nextCount + 1);
    }
    
    return sumCount;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 0, 1, 0, 1},
        2,
        4,
      },
      {
        new int[] { 1, 0, 1, 0, 1 },
        3,
        1,
      },
      {
        new int[] { 0, 0, 0, 0, 0 },
        0,
        15,
      },
      {
        new int[] { 0, 0, 0, 0, 0 },
        1,
        0,
      },
    };

    var solution = new BinarySubarraysWithSum();
    for (Object[] parameter : parameters) {
      var nums = (int[]) parameter[0];
      var goal = (int) parameter[1];
      var expected = (int) parameter[2];

      {
        var actual = solution.numSubarraysWithSum(nums, goal);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
