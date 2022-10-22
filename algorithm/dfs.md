# Dfs

Depth First Search, 깊이 우선 탐색. graph에 쓰이든 어디 쓰이든 그게 다임.

```cpp
void dfsAll(vector<int>& arr) {
  // initialize to false
  vector<bool> visited(arr.size(), false);  

  for (int i = 0; i < visited.size(); ++i) {
    if (!visited[i]) {
      dfs(i, arr, visited);
    }
  }
}

void dfs(int i, vector<>& arr, vector<bool>& visited) {
  // pass visited one
  if (visited[i]) {
    return;
  }

  // mark from as visited
  visited[i] = true;

  /* process current one */

  /* call next dfs based on search criteria  */

  /* exit function, go back to previous one */
}
```

## Problems

- [JumpGameIII](./leetcode/JumpGameIII.cpp)
- [AverageofLevelsinBinaryTree](./leetcode/AverageofLevelsinBinaryTree.cpp)