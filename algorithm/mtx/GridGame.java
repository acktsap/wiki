import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/grid-game/
 *
 *
 * You are given a 0-indexed 2D array grid of size 2 x n, where grid[r][c] represents the number of points at position (r, c) on the matrix.
 * Two robots are playing a game on this matrix.
 * 
 * Both robots initially start at (0, 0) and want to reach (1, n-1).
 * Each robot may only move to the right ((r, c) to (r, c + 1)) or down ((r, c) to (r + 1, c)).
 * 
 * At the start of the game, the first robot moves from (0, 0) to (1, n-1), collecting all the points from the cells on its path.
 * For all cells (r, c) traversed on the path, grid[r][c] is set to 0.
 * Then, the second robot moves from (0, 0) to (1, n-1), collecting the points on its path.
 * Note that their paths may intersect with one another.
 * 
 * The first robot wants to minimize the number of points collected by the second robot.
 * In contrast, the second robot wants to maximize the number of points it collects.
 * If both robots play optimally, return the number of points collected by the second robot.
 *  
 * 
 * Example 1:
 * 
 * Input: grid = [[2,5,4],[1,5,1]]
 * Output: 4
 * Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
 * The cells visited by the first robot are set to 0.
 * The second robot will collect 0 + 0 + 4 + 0 = 4 points.
 *
 * Example 2:
 * 
 * Input: grid = [[3,3,1],[8,5,2]]
 * Output: 4
 * Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
 * The cells visited by the first robot are set to 0.
 * The second robot will collect 0 + 3 + 1 + 0 = 4 points.
 *
 * Example 3:
 * 
 * Input: grid = [[1,3,1,15],[1,3,3,1]]
 * Output: 7
 * Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
 * The cells visited by the first robot are set to 0.
 * The second robot will collect 0 + 1 + 3 + 3 + 0 = 7 points.
 *
 *
 * Constraints:
 * 
 * - grid.length == 2
 * - n == grid[r].length
 * - 1 <= n <= 5 * 10^4
 * - 1 <= grid[r][c] <= 10^5
 * 
 *
 *
 * Review
 *
 * 뭔가 찝찝하면 증명해보자.
 * 그리고 항상 overflow 생각. 처음부터 에초에 long리 리턴값이었음.
 *
 *
 */
class GridGame {
  /*
    양쪽에 대한 부분합?
    
    sum0[]
    sum1[]
    
    turnAt : turn to down index
    robot1 sum = (sum0[turnAt] - sum0[0]) + (sum1[last] - sum[turnAt - 1])
    robot2 sum = max(sum0[last] - sum0[turnAt], sum1[turnAt - 1] - sum1[0])
    
    robot1 sum을 최대화 하는 값이 robot2 sum을 최소화 하는 값이다?
    -> 아님... 다르게 바라봐야 함..
    -> robot2 sum을 최소화 하는 값으로 계산!!!
    
    - time: O(2*n)
    - space: O(2*n)
  */
  public long gridGame(int[][] grid) {
    long[] sum0 = new long[grid[0].length + 1];
    long[] sum1 = new long[grid[1].length + 1];
    
    for (int i = 1; i < sum0.length; ++i) {
      sum0[i] = grid[0][i - 1] + sum0[i - 1];
      sum1[i] = grid[1][i - 1] + sum1[i - 1];
    }
    
    int turnAt = 0;
    long min = Long.MAX_VALUE;
    for (int i = 1; i < sum0.length; ++i) {
      long sum = Math.max(sum0[sum0.length - 1] - sum0[i], sum1[i - 1] + sum1[0]);
      min = Math.min(sum, min);
    }
    
    return min;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] {
          { 2, 5, 4 },
          { 1, 5, 1 },
        },
        4L
      },
      {
        new int[][] {
          { 3, 3, 1 },
          { 8, 5, 2 },
        },
        4L
      },
      {
        new int[][] {
          { 1, 3, 1, 15 },
          { 1, 3, 3, 1 },
        },
        7L
      },
    };

    var solution = new GridGame();
    for (Object[] parameter : parameters) {
      var grid = (int[][]) parameter[0];
      var expected = (long) parameter[1];

      {
        var actual = solution.gridGame(grid);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
