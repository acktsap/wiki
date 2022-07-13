#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/redundant-connection/
 *
 * In this problem, a tree is an undirected graph that is connected and has no cycles.
 *
 * You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added.
 * The added edge has two different vertices chosen from 1 to n, and was not an edge that already existed.
 * The graph is represented as an array edges of length n
 * where edges[i] = [a_i, b_i] indicates that there is an edge between nodes a_i and b_i in the graph.
 *
 * Return an edge that can be removed so that the resulting graph is a tree of n nodes.
 * If there are multiple answers, return the answer that occurs last in the input.
 *
 *
 * Example 1:
 *
 * Input: edges = [[1,2],[1,3],[2,3]]
 * Output: [2,3]
 *
 * Example 2:
 *
 * Input: edges = [[1,2],[2,3],[3,4],[1,4],[1,5]]
 * Output: [1,4]
 *
 *
 * Constraints:
 *
 * - n == edges.length
 * - 3 <= n <= 1000
 * - edges[i].length == 2
 * - 1 <= a_i < b_i <= edges.length
 * - a_i != b_i
 * - There are no repeated edges.
 * - The given graph is connected.
 *
 *
 *
 * Review
 *
 *
 */
class RedundantConnection {
public:
  /*
    union find 해서 parent를 계속 유지
    parent가 다른 경우가 나오면 그 친구를 return
    즉, cycle이 만들어지면 리턴

    - time : O(n)
    - space : O(n)
  */
  vector<int> findRedundantConnectionUnionFind(vector<vector<int>>& edges) {
    vector<int> parents(edges.size() + 1, 0);
    for (vector<int>& edge : edges) {
      int parent1 = findParent(parents, edge[0]);
      int parent2 = findParent(parents, edge[1]);

      if (parent1 == parent2) {
        return edge;
      }

      // union
      parents[parent2] = parent1;
    }

    return { };
  }

  int findParent(vector<int>& parents, int vertex) {
    int current = vertex;
    while (parents[current] != 0) {
      current =  parents[current];
    }
    return current;
  }

  /*
    union find 해서 parent를 계속 유지
    parent가 다른 경우가 나오면 그 친구를 return
    즉, cycle이 만들어지면 리턴

    - time : O(n)
    - space : O(n)
  */
  vector<int> findRedundantConnectionUnionFindOptimal(vector<vector<int>>& edges) {
    vector<int> parents(edges.size() + 1, 0);
    for (vector<int>& edge : edges) {
      int parent1 = findParentOptimal(parents, edge[0]);
      int parent2 = findParentOptimal(parents, edge[1]);

      if (parent1 == parent2) {
        return edge;
      }

      // union
      parents[parent2] = parent1;
    }

    return { };
  }

  int findParentOptimal(vector<int>& parents, int vertex) {
    if (parents[vertex] == 0 || parents[vertex] == vertex) {
      parents[vertex] = vertex;
    } else {
      parents[vertex] = findParentOptimal(parents, parents[vertex]);
    }
    return parents[vertex];
  }

  /*
    union find 해서 parent를 계속 유지
    parent가 다른 경우가 나오면 그 친구를 return
    즉, cycle이 만들어지면 리턴

    - time : O(n)
    - space : O(n)
  */
  vector<int> findRedundantConnectionUnionFindOptimalWithSetup(vector<vector<int>>& edges) {
    vector<int> parents(edges.size() + 1, 0);

    for (int i = 1; i < parents.size(); ++i) {
      parents[i] = i;
    }

    for (vector<int>& edge : edges) {
      int parent1 = findParentOptimalWithSetup(parents, edge[0]);
      int parent2 = findParentOptimalWithSetup(parents, edge[1]);

      if (parent1 == parent2) {
        return edge;
      }

      // union
      parents[parent2] = parent1;
    }

    return { };
  }

  int findParentOptimalWithSetup(vector<int>& parents, int vertex) {
    if (parents[vertex] == vertex) {
      parents[vertex] = vertex;
    } else {
      parents[vertex] = findParentOptimalWithSetup(parents, parents[vertex]);
    }
    return parents[vertex];
  }
};

int main() {
  vector<tuple<vector<vector<int>>, vector<int>>> parameters {
    make_tuple(
        vector<vector<int>> { { 1, 2 }, { 1, 3 }, { 2, 3 }  },
        vector<int> { 2, 3 }
    ),
    make_tuple(
        vector<vector<int>> { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 }  },
        vector<int> { 1, 4 }
    )
  };

  RedundantConnection solution;
  for (auto& parameter : parameters) {
    auto& edges = get<0>(parameter);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.findRedundantConnectionUnionFind(edges);
      if (actual != expected) {
        cout << "Expected: ";
        copy(expected.begin(), expected.end(), ostream_iterator<int>(cout, ", "));
        cout << " but was: ";
        copy(actual.begin(), actual.end(), ostream_iterator<int>(cout, ", "));
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.findRedundantConnectionUnionFindOptimal(edges);
      if (actual != expected) {
        cout << "Expected: ";
        copy(expected.begin(), expected.end(), ostream_iterator<int>(cout, ", "));
        cout << " but was: ";
        copy(actual.begin(), actual.end(), ostream_iterator<int>(cout, ", "));
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.findRedundantConnectionUnionFindOptimalWithSetup(edges);
      if (actual != expected) {
        cout << "Expected: ";
        copy(expected.begin(), expected.end(), ostream_iterator<int>(cout, ", "));
        cout << " but was: ";
        copy(actual.begin(), actual.end(), ostream_iterator<int>(cout, ", "));
        cout << endl;
        assert (false);
      }
    }
  }
}
