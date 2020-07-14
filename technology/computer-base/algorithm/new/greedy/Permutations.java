import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/*
 * Descryption
 *
 * https://leetcode.com/problems/permutations/
 *
 * Given a collection of distinct integers, return all possible permutations.
 * 
 * Example:
 * 
 * Input: [1,2,3]
 * Output:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 *
 * Approach & Proof 
 *
 * dfs
 *
 * 1. mark it used; add current to list
 * 2. 
 *
 * loop invarient
 *
 * - used (array): holds used keys
 *
 *
 * Complexity
 *
 *  - Time  :
 *  - Space :
 *
 *
 * Review
 *
 *
 */
class Permutations {
  public List<List<Integer>> permute(final int[] nums) {
    return Collections.emptyList();
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 3 },
        new int[][] {
          { 1, 2, 3 },
          { 1, 3, 2 },
          { 2, 1, 3 },
          { 2, 3, 1 },
          { 3, 1, 2 },
          { 3, 2, 1 },
        }
      },
    };
    final Permutations solution = new Permutations();
    for (final Object[] parameter : parameters) {
      final int[] input = (int[]) parameter[0];
      final int[][] expected = (int[][]) parameter[1];
      final Integer[][] actual = solution.permute(input).stream()
            .map(l -> l.stream().toArray(Integer[]::new))
            .toArray(Integer[][]::new);
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
