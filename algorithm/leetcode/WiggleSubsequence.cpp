#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/wiggle-subsequence/
 *
 *
 *
 * Review
 *
 *
 */
class WiggleSubsequence {
public:
};

int main() {
  vector<tuple<int, vector<vector<int>>, vector<int>>> parameters {
    make_tuple(
        2,
        vector<vector<int>> { { 1, 0 } },
        vector<int> { 0, 1 }
    ),
    make_tuple(
        4,
        vector<vector<int>> { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } },
        vector<int> { 0, 2, 1, 3 }
    ),
    make_tuple(
        1,
        vector<vector<int>> { },
        vector<int> { 0 }
    ),
    make_tuple(
        2,
        vector<vector<int>> { { 1, 0 }, { 0, 1 } },
        vector<int> { }
    )
  };

  WiggleSubsequence solution;
  for (auto& parameter : parameters) {
    auto& numCourses = get<0>(parameter);
    auto& prerequisites = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
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
