# Matrix

## Depth First Search

In Graph theory, the depth-first search algorithm (abbreviated as DFS) is mainly used to:

- Traverse all vertices in a graph.
- Traverse all paths between any two vertices in a graph.

### Traversing all Vertices

- Time Complexity : O(m*n)
- Space Complexity : O(m*n)

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
  // check visited
  if (visited[i][j]) {
    return;
  }

  /* do someting here */

  // set visited to true
  visited[i][j] = true;

  // visit next
  for (int k = 0; k < 4; ++k) {
    int x = i + dx[k];
    int y = j + dy[k;
    // check index
    if (x < 0 || x >= visited.length || y < 0 || y >= visited[i].length) {
      continue;
    }

    /* check something here */

    // visit next
    dfs(x, y, visited);
  }
}
```

### Traversing all paths between two vertices

todo

## Breath First Search

In Graph theory, the primary use cases of the “breadth-first search” (“BFS”) algorithm are:

- Traversing all vertices in the graph.
- Finding the shortest path between two vertices in a graph where all edges have equal and positive weights.

### Traversing all vertices in the graph.

- Time Complexity : O(m*n)
- Space Complexity : O(m*n)

```java
int[] dx = { 1, -1, 0, 0 };
int[] dy = { 0, 0, 1, -1 };

// todo
```

### Shortest Path Between Two Vertices

todo
