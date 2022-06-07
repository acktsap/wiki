#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/non-overlapping-intervals/
 *
 * Given an array of intervals intervals where intervals[i] = [start_i, end_i],
 * return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
 * 
 * 
 * Example 1:
 * 
 * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 *
 * Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
 *
 * Example 2:
 * 
 * Input: intervals = [[1,2],[1,2],[1,2]]
 * Output: 2
 *
 * Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
 *
 * Example 3:
 * 
 * Input: intervals = [[1,2],[2,3]]
 * Output: 0
 *
 * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 *  
 * 
 * Constraints:
 * 
 * - 1 <= intervals.length <= 10^5
 * - intervals[i].length == 2
 * - -5 * 10^4 <= start_i < end_i <= 5 * 10^4
 *
 *
 *
 * Review
 *
 *
 */
class NonoverlappingIntervals {
public:
  /*
    Greedy

    가장 일찍 끝나는거부터 정렬하고 가장 일찍 끝나는거부터 처리.
    가장 많이 선택하는거로 하면 남는것들이 최소로 제거해야 하는 개수.

    - time: O(n*log(n))
    - space: O(1)
  */
  int eraseOverlapIntervalsGreedy(vector<vector<int>>& intervals) {
    sort(intervals.begin(), intervals.end(), [](vector<int>& left, vector<int>& right) {
      return left[1] < right[1];
   });
    
    int maxCount = 0;
    int last = INT_MIN;
    for (vector<int>& interval : intervals) {
      if (last <= interval[0]) {
        last = interval[1];
        maxCount++;
      }
    }
    
    return intervals.size() - maxCount;
  }
};

int main() {
  vector<tuple<vector<vector<int>>, int>> parameters {
    make_tuple(
        vector<vector<int>> { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } },
        1
    ),
    make_tuple(
        vector<vector<int>> { { 1, 2 }, { 1, 2 }, { 1, 2 } },
        2
    ),
    make_tuple(
        vector<vector<int>> { { 1, 2 }, { 2, 3 } },
        0
    ),
    make_tuple(
        vector<vector<int>> { { 1, 2 }, { 2, 3 }, { 3, 4 }, { -100, -2 }, { 5, 7 } },
        0
    ),
    make_tuple(
        vector<vector<int>> {
          { -52, 31 }, { -73, -26 }, { 82, 97 }, { -65, -11 }, { -62, -49 }, { 95, 99 },
          { 58, 95 }, { -31, 49 }, { 66, 98 }, { -63, 2 }, { 30, 47 }, { -40, -26 }
        }, 
        7
    )
  };

  NonoverlappingIntervals solution;
  for (auto& parameter : parameters) {
    auto& intervals = get<0>(parameter);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.eraseOverlapIntervalsGreedy(intervals);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }
  }
}
