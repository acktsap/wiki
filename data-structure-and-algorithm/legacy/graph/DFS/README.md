# DFS

- [DFS](#dfs)
  - [Depth First Search](#depth-first-search)
  - [By recursion using adjacent matrix](#by-recursion-using-adjacent-matrix)
  - [Grid search (By recursion)](#grid-search-by-recursion)
  - [By stack (adjacent matrix)](#by-stack-adjacent-matrix)
  - [Time Complexity of DFS](#time-complexity-of-dfs)
  - [Usage of DFS](#usage-of-dfs)
    - [Is two vertices u, v connected](#is-two-vertices-u-v-connected)
    - [Find # of connected components](#find--of-connected-components)
    - [Find # of elements of connected components](#find--of-elements-of-connected-components)

## Depth First Search

- more deeper vertex first
- no deeper vertex, go back to a previous vertex
- Implementation : by recursion | by stack

## By recursion using adjacent matrix

```cpp
vector<vector<bool>> adjacents;  // adjacent matrix
vector<bool> visited;  // whether visited or not for each vertex

/*
  Why dfsAll?
  In mose case, dfs is used to reveal overall structore of the graph
  So, we need to put dfs for all vertices

  ex)
  A — B   D — E
  |
  C

  If dfs is excuted only in A, righter graph can't be searched
*/
void dfsAll() {
  visited = vector<bool>(adjacents.size(), false);  // initialize to false
  for (int i = 0; i < visited.size(); ++i) {
    if (!visited[i]) {
      dfs(i);
    }
  }
}

void dfs(int from) {
  // 1. mark from as visited
  visited[from] = true;

  // 2. process current vertex

  // 3. search next vertex
  for (int to = 0; to < adjacents[from].size(); ++to) {
    if (adjacents[from][to] && !visited[to]) {
      dfs(to);
    }
  }

  // exit function, go back to previous vertex
}
```

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

## By stack (adjacent matrix)

```cpp
void dfs(int start) {
  stack<int> stk;
  stk.push(start);

  while (!stk.empty()) {
    int from = stk.top(); stk.pop();

    if (!visited[from]) {
      // mark from as visited
      visited[from] = true;

      // process current vertex

      // search next vertex
      for (int to = 0; to < adjacents[from].size(); ++to) {
        if (adjacents[from][to] == true && !visited[to]) {
          stk.push(to);
        }
      }
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
