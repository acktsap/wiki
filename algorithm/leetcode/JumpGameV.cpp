#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements);

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid);

/*
 * Descryption
 *
 * https://leetcode.com/problems/jump-game-v/
 *
 * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
 * 
 * - i + x where: i + x < arr.length and 0 < x <= d.
 * - i - x where: i - x >= 0 and 0 < x <= d.
 *
 * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for all indices k
 * between i and j (More formally min(i, j) < k < max(i, j)).
 * 
 * You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
 * 
 * Notice that you can not jump outside of the array at any time.
 *  
 * 
 * Example 1:
 * 
 * Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
 * Output: 4
 *
 * Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
 * Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 because 13 > 9. You cannot jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
 * Similarly You cannot jump from index 3 to index 2 or index 1.
 *
 * Example 2:
 * 
 * Input: arr = [3,3,3,3,3], d = 3
 * Output: 1
 * Explanation: You can start at any index. You always cannot jump to any index.
 *
 * Example 3:
 * 
 * Input: arr = [7,6,5,4,3,2,1], d = 1
 * Output: 7
 * Explanation: Start at index 0. You can visit all the indicies. 
 *  
 *
 * Constraints:
 * 
 * - 1 <= arr.length <= 1000
 * - 1 <= arr[i] <= 10^5
 * - 1 <= d <= arr.length
 *
 *
 *
 * Review
 *
 *
 */
class JumpGameV {
public:
    /*
    a -> b, a -> c로 가면 dp[a] = 1 + max(dp[b], dp[c])임
    overlapping subproblem & optimal substructure -> dp
    
    dp[i] : i에서 시작해서 갈 수 있는 최대의 수
    
    근데 이거 관계가 복잡해서 tabulation으로는 쉽지 않을듯 memoization으로만 하자
    cycle이 있을 수 있을까 했는데 생각해보니 높이차가 있어야만 움직일 수 있어서 cycle은 안생길듯
    
    - time: O(n^2), d가 클경우를 생각해서 대충 잡음.
    - space: O(n), store dp table
  */
  int maxJumps(vector<int>& arr, int d) {
    vector<int> dp(arr.size(), -1);
    
    int maxJump = 0;
    for (int i = 0; i < dp.size(); ++i) {
      maxJump = max(maxJump, visit(i, dp, arr, d));
    }
    return maxJump;
  }
  
  int visit(int i, vector<int>& dp, vector<int>& arr, int d) {
    if(dp[i] != -1) {
      return dp[i];
    }
    
    int jump = 0;
    for (int j = i - 1; j >= max(i - d, 0); --j) {
      if (arr[i] <= arr[j]) {
        break;
      }
      jump = max(jump, visit(j, dp, arr, d));
    }
    int lastIndex = arr.size() - 1;
    for (int j = i + 1; j <= min(lastIndex, i + d); ++j) {
      if (arr[i] <= arr[j]) {
        break;
      }
      jump = max(jump, visit(j, dp, arr, d));
    }
    
    dp[i] = 1 + jump;
    
    return dp[i];
  }
};

int main() {
  vector<tuple<vector<int>, int, int>> parameters {
    make_tuple(
        vector<int> { 6, 4, 14, 6, 8, 13, 9, 7, 10, 6, 12 },
        2,
        4
    ),
    make_tuple(
        vector<int> { 3, 3, 3, 3, 3 },
        3,
        1
    ),
    make_tuple(
        vector<int> { 7, 6, 5, 4, 3, 2, 1 },
        1,
        7
    )
  };

  JumpGameV solution;
  for (auto& parameter : parameters) {
    auto& arr = get<0>(parameter);
    auto& d = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
      auto actual = solution.maxJumps(arr, d);
      if (actual != expected) {
        cout << "Expected" << endl << expected << endl;
        cout << "Actual" << endl << actual << endl;
        assert (false);
      }
    }
  }
}

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements){
  if (elements.size() == 0) {
    return os;
  }

  os << elements[0];
  for (int i = 1; i < elements.size(); i++) {
      os << ", " << elements[i];
  }

  return os;
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

  return os;
}
