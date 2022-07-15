#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/unique-paths/
 *
 * There is a robot on an m x n grid.
 * The robot is initially located at the top-left corner (i.e., grid[0][0]).
 * The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]).
 * The robot can only move either down or right at any point in time.
 * 
 * Given the two integers m and n,
 * return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * 
 * The test cases are generated so that the answer will be less than or equal to 2 * 109.
 *  
 * 
 * Example 1:
 * 
 * Input: m = 3, n = 7
 * Output: 28
 *
 *
 * Example 2:
 * 
 * Input: m = 3, n = 2
 * Output: 3
 *
 * Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Down -> Down
 * 2. Down -> Down -> Right
 * 3. Down -> Right -> Down
 *  
 * 
 * Constraints:
 * 
 * - 1 <= m, n <= 100
 *
 *
 *
 * Review
 *
 *
 */
class UniquePaths {
public:
  /*
    - overlapping subproblem
    - optimal substructure
    
    n이 100이면 최대 10000 matrix.. dp 할만함
    
    dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
    
    - time: O(m * n)
    - space: O(m * n)
  */
  int uniquePathsDpTabulation(int m, int n) {
    vector<vector<int>> dp(m + 1, vector<int>(n + 1));
    
    dp[0][1] = 1;
    for (int i = 1; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) {
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
      }
    }
    
    return dp[m][n];
  }

  /*
    - overlapping subproblem
    - optimal substructure
    
    n이 100이면 최대 10000 matrix.. dp 할만함
    
    dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
    
    single row로 dp 가능
    
    dp[i] = dp[i - 1] + dp[i]
    
    - time: O(m * n)
    - space: O(n)
  */
  int uniquePathsDpTabulationOptimized(int m, int n) {
    vector<int> dp(n);
    
    dp[0] = 1;
    for (int i = 0; i < m; ++i) {
      for (int j = 1; j < n; ++j) {
        dp[j] = dp[j - 1] + dp[j];
      }
    }
    
    return dp[n - 1];
  }

  /*
    brute force with memoization (dp)
    
    f(m, n) = f(m - 1, n) + f(m, n - 1)
            = 1             if m == 0 && n == 0
    
    - time: O(m * n)
    - space: O(m * n)
  */
  int uniquePathsDpMemoization(int m, int n) {
    vector<vector<int>> dp(m, vector<int>(n, -1));
    return uniquePaths(m - 1, n - 1, dp);
  }
  
  int uniquePaths(int i, int j, vector<vector<int>>& dp) {
    if (i == 0 && j == 0) {
      return 1;
    }
    
    if (i < 0 || j < 0) {
      return 0;
    }
    
    if (dp[i][j] == -1) {
      dp[i][j] = uniquePaths(i - 1, j, dp) + uniquePaths(i, j - 1, dp);
    }
    
    return dp[i][j];
  }

  /*
    brute force version
    
    f(m, n) = f(m - 1, n) + f(m, n - 1)
            = 1             if m == 0 && n == 0
    
    - time: O(2^(m * n))
    - space: O(m * n)
  */
  int uniquePathsBruteforce(int m, int n) {
    if (m == 1 && n == 1) {
      return 1;
    }
    
    if (m <= 0 || n <= 0) {
      return 0;
    }
    
    return uniquePathsBruteforce(m - 1, n) + uniquePathsBruteforce(m, n - 1);
  }
};

int main() {
  vector<tuple<int, int, int>> parameters {
    make_tuple(
        3,
        7,
        28
    ),
    make_tuple(
        3,
        2,
        3
    ),
    make_tuple(
        1,
        1,
        1
    )
  };

  UniquePaths solution;
  for (auto& parameter : parameters) {
    auto& m = get<0>(parameter);
    auto& n = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
      auto actual = solution.uniquePathsDpTabulation(m, n);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.uniquePathsDpTabulationOptimized(m, n);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.uniquePathsDpMemoization(m, n);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.uniquePathsBruteforce(m, n);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }
  }
}
