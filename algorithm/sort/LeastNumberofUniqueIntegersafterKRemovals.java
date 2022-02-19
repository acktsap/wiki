import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/
 *
 * Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly k elements.
 * 
 * 
 * Example 1:
 * 
 * Input: arr = [5,5,4], k = 1
 * Output: 1
 *
 * Explanation: Remove the single 4, only 5 is left.
 *
 *
 * Example 2:
 *
 * Input: arr = [4,3,1,1,3,3,2], k = 3
 * Output: 2
 *
 * Explanation: Remove 4, 2 and either one of the two 1s or three 3s. 1 and 3 will be left.
 *  
 * 
 * Constraints:
 * 
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^9
 * 0 <= k <= arr.length
 *
 *
 *
 * Review
 *
 */
class LeastNumberofUniqueIntegersafterKRemovals {

  // TODO: heap 써서 하는 방법 추가

  /*
    Loop invariant
    
    num2Count : value -> the number of value
    count2TypeCount : count -> the number of value type
    
    - time:  O(3n)
    - space: O(2n)
  */
  public int findLeastNumOfUniqueIntsLinear(int[] arr, int k) {
    Map<Integer, Integer> num2Count = new HashMap<>();
    
    for (int num : arr) {
      Integer count = num2Count.get(num);
      if (count == null) {
        num2Count.put(num, 1);
      } else {
        num2Count.put(num, count + 1);
      }
    }
    
    Map<Integer, Integer> count2TypeCount = new HashMap<>();
    for (Integer count : num2Count.values()) {
      Integer typeCount = count2TypeCount.get(count);
      if (typeCount == null) {
        count2TypeCount.put(count, 1);
      } else {
        count2TypeCount.put(count, typeCount + 1);
      }
    }
    
    int skipCount = k;
    int leftCount = 0;
    for (int i = 1; i <= arr.length; ++i) {
      Integer typeCount = count2TypeCount.get(i);
      if (typeCount == null) {
        continue;
      }
      
      if (skipCount <= 0) {
        leftCount += typeCount;
      } else {
        skipCount -= i * typeCount;
        if (skipCount < 0) {
          if (skipCount % i == 0) {
            leftCount += ((-1) * skipCount / i);
          } else {
            leftCount += ((-1) * skipCount / i + 1);
          }
        }
      }
    }
   
    return leftCount;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 5, 5, 4 },
        1,
        1
      },
      {
        new int[] { 4, 3, 1, 1, 3, 3, 2 },
        3,
        2
      },
      {
        new int[] { 1, 1, 2, 2, 3, 3 },
        1,
        3
      },
    };

    var solution = new LeastNumberofUniqueIntegersafterKRemovals();
    for (Object[] parameter : parameters) {
      var arr = (int[]) parameter[0];
      var k = (int) parameter[1];
      var expected = (int) parameter[2];

      {
        var actual = solution.findLeastNumOfUniqueIntsLinear(arr, k);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
