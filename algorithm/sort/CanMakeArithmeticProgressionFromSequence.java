import java.util.Arrays;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/
 *
 * A sequence of numbers is called an arithmetic progression if the difference between any two consecutive elements is the same.
 * 
 * Given an array of numbers arr, return true if the array can be rearranged to form an arithmetic progression. Otherwise, return false.
 * 
 *  
 * Example 1:
 * 
 * Input: arr = [3,5,1]
 * Output: true
 * Explanation: We can reorder the elements as [1,3,5] or [5,3,1] with differences 2 and -2 respectively, between each consecutive elements.
 *
 * Example 2:
 * 
 * Input: arr = [1,2,4]
 * Output: false
 * Explanation: There is no way to reorder the elements to obtain an arithmetic progression.
 *
 *
 *
 * Review
 *
 * sort 말고 다른 방법은 없을까?
 *
 */
class CanMakeArithmeticProgressionFromSequence{
  /*
    sort first
    let gap = arr[1] - arr[0]
    check for i in (2..arr.length - 1)
    
    time: O(nlog(n)) where n = arr.length
  */
  public boolean canMakeArithmeticProgression(int[] arr) {
    Arrays.sort(arr);
    int gap = arr[1] - arr[0];
    for (int i = 2; i < arr.length; ++i) {
      if (arr[i] - arr[i - 1] != gap) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 3, 5, 1 },
        true,
      },
      {
        new int[] { 1, 2, 4 },
        false,
      },
    };

    var solution = new CanMakeArithmeticProgressionFromSequence();
    for (Object[] parameter : parameters) {
      var arr = (int[]) parameter[0];
      var expected = (boolean) parameter[1];

      {
        var actual = solution.canMakeArithmeticProgression(arr);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
