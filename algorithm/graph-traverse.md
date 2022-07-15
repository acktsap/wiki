# Graph Traverse

- [Depth First Search](#depth-first-search)
  - [Traversing all Vertices](#traversing-all-vertices)
    - [By recursion (for adjacent list, recommanded)](#by-recursion-for-adjacent-list-recommanded)
    - [By recursion (for adjacent matrix)](#by-recursion-for-adjacent-matrix)
    - [By stack (for adjacent list, recommanded)](#by-stack-for-adjacent-list-recommanded)
    - [By stack (for adjacent matrix)](#by-stack-for-adjacent-matrix)
    - [Matrix](#matrix)
  - [Traversing all paths between two vertices](#traversing-all-paths-between-two-vertices)
- [Breath First Search](#breath-first-search)
  - [Traversing all vertices in the graph.](#traversing-all-vertices-in-the-graph)
    - [Graph](#graph)
  - [Shortest Path Between Two Vertices](#shortest-path-between-two-vertices)
- [Problems](#problems)
- [Reference](#reference)

## Depth First Search

- More deeper vertex first.
- No deeper vertex, go back to a previous vertex.

In Graph theory, the depth-first search algorithm (abbreviated as DFS) is mainly used to:

- Traverse all vertices in a graph.
- Traverse all paths between any two vertices in a graph.

### Traversing all Vertices

Let V be the number of vertices, and E be the number of edges. Then,

#### By recursion (for adjacent list, recommanded)

- Time: O(V + E)
  - We need to check every vertex and traverse through every edge in the graph.
- Space: O(V)
  - Either the manually created stack or the recursive call stack can store up to V vertices.

```cpp
void dfsAll(vector<vector<int>>& adjacents) {
  // initialize to false
  vector<bool> visited(adjacents.size(), false);  

  for (int i = 0; i < visited.size(); ++i) {
    if (!visited[i]) {
      dfs(i, adjacents, visited);
    }
  }
}

void dfs(int node, vector<vector<int>>& adjacents, vector<bool>& visited) {
  // pass visited one
  if (visited[node]) {
    return;
  }

  // mark from as visited
  visited[node] = true;

  /* process current vertex.. */

  // search next vertex
  for (int to : to < adjacents[node]) {
    dfs(to, adjacents, visited);
  }

  /* exit function, go back to previous vertex */
}
```

#### By recursion (for adjacent matrix)

- Time: O(V^2)
  - We need to check every vertex and traverse through every edge in the graph.
- Space: O(V)
  - Either the manually created stack or the recursive call stack can store up to V vertices.

```cpp
void dfsAll(vector<vector<int>>& adjacents) {
  // initialize to false
  vector<bool> visited(adjacents.size(), false);  

  for (int i = 0; i < visited.size(); ++i) {
    if (!visited[i]) {
      dfs(i, adjacents, visited);
    }
  }
}

void dfs(int node, vector<vector<int>>& adjacents, vector<bool>& visited) {
  if (visited[node]) {
    return;
  }

  // mark from as visited
  visited[node] = true;

  /* process current vertex.. */

  // search next vertex
  for (int to : adjacents[node]) {
    if (node != to && adjacents[node][to] == 1) {
      dfs(to, adjacents, visited);
    }
  }

  /* exit function, go back to previous vertex */
}
```

#### By stack (for adjacent list, recommanded)

#### By stack (for adjacent matrix)

- Time: O(V^2)
  - We need to check every vertex and traverse through every edge in the graph.
- Space: O(V)
  - Either the manually created stack or the recursive call stack can store up to V vertices.

```cpp
void dfs(vector<vector>>& adjacents, int node) {
  vector<bool> visited = vector<bool>()

  stack<int> stk;
  stk.push(node);

  while (!stk.empty()) {
    int from = stk.top();
    stk.pop();

    if (!visited[from]) {
      // mark from as visited
      visited[from] = true;

      /* process current vertex.. */

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

#### Matrix

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

#### Graph

- Time Complexity : O(m*n)
- Space Complexity : O(m*n)

```java
int[] dx = { 1, -1, 0, 0 };
int[] dy = { 0, 0, 1, -1 };

// todo
```

### Shortest Path Between Two Vertices

todo


## Problems

- [CloneGraph](./leetcode/CloneGraph.java)
- [CourseSchedule](./leetcode/CourseSchedule.java)
- [CourseScheduleII](./leetcode/CourseScheduleII.cpp)
- [GridGame](./leetcode/GridGame.java)
- [IslandPerimeter](./leetcode/IslandPerimeter.java)
- [NumberofEnclaves](./leetcode/NumberofEnclaves.cpp)
- [NumberOfIslands](./leetcode/NumberOfIslands.java)
- [PacificAtlanticWaterFlow](./leetcode/PacificAtlanticWaterFlow.java)

## Reference