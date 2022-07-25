#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid);

/*
 * Descryption
 *
 * https://leetcode.com/problems/coloring-a-border/
 *
 * You are given an m x n integer matrix grid, and three integers row, col, and color.
 * Each value in the grid represents the color of the grid square at that location.
 * 
 * Two squares belong to the same connected component if they have the same color and are next to each other in any of the 4 directions.
 * 
 * The border of a connected component is all the squares in the connected component
 * that are either 4-directionally adjacent to a square not in the component, or on the boundary of the grid (the first or last row or column).
 * 
 * You should color the border of the connected component that contains the square grid[row][col] with color.
 * 
 * Return the final grid.
 *  
 * 
 * Example 1:
 * 
 * Input: grid = [[1,1],[1,2]], row = 0, col = 0, color = 3
 * Output: [[3,3],[3,2]]
 *
 * Example 2:
 * 
 * Input: grid = [[1,2,2],[2,3,2]], row = 0, col = 1, color = 3
 * Output: [[1,3,3],[2,3,3]]
 *
 * Example 3:
 * 
 * Input: grid = [[1,1,1],[1,1,1],[1,1,1]], row = 1, col = 1, color = 2
 * Output: [[2,2,2],[2,1,2],[2,2,2]]
 *  
 * 
 * Constraints:
 * 
 * - m == grid.length
 * - n == grid[i].length
 * - 1 <= m, n <= 50
 * - 1 <= grid[i][j], color <= 1000
 * - 0 <= row < m
 * - 0 <= col < n
 *
 *
 * Review
 *
 * grid 자체에 바꾼는게 영향을 끼치는걸 왜 몰랐는가..
 * 그정도는 문제 보고 알았어야 했는데..
 *
 */
class ColoringABorder {
public:
  int dx[4] = { -1, 1, 0, 0 };
  int dy[4] = { 0, 0, -1, 1 };
  
  /*
    그냥 row, col로 시작하면서 border인거 color로 칠하면 될거같은데?
    
    - time: O(n * m)
    - space: O(n * m)
  */
  vector<vector<int>> colorBorder(vector<vector<int>>& grid, int row, int col, int color) {
    vector<vector<int>> copy = vector<vector<int>>(grid);
    vector<vector<bool>> visited = vector<vector<bool>>(grid.size(), vector<bool>(grid[0].size(), false));
    dfs(row, col, grid[row][col], color, grid, copy, visited);
    return copy;
  }
  
  void dfs(int row, int col, int baseColor, int changeColor, const vector<vector<int>>& grid, vector<vector<int>>& markGrid, vector<vector<bool>>& visited) {
    bool isBorder = false;
    for (int i = 0; i < 4; ++i) {
      int x = row + dx[i];
      int y = col + dy[i];
      
      if (x < 0 || x >= grid.size()) {
        isBorder = true;
        break;
      }
      
      if (y < 0 || y >= grid[0].size()) {
        isBorder = true;
        break;
      }
        
      if (grid[x][y] != baseColor) {
        isBorder = true;
        break;
      }
    }
    
    if (isBorder) {
      markGrid[row][col] = changeColor;
    }
    visited[row][col] = true;
    
    for (int i = 0; i < 4; ++i) {
      int x = row + dx[i];
      int y = col + dy[i];
      
      if (x < 0 || x >= grid.size()) {
        continue;
      }
      
      if (y < 0 || y >= grid[0].size()) {
        continue;
      }
      
      if (visited[x][y] || grid[x][y] != baseColor) {
        continue;
      }
      
      dfs(x, y, baseColor, changeColor, grid, markGrid, visited);
    }
  }
};

int main() {
  vector<tuple<vector<vector<int>>, int, int, int, vector<vector<int>>>> parameters {
    make_tuple(
        vector<vector<int>> { 
          { 1, 2, 1, 2, 1, 2 },
          { 2, 2, 2, 2, 1, 2 },
          { 1, 2, 2, 2, 1, 2 },
        },
        1,
        3,
        1,
        vector<vector<int>> { 
          { 1, 1, 1, 1, 1, 2 },
          { 1, 2, 1, 1, 1, 2 },
          { 1, 1, 1, 1, 1, 2 }
        }
    ),
    make_tuple(
        vector<vector<int>> { 
          { 1, 1 },
          { 1, 2 }
        },
        0,
        0,
        3,
        vector<vector<int>> { 
          { 3, 3 },
          { 3, 2 }
        }
    ),
    make_tuple(
        vector<vector<int>> { 
          { 1, 2, 2 },
          { 2, 3, 2 }
        },
        0,
        1,
        3,
        vector<vector<int>> { 
          { 1, 3, 3 },
          { 2, 3, 3 }
        }
    ),
    make_tuple(
        vector<vector<int>> { 
          { 1, 1, 1 },
          { 1, 1, 1 },
          { 1, 1, 1 }
        },
        1,
        1,
        2,
        vector<vector<int>> { 
          { 2, 2, 2 },
          { 2, 1, 2 },
          { 2, 2, 2 }
        }
    )
  };

  ColoringABorder solution;
  for (auto& parameter : parameters) {
    auto& grid = get<0>(parameter);
    auto& row = get<1>(parameter);
    auto& col = get<2>(parameter);
    auto& color = get<3>(parameter);
    auto& expected = get<4>(parameter);

    {
      auto actual = solution.colorBorder(grid, row, col, color);
      if (actual != expected) {
        cout << "Expected" << endl << expected;
        cout << "Actual" << endl << actual;
        assert (false);
      }
    }
  }
}

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid){
  for(int i = 0; i < grid.size(); i++) {
    os << grid[i][0];
    for (int j = 1; j < grid[i].size(); j++) {
        os << ", " << grid[i][j];
    }
    os << endl;
  }
  os << endl;     

  return os;
}
