import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Descryption
 *
 * https://leetcode.com/problems/3sum/
 *
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * 
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Example 2:
 * 
 * Input: nums = []
 * Output: []
 * Example 3:
 * 
 * Input: nums = [0]
 * Output: []
 *  
 * 
 * Constraints:
 * 
 * 0 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 *
 * 
 * Approach & Proof 
 * 
 * 3개중 한개를 고정하면 i = j + k 의 문제가 됨 (twoSum).
 * for i in 0 ~ n - 2
 *   for j in (i + 1) ~ n
 *     resolve twoSum
 *
 * Two Pointer solution
 *
 * 1. Map
 * 
 * a + b + c = 0 -> c = -a - b
 * Make a map c to b
 * 
 *
 * 2.
 * 
 * Sort first
 * 
 * i = 0
 * j = n
 * if (a + b + c > 0) {
 *   --j
 * } else if (a + b + c < 0) {
 *   ++i
 * } else {
 *   ++i
 *   --j
 * }
 *
 * 
 *
 *
 * Complexity
 *
 * Set solution
 *  - Time  : O(n^2)
 *  - Space : O(n) - return을 제외하면 최대 n만큼 set에 저장
 *
 * Two pointer solution
 *  - Time  : O(n^2)
 *  - Space : O(1) - return을 제외
 *
 *
 * Review
 * 
 * 중복이 없어야 해서 sort를 나중에 추가.
 * Arrays.asList도있었지..
 *
 * Sort를 하면 중복제거도 쉽고 two pointer도 쉽네!
 *
 *
 */
class ThreeSum {
  public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> ret = new HashSet<>();

    for (int i = 0; i < nums.length - 2; ++i) {
      int a = nums[i];

      // a + b + c = 0 -> c = -a - b
      Map<Integer, Integer> cTob = new HashMap<>();
      for (int j = i + 1; j < nums.length; ++j) {
        int next = nums[j];
        if (cTob.containsKey(next)) {
          int b = cTob.get(next);
          int c = next;
          List<Integer> candidate = Arrays.asList(a, b, c);
          Collections.sort(candidate);
          
          if (!ret.contains(candidate)) {
            ret.add(candidate);
          }
        } else {
          int b = next;
          int c = (-1) * (a + b);
          cTob.put(c, next);
        }
      }
    }

    return new ArrayList<>(ret);
  }

  public List<List<Integer>> threeSumTwoPointer(int[] nums) {
    List<List<Integer>> ret = new ArrayList<>();

    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 2; ++i) {
      // remove duplication
      if (i != 0 && nums[i] == nums[i - 1]) {
        continue;
      }

      int a = nums[i];
      int j = i + 1;
      int k = nums.length - 1;
      while (j < k) {
        int b = nums[j];
        int c = nums[k];
        int sum = a + b + c;
        if (sum < 0) {
          ++j;
        } else if (sum > 0) {
          --k;
        } else {
          // skip duplicate b
          while (j < k && nums[j] == nums[j + 1]) {
            ++j;
          }
          // skip duplicate c
          while (j < k && nums[k - 1] == nums[k]) {
            --k;
          }
          ret.add(Arrays.asList(a, b, c));
          ++j;
          --k;
        }
      }
    }

    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { -1, 0, 1, 2, -1, -4 },
        Arrays.asList(
          Arrays.asList(-1, -1, 2),
          Arrays.asList(-1, 0, 1)
        ),
      },
    };

    var solution = new ThreeSum();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      List<List<Integer>> expected = (List<List<Integer>>) parameter[1];

      var actual1 = solution.threeSum(input);
      if (!Set.copyOf(actual1).equals(Set.copyOf(expected))) {
        throw new IllegalStateException("1 - Expected: " + expected +
            ", but was: " + actual1);
      }

      var actual2 = solution.threeSumTwoPointer(input);
      if (!Set.copyOf(actual2).equals(Set.copyOf(expected))) {
        throw new IllegalStateException("2 - Expected: " + expected +
            ", but was: " + actual2);
      }
    }
  }
}
