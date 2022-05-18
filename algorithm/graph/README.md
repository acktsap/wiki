# Graph

- [Depth First Search](#depth-first-search)
  - [Traversing all Vertices](#traversing-all-vertices)
    - [By recursion (for adjacent list, recommanded)](#by-recursion-for-adjacent-list-recommanded)
    - [By recursion (for adjacent matrix)](#by-recursion-for-adjacent-matrix)
    - [By stack (for adjacent list, recommanded)](#by-stack-for-adjacent-list-recommanded)
    - [By stack (for adjacent matrix)](#by-stack-for-adjacent-matrix)
  - [Traversing all paths between two vertices](#traversing-all-paths-between-two-vertices)
- [Breath First Search](#breath-first-search)
- [Topoligical Sort](#topoligical-sort)
  - [Using Dfs](#using-dfs)
  - [Kahn's Algorithm](#kahns-algorithm)
- [Reference](#reference)

## Depth First Search

- More deeper vertex first.
- No deeper vertex, go back to a previous vertex.

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

### Traversing all paths between two vertices

todo

## Breath First Search

todo

## Topoligical Sort

### Using Dfs

- dfs가 끝날 때 마다 order에 집어넣고 이를 뒤집는다.

On adjacent list.

```cpp
vector<int> dfsAll(vector<vector<int>>& adjacents) {
  vector<int> order;

  vector<int> visited(adjacents.size(), 0);  
  for (int i = 0; i < visited.size(); ++i) {
    if (!visited[i]) {
      if (dfs(i, adjacents, order)) {
        cout << "cycle detected" << endl;
      }
    }
  }

  // reverse order
  reverse(order.begin(), order.end());

  return order;
}

bool dfs(int node, vector<vector<int>>& adjacents, vector<int>& order) {
  // cycle detected
  if (visited[node] == 1) {
    return true;
  }

  if (visited[node] == 2) {
    return false;
  }

  visited[node] = 1;
  for (int to = 0; to < adjacents[node].size(); ++to) {
    if (dfs(to, adjacents, visited)) {
      return true;
    }
  }
  visited[node] = 2;

  // insert to order
  order.push_back(node);

  // no cycle
  return false;
}
```

### Kahn's Algorithm

todo

## Reference

- [reakwon](https://reakwon.tistory.com/140)