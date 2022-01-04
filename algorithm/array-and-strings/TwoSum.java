import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Descryption
 *
 * https://leetcode.com/problems/two-sum/
 *
 * Given an array of integers, return indices of the two numbers
 * such that they add up to a specific target.
 * You may assume that each input would have exactly one solution,
 * and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 *
 * Approach & Proof 
 *
 * 루프 돌 때 마다 
 * nums iterating하면서 (nums[i] to i) 를 map entry로 집어넣어
 * 이것은 j기준으로 target까지 채우기 위한 left값임
 * target - num[j] (== left) 를 key로 하는게 있으면 그걸 값으로 해서 break
 *
 * loop invariant
 *
 * - left2Index: i - 1까지 target이 되기 까지 필요한 값을 들고 있음
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(n)
 *
 *
 * Review
 *
 * 처음에 map의 key에 target - nums[i]를 넣음..
 * j 기준으로는 nums[i]를 넣어야 key값이 left가 되는건데
 *
 * java map이 contains가 없고 containsKey가 있다는걸 첨알음
 * 맨날 value = map.get("key"); if (null != value) 이래서 그런가..
 * java map
 *  - put
 *  - get
 *  - containsKey
 *  - containsValue
 *
 */
class TwoSum {
  public int[] twoSum(int[] nums, int target) {
    int[] ret = new int[2];
    Map<Integer, Integer> left2Index = new HashMap<>();
    for (int i = 0; i < nums.length; ++i) {
      int next = nums[i];
      if (null != left2Index.get(next)) {
        ret[0] = left2Index.get(next);
        ret[1] = i;
        break;
      }
      left2Index.put(target - next, i);
    }
    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 2, 7, 11, 15 },
            9,
            new int[] { 0, 1 }
      },
        {
          new int[] { 4, 10, 5, 17, 2, 23 },
          12,
          new int[] { 1, 4 }
        },
    };

    var solution = new TwoSum();
    for (Object[] parameter : parameters) {
      int[] nums = (int[]) parameter[0];
      int target = (int) parameter[1];
      int[] expected = (int[]) parameter[2];
      int[] actual = solution.twoSum(nums, target);
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
