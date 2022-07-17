#include <iostream>
#include <vector>
#include <set>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/number-of-operations-to-make-network-connected/
 *
 * There are n computers numbered from 0 to n - 1 connected by ethernet cables connections
 * forming a network where connections[i] = [a_i, b_i] represents a connection between computers a_i and b_i.
 * Any computer can reach any other computer directly or indirectly through the network.
 * 
 * You are given an initial computer network connections.
 * You can extract certain cables between two directly connected computers,
 * and place them between any pair of disconnected computers to make them directly connected.
 * 
 * Return the minimum number of times you need to do this in order to make all the computers connected. If it is not possible, return -1.
 *  
 * 
 * Example 1:
 * 
 * Input: n = 4, connections = [[0,1],[0,2],[1,2]]
 * Output: 1
 * Explanation: Remove cable between computer 1 and 2 and place between computers 1 and 3.
 *
 * Example 2:
 * 
 * Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
 * Output: 2
 *
 * Example 3:
 * 
 * Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]
 * Output: -1
 * Explanation: There are not enough cables.
 *  
 * 
 * Constraints:
 * 
 * - 1 <= n <= 10^5
 * - 1 <= connections.length <= min(n * (n - 1) / 2, 10^5)
 * - connections[i].length == 2
 * - 0 <= a_i, b_i < n
 * - a_i != b_i
 * - There are no repeated connections.
 * - No two computers are connected by more than one cable.
 * 
 *
 * Review
 * 
 * union find를 한 결과가 무조건 아름답게 최종 조상을 가르키지 않음.
 * 아닐 수도 있어서 findParent를 해보긴 해야함.
 *
 */
class NumberofOperationstoMakeNetworkConnected {
public:
  // todo: dfs 사용

  // todo: union find & set 안쓰고 해보기

  /*
    disjoint set만드는 과정에서 cycle 발견하는 수를 리턴?
    
    n - 1개 있으면 무조건 어케든 됨 n - 1보다 작으면 -1 리턴
    
    ---
    
    음 그냥 disjoint set 만들고 다른 친구만 리턴하자..
    
    - time: O(n)
    - space: O(n)
  */
  int makeConnectedPoor(int n, vector<vector<int>>& connections) {
    if (connections.size() < n - 1) {
      return -1;
    }
    
    vector<int> parents(n, 0);
    for (int i = 0; i < parents.size(); ++i) {
      parents[i] = i;
    }
    
    for (vector<int>& connection : connections) {
      int p0 = findParent(parents, connection[0]);
      int p1 = findParent(parents, connection[1]);
      
      if (p0 < p1) {
        parents[p1] = p0;
      } else {
        parents[p0] = p1;
      }
    }
     
    set<int> disjointSet;
    for (int parent : parents) {
      int p = findParent(parents, parent);
      disjointSet.insert(p);
    }
    
    return disjointSet.size() - 1;
  }
  
  int findParent(vector<int>& parents, int vertex) {
    if (parents[vertex] != vertex) {
      parents[vertex] = findParent(parents, parents[vertex]);
    }

    return parents[vertex];
  }
};

int main() {
  vector<tuple<int, vector<vector<int>>, int>> parameters {
    make_tuple(
        4,
        vector<vector<int>> { { 0, 1 }, { 0, 2 }, { 1, 2 } },
        1
    ),
    make_tuple(
        6,
        vector<vector<int>> { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 } },
        2
    ),
    make_tuple(
        6,
        vector<vector<int>> { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 } },
        -1
    )
  };

  NumberofOperationstoMakeNetworkConnected solution;
  for (auto& parameter : parameters) {
    auto& n = get<0>(parameter);
    auto& connections = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
      auto actual = solution.makeConnectedPoor(n, connections);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }
  }
}
