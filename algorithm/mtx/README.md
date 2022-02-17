# Matrix

## Depth First Search

```java
int[] dx = { 1, -1, 0, 0 };
int[] dy = { 0, 0, 1, -1 };

void dfsAll(int[][] matrix) {
  boolean[][] visited = new int[matrix.length][];
  for (int i = 0; i < matrix.length; ++i) {
    visited[i] = new boolean[matrix[i].length];
  }

  for (int i = 0; i < matrix.length; ++i) {
    for (int j = 0; j < matrix[i].length; ++j) {
      if (!visited[i][j]) {
        dfs(i, j, visited);
      }
    }
  }
}

// dfs, check condition first
void dfs(int i, int j, boolena[][] visited) {
  // check index
  if (i < 0 || visited.length <= i || j < 0 || visited[i].length <= j) {
    return;
  }

  // check visited
  if (visited[i][j]) {
    return;
  }

  /* add someting here */

  // set visited to true
  visited[i][j] = true;

  // visit next
  for (int k = 0; k < 4; ++k) {
    dfs(i + dx[k], j + dy[k], visited);
  }
}
```
