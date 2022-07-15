# Topoligical Sort

- [What for?](#what-for)
- [Using DFS](#using-dfs)
- [Kahn's Algorithm (BFS)](#kahns-algorithm-bfs)
- [Reference](#reference)

## What for?

## Using DFS

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

## Kahn's Algorithm (BFS)

todo

 Topoligical Sort

## Reference

- [reakwon](https://reakwon.tistory.com/140)