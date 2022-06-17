#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/number-of-enclaves/
 *
 * You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
 * 
 * A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.
 * 
 * Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.
 * 
 * 
 * Example 1:
 * 
 * Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
 * Output: 3
 * Explanation: There are three 1s that are enclosed by 0s, and one 1 that is not enclosed because its on the boundary.
 *
 *
 * Example 2:
 * 
 * Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
 * Output: 0
 * Explanation: All 1s are either on the boundary or can reach the boundary.
 *  
 * 
 * Constraints:
 * 
 * - m == grid.length
 * - n == grid[i].length
 * - 1 <= m, n <= 500
 * - grid[i][j] is either 0 or 1.
 *
 *
 *
 * Review
 *
 *
 */
class NumberofEnclaves {
public:
  vector<int> dx = { 1, -1, 0, 0 };
  vector<int> dy = { 0, 0, -1, 1 };
    
  /*
    canReach[i][j] 한개 만들고 boundary부터 dfs
    미지막에 !canReach[i][j] && grid[i][j] == 1인거 count
    
    - time: O(m * n)
    - space: O(m * n)
  */
  int numEnclaves(vector<vector<int>>& grid) {
    int m = grid.size();
    int n = grid[0].size();
    vector<vector<bool>> canReachMap(m, vector<bool>(n, false));
    
    for (int i = 0; i < m; ++i) {
      if (grid[i][0] == 1) {
        dfs(i, 0, grid, canReachMap);
      }
      if (grid[i][n - 1] == 1) {
        dfs(i, n - 1, grid, canReachMap);
      }
    }
    
    for (int j = 1; j < n - 1; ++j) {
      if (grid[0][j] == 1) {
        dfs(0, j, grid, canReachMap);
      }
      if (grid[m - 1][j] == 1) {
        dfs(m - 1, j, grid, canReachMap);
      }
    }
    
    int count = 0;
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (!canReachMap[i][j] && grid[i][j] == 1) {
          ++count;
        }
      }
    }
    
    return count;
  }
  
  void dfs(int i, int j, vector<vector<int>>& grid, vector<vector<bool>>& canReachMap) {
    int m = grid.size();
    int n = grid[0].size();
    
    canReachMap[i][j] = true;
    
    for (int k = 0; k < 4; ++k) {
      int x = i + dx[k];
      int y = j + dy[k];
      
      if (x < 0 || x >= m || y < 0 || y >= n) {
        continue;
      }
      
      if (grid[x][y] == 0 || canReachMap[x][y]) {
        continue;
      }
      
      dfs(x, y, grid, canReachMap);
    }
  }
};

int main() {
  vector<tuple<vector<vector<int>>, int>> parameters {
    make_tuple(
        vector<vector<int>> { { 0, 0, 0, 0 }, { 1, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
        3
    ),
    make_tuple(
        vector<vector<int>> { { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } },
        0
    ),
    make_tuple(
        vector<vector<int>> { { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
        1
    ),
    make_tuple(
        vector<vector<int>> { { 0, 0, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } },
        2
    ),
    make_tuple(
        vector<vector<int>> { { 1 } },
        0
    )
  };

  NumberofEnclaves solution;
  for (auto& parameter : parameters) {
    auto& grid = get<0>(parameter);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.numEnclaves(grid);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }
  }
}
