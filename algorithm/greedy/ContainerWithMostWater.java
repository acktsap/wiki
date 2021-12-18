/*
 * Descryption
 *
 * https://leetcode.com/problems/container-with-most-water/
 *
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 * 
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * 
 * Return the maximum amount of water a container can store.
 * 
 * Notice that you may not slant the container.
 * 
 * Example 1:
 * 
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 *
 * Example 2:
 * 
 * Input: height = [1,1]
 * Output: 1
 *  
 * 
 * Constraints:
 * 
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 *
 *
 * Approach & Proof 
 *
 * Two pointer로 해서 greedy하게 접근하면 되지 않을까?
 *
 * TODO: greedy 증명
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(1)
 *
 *
 * Review
 *
 *
 */
class ContainerWithMostWater {
  public int maxArea(int[] height) {
    int max = 0;
    
    int i = 0;
    int j = height.length - 1;
    while (i < j) {
      max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
      if (height[i] < height[j]) {
        ++i;
      } else {
        --j;
      }
    }
    
    return max;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 },
        49,
      },
    };

    var solution = new ContainerWithMostWater();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      int expected = (int) parameter[1];
      int actual = solution.maxArea(input);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
