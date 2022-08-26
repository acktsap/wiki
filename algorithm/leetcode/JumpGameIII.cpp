#include <iostream>
#include <vector>
#include <queue>
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
 * https://leetcode.com/problems/jump-game-iii/
 *
 * Given an array of non-negative integers arr, you are initially positioned at start index of the array.
 * When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.
 *
 * Notice that you can not jump outside of the array at any time.
 *
 *
 * Example 1:
 *
 * Input: arr = [4,2,3,0,3,1,2], start = 5
 * Output: true
 * Explanation:
 * All possible ways to reach at index 3 with value 0 are:
 * index 5 -> index 4 -> index 1 -> index 3
 * index 5 -> index 6 -> index 4 -> index 1 -> index 3
 *
 * Example 2:
 *
 * Input: arr = [4,2,3,0,3,1,2], start = 0
 * Output: true
 * Explanation:
 * One possible way to reach at index 3 with value 0 is:
 * index 0 -> index 4 -> index 1 -> index 3
 *
 * Example 3:
 *
 * Input: arr = [3,0,2,1,2], start = 2
 * Output: false
 * Explanation: There is no way to reach at index 1 with value 0.
 *
 *
 * Constraints:
 *
 * - 1 <= arr.length <= 5 * 104
 * - 0 <= arr[i] < arr.length
 * - 0 <= start < arr.length
 *
 *
 * Review
 *
 * dfs나 bfs를 그래프에만 국한시킬 필요가 없다
 *
 *
 */
class JumpGameIII {
public:
  /*
    dp인가? dp로도 풀 수 있을거 같긴 한데..
    dp로 하기에는 좀 오바같단 말이지
    dfs?

    start부터 시작해서 dfs로 index visit
    마지막에 0인거 visit인지 체크

    - time
      - O(n), arr size
    - space
      - O(n), visited length
      - O(n), max recursion
  */
  bool canReachDfs(vector<int>& arr, int start) {
    vector<bool> visited(arr.size(), false);
    visitDfs(arr, visited, start);

    for (int i = 0; i < arr.size(); ++i) {
      if (arr[i] == 0 && visited[i]) {
        return true;
      }
    }

    return false;
  }

  void visitDfs(vector<int>& arr, vector<bool>& visited, int i) {
    visited[i] = true;

    int left = i - arr[i];
    if (0 <= left && !visited[left]) {
      visitDfs(arr, visited, left);
    }

    int right = i + arr[i];
    if (right < arr.size() && !visited[right]) {
      visitDfs(arr, visited, right);
    }
  }

    /*
    start부터 시작해서 dfs로 index visit
    0인거 한개라도 도달하면 바로 끝

    - time
      - O(n), arr size
    - space
      - O(n), visited length
      - O(n), max recursion
  */
  bool canReachDfsShort(vector<int>& arr, int start) {
    vector<bool> visited(arr.size(), false);
    return visitDfsShort(arr, visited, start);
  }

  bool visitDfsShort(vector<int>& arr, vector<bool>& visited, int i) {
    visited[i] = true;

    if (arr[i] == 0) {
      return true;
    }

    bool found = false;
    int left = i - arr[i];
    if (0 <= left && !visited[left]) {
      found = visitDfsShort(arr, visited, left);
    }
    if (found) {
      return true;
    }

    int right = i + arr[i];
    if (right < arr.size() && !visited[right]) {
      found = visitDfsShort(arr, visited, right);
    }
    if (found) {
      return true;
    }

    return false;
  }

  /*
    start부터 시작해서 bfs로 계속 탐색
    0인거 한개라도 발견하면 끝

    - time
      - O(n), arr size
    - space
      - O(n), visited length
  */
  bool canReachBfs(vector<int>& arr, int start) {
    queue<int> q;
    vector<bool> visited(arr.size(), false);

    q.push(start);
    while (!q.empty()) {
      int i = q.front();
      q.pop();

      visited[i] = true;
      if (arr[i] == 0) {
        return true;
      }

      int left = i - arr[i];
      if (0 <= left && !visited[left]) {
        q.push(left);
      }

      int right = i + arr[i];
      if (right < arr.size() && !visited[right]) {
        q.push(right);
      }
    }

    return false;
  }
};

int main() {
  vector<tuple<vector<int>, int, bool>> parameters {
    make_tuple(
        vector<int> { 4, 2, 3, 0, 3, 1, 2 },
        5,
        true
    ),
    make_tuple(
        vector<int> { 4, 2, 3, 0, 3, 1, 2 },
        0,
        true
    ),
    make_tuple(
        vector<int> { 3, 0, 2, 1, 2 },
        2,
        false
    )
  };

  JumpGameIII solution;
  for (auto& parameter : parameters) {
    auto& arr = get<0>(parameter);
    auto& start = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
      auto actual = solution.canReachDfs(arr, start);
      if (actual != expected) {
        cout << "Expected" << endl << expected << endl;
        cout << "Actual" << endl << actual << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.canReachDfsShort(arr, start);
      if (actual != expected) {
        cout << "Expected" << endl << expected << endl;
        cout << "Actual" << endl << actual << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.canReachBfs(arr, start);
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
  os << endl;

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
  os << endl;

  return os;
}
