# DFS

- [Grid search (By recursion)](#grid-search-by-recursion)
- [Time Complexity of DFS](#time-complexity-of-dfs)
- [Usage of DFS](#usage-of-dfs)
  - [Is two vertices u, v connected](#is-two-vertices-u-v-connected)
  - [Find # of connected components](#find--of-connected-components)
  - [Find # of elements of connected components](#find--of-elements-of-connected-components)

## Grid search (By recursion)

```java
public class Solution {
  protected static final int nDirection = 4;
  protected static final int[] di = { 1, -1, 0, 0 };
  protected static final int[] dj = { 0, 0, 1, -1 };

  public int dfsAll(final int[][] grid) {
    final boolean[][] visited = new boolean[grid.length][grid[0].length];
    int count = 0; // # of island
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[i].length; ++j) {
        if (1 == grid[i][j] && !visited[i][j]) {
          dfs(grid, visited, i, j);
          ++count;
        }
      }
    }
    return count;
  }

  protected void dfs(final int[][] grid, final boolean[][] visited, final int i, final int j) {
    // mark as visited
    visited[i][j] = true;

    // do something

    // visit next
    for (int n = 0; n < nDirection; ++n) {
      final int nextI = i + di[n];
      final int nextJ = j + dj[n];
      if (0 <= nextI && nextI < grid.length &&
          0 <= nextJ && nextJ < grid[i].length &&
          1 == grid[nextI][nextJ] && !visited[nextI][nextJ]) {
        dfs(grid, visited, nextI, nextJ);
      }
    }
  }
```

## Time Complexity of DFS

Check all edges of all vertices

- adjacent list[by recursion] : O(|V|+|E|);
- adjacent list[by stack] : O(|E|) why?
- adjacent matrix : O(|V|^2);  // adjacent matrix is a V*V matrix

## Usage of DFS

### Is two vertices u, v connected

Execute dfs(u), if visited[v] = true, u and v are connected

### Find # of connected components

count++ on every dfs call exits

```java
int dfsAll() {
  int count = 0;
  for (int i = 0; i < map.length; ++i) {
    for (int j = 0; j < map[0].length; ++j) {
      if (!visited[i][j]) {
        dfs(i, j);
        ++count;
      }
    }
  }
  return count;
}
```

### Find # of elements of connected components

Count dfs call count

```java
int dfs(int from) {
  int connectedCount = 1;
  visited[from] = true;
  for (int to = 0; to < map[from].size(); ++to) {
    if (map[from][to] == 1 && !visited[to]) {
      connectedCount += dfs(to);
    }
  }
  return connectedCount;
}
```
