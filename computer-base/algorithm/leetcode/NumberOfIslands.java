/*
 * Descryption
 *
 * https://leetcode.com/problems/number-of-islands/
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * Jou may assume all four edges of the grid are all surrounded by water.
 *
 * Approach
 * 
 * dfsAll이 끝날 때 마다 ++count를 하고 return하면 될 듯.
 *
 * Time : O(n*m) where n is # of x and y is # of y
 * 
 * Review
 * 
 * 전에 cpp로 풀었던 문제고 접근방식도 맞았으나.
 * char인걸 잊고 1하고 비교했음... '1'하고 비교했어야 했는데.
 * visited를 setup할 때 어떻게 해야 하나 고민했으나
 * for문을 돌면서 하는 식으로 함.
 */
class NumberOfIslands {
  protected static final int[] di = { 1, -1, 0, 0 };
  protected static final int[] dj = { 0, 0, 1, -1 };
  protected static final int nDirection = 4;
  
  public int numIslands(final char[][] grid) {
    final boolean[][] visited = setup(grid);
    return dfsAll(grid, visited);
  }
  
  protected boolean[][] setup(final char[][] grid) {
    final boolean[][] visited = new boolean[grid.length][];
    for (int y = 0; y < grid.length; ++y) {
      visited[y] = new boolean[grid[y].length];
    }
    return visited;
  }
  
  protected int dfsAll(final char[][] grid, final boolean[][] visited) {
    int count = 0;
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[i].length; ++j) {
        if ('1' == grid[i][j] && !visited[i][j]) {
          dfs(grid, visited, i, j);
          ++count;
        }
      }
    }
    return count;
  }
  
  protected void dfs(final char[][] grid, final boolean[][] visited, final int i, final int j) {
    visited[i][j] = true;
    for (int n = 0; n < nDirection; ++n) {
      final int nextI = i + di[n];
      final int nextJ = j + dj[n];
      if (0 <= nextI && nextI < grid.length &&
            0 <= nextJ && nextJ < grid[i].length &&
            '1' == grid[nextI][nextJ] && !visited[nextI][nextJ]) {
        dfs(grid, visited, nextI, nextJ);
      }
    }
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new char[][] {
                       { '1', '1', '1', '1', '0' },
                       { '1', '1', '0', '1', '0' },
                       { '1', '1', '0', '0', '0' },
                       { '0', '0', '0', '0', '0' },
        }
        , 1
      },
      {
        new char[][] {
                       { '1', '1', '0', '0', '0' },
                       { '1', '1', '0', '0', '0' },
                       { '0', '0', '1', '0', '0' },
                       { '0', '0', '0', '1', '1' },
        }
        , 3
      },
    };
    final NumberOfIslands solution = new NumberOfIslands();
    for (final Object[] parameter : parameters) {
      final char[][] grid = (char[][]) parameter[0];
      final int expected = (int) parameter[1];
      final int actual = solution.numIslands(grid);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected + ", but actual: " + actual);
      }
    }
  }
}
