/*
 * Descryption
 *
 * https://leetcode.com/problems/number-of-islands/
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * Jou may assume all four edges of the grid are all surrounded by water.
 *
 * Approach & Proof
 * 
 * dfsAll이 끝날 때 마다 ++count를 하고 return하면 될 듯.
 *
 * Loop Invarient : dfsAll에서 dfs를 한번 호출하면
 *
 * - 호출한 (i, j)와 연결되어 있는 point들에 대해 visited[i][j] == true
 * - 이전에 비해 count가 1 증가
 *
 * Complexity
 *
 * - Time : O(n*m) where n is # of i and y is # of j
 * 
 * Review
 * 
 * 전에 cpp로 풀었던 문제고 접근방식도 맞았으나.
 * char인걸 잊고 1하고 비교했음... '1'하고 비교했어야 했는데.
 * visited를 setup할 때 어떻게 해야 하나 고민했으나
 * for문을 돌면서 하는 식으로 함.
 * -> new booolean[][] 이러면 됨
 */
class NumberOfIslands {
  protected static int[] di = { 1, -1, 0, 0 };
  protected static int[] dj = { 0, 0, 1, -1 };
  protected static int nDirection = 4;
  
  public int numIslands(char[][] grid) {
    if (0 == grid.length) {
      return 0;
    }

    boolean[][] visited = new boolean[grid.length][grid[0].length];
    return dfsAll(grid, visited);
  }

  protected int dfsAll(char[][] grid, boolean[][] visited) {
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
  
  protected void dfs(char[][] grid, boolean[][] visited, int i, int j) {
    visited[i][j] = true;

    for (int n = 0; n < nDirection; ++n) {
      int nextI = i + di[n];
      int nextJ = j + dj[n];
      if (0 <= nextI && nextI < grid.length &&
            0 <= nextJ && nextJ < grid[i].length &&
            '1' == grid[nextI][nextJ] && !visited[nextI][nextJ]) {
        dfs(grid, visited, nextI, nextJ);
      }
    }
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
      char[][] grid = (char[][]) parameter[0];
      int expected = (int) parameter[1];
      int actual = solution.numIslands(grid);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected + ", but actual: " + actual);
      }
    }
  }
}
