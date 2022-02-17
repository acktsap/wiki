import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/number-of-islands/
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * Jou may assume all four edges of the grid are all surrounded by water.
 *
 *
 * Example 1:
 * 
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 *
 * Example 2:
 * 
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *  
 * 
 * Constraints:
 * 
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 *
 *
 *
 * Review
 *
 * 패턴 숙지
 *
 */
class NumberOfIslands {
  int[] dx = new int[] { 1, -1, 0, 0 };
  int[] dy = new int[] { 0, 0, 1, -1 };
  
  /*
    dfs, increase count on each dfs sequence
    
    - time : O(i * j)
    - space: O(i * j)
  */
  public int numIslandsDfs(char[][] grid) {
    boolean[][] visited = new boolean[grid.length][];
    for (int i = 0; i < grid.length; ++i) {
      visited[i] = new boolean[grid[i].length];
    }
    
    int count = 0;
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[i].length; ++j) {
        if (!visited[i][j] && grid[i][j] == '1') {
          dfs(i, j, grid, visited);
          ++count;
        }
      }
    }
    
    return count;
  }
  
  protected void dfs(int i, int j, char[][] grid, boolean[][] visited) {
    if (i < 0 || visited.length <= i || j < 0 || visited[i].length <= j) {
      return;
    }
    
    if (visited[i][j]) {
      return;
    }
    
    if (grid[i][j] == '0') {
      return;
    }
    
    visited[i][j] = true;
    for (int k = 0; k < 4; ++k) {
      dfs(i + dx[k], j + dy[k], grid, visited);
    }
  }

  public int numIslandsBfs(char[][] grid) {
    // TODO
    return 0;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new char[0][0],
        0,
      },
      {
        new char[][] {
           { '1', '1', '1', '1', '0' },
           { '1', '1', '0', '1', '0' },
           { '1', '1', '0', '0', '0' },
           { '0', '0', '0', '0', '0' },
        },
        1,
      },
      {
        new char[][] {
           { '1', '1', '0', '0', '0' },
           { '1', '1', '0', '0', '0' },
           { '0', '0', '1', '0', '0' },
           { '0', '0', '0', '1', '1' },
        },
        3,
        new char[][] {
           { '1', '0', '1', '0', '1' },
           { '0', '1', '0', '1', '0' },
           { '1', '0', '1', '0', '1' },
           { '0', '1', '0', '1', '0' },
        },
        10,
      },
    };

    NumberOfIslands solution = new NumberOfIslands();
    for (Object[] parameter : parameters) {
      var grid = (char[][]) parameter[0];
      var expected = (int) parameter[1];

      {
        var actual = solution.numIslandsDfs(grid);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
