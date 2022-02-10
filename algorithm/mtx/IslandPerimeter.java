/*
 * Descryption
 *
 * https://leetcode.com/problems/island-perimeter/
 *
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water.
 * Grid cells are connected horizontally/vertically (not diagonally).
 * The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells).
 * The island doesn't have "lakes" (water inside that isn't connected to the water around the island).
 * One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100.
 * Determine the perimeter of the island.
 *
 * Approach
 *
 * dfs
 *  - default count = 4
 *  - if visited[nextI][nextJ] -> --count;
 *  - if !visited[nextI][nextJ] -> --count; count += dfs(nextI, nextJ)
 *
 * 전체를 순회하면서 4방면을 뒤져보는 것이 아니라 차있는 grid를 찾아서 순회.
 *
 * Time : (# of node * 4)
 * 
 * Review
 *
 * 그냥 꼼수 같은데...
 *
 */
class IslandPerimeter {
  protected static int nDirection = 4;
  protected static int[] di = { 1, -1, 0, 0 };
  protected static int[] dj = { 0, 0, 1, -1 };

  public int islandPerimeter(int[][] grid) {
    boolean[][] visited = new boolean[grid.length][grid[0].length];
    int ret = 0;
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[i].length; ++j) {
        if (1 == grid[i][j] && !visited[i][j]) {
          ret = dfs(grid, visited, i, j);
          break;
        }
      }
    }
    return ret;
  }

  protected int dfs(int[][] grid, boolean[][] visited, int i, int j) {
    visited[i][j] = true;
    int count = 4;
    for (int n = 0; n < nDirection; ++n) {
      int nextI = i + di[n];
      int nextJ = j + dj[n];
      if (0 <= nextI && nextI < grid.length &&
            0 <= nextJ && nextJ < grid[i].length) {
        if (1 == grid[nextI][nextJ]) {
          --count;
          if (!visited[nextI][nextJ]) count += dfs(grid, visited, nextI, nextJ);
        }
      }
    }
    return count;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] {
                       { 0, 1, 0, 0 },
                       { 1, 1, 1, 0 },
                       { 0, 1, 0, 0 },
                       { 1, 1, 0, 0 },
        }
        , 16
      },
    };
    IslandPerimeter solution = new IslandPerimeter();
    for (Object[] parameter : parameters) {
      int[][] grid = (int[][]) parameter[0];
      int expected = (int) parameter[1];
      int actual = solution.islandPerimeter(grid);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected + ", but actual: " + actual);
      }
    }
  }
}
