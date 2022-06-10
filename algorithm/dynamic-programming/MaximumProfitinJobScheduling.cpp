#include <iostream>
#include <vector>
#include <set>
#include <unordered_map>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
 *
 * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of profit[i].
 * 
 * You're given the startTime, endTime and profit arrays,
 * return the maximum profit you can take such that there are no two jobs in the subset with overlapping time range.
 * 
 * If you choose a job that ends at time X you will be able to start another job that starts at time X.
 *  
 * 
 * Example 1:
 * 
 * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
 * Output: 120
 * Explanation: The subset chosen is the first and fourth job. 
 * Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
 *
 *
 * Example 2:
 * 
 * Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
 * Output: 150
 * Explanation: The subset chosen is the first, fourth and fifth job. 
 * Profit obtained 150 = 20 + 70 + 60.
 *
 *
 * Example 3:
 * 
 * Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
 * Output: 6
 *  
 * 
 * Constraints:
 * 
 * - 1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
 * - 1 <= startTime[i] < endTime[i] <= 10^9
 * - 1 <= profit[i] <= 10^4
 *
 *
 * Review
 *
 *
 */
class MaximumProfitinJobScheduling {
public:
  /*
    자.. 뭐지?? greedy의 변형인가? 그냥 일찍 끝나는거 부터 하면 노답인데..
    이거야말로 dp로 하는건가?
    
    dp[i] : i까지 최대값, i는 endTime
    
    dp[i] = 
      max(dp[j] + profits[j]), j는 i에 도달할 수 있는 startTime
      dp[i - 1] (j가 없을 경우)
      
    - time: O(maxEndTime^2)
    - space: O(maxEndTime)
  */
  int jobSchedulingMemoryLimit(vector<int>& startTimes, vector<int>& endTimes, vector<int>& profits) {
    unordered_map<int, set<int>> endTime2StartTimeIndexes;
    
    int maxEndTime = 0;
    for (int i = 0; i < startTimes.size(); ++i) {
      int startTime = startTimes[i];
      int endTime = endTimes[i];
      if (endTime2StartTimeIndexes.find(endTime) == endTime2StartTimeIndexes.end()) {
        endTime2StartTimeIndexes[endTime] = set<int>();
      }
      endTime2StartTimeIndexes[endTime].insert(i);
      maxEndTime = max(maxEndTime, endTime);
    }
    
    vector<int> dp(maxEndTime + 1);
    for (int i = 1; i < dp.size(); ++i) {
      int candidate = dp[i - 1];
      if (endTime2StartTimeIndexes.find(i) != endTime2StartTimeIndexes.end()) {
        for (int j : endTime2StartTimeIndexes[i]) {
          int startTime = startTimes[j];
          int profit = profits[j];
          candidate = max(candidate, dp[startTime] + profit);
        }
      }
      dp[i] = candidate;
    }
      
    return dp[dp.size() - 1];
  }

  /*
    memory limit하고 동일한데 stack에 쌓아서 memory limit이 나는 듯 해서 heap에 생성해봄.
    leak은 있는 코드긴함..
  */
  int jobSchedulingTimeLimit(vector<int>& startTimes, vector<int>& endTimes, vector<int>& profits) {
    unordered_map<int, set<int>> endTime2StartTimeIndexes;
    
    int maxEndTime = 0;
    for (int i = 0; i < startTimes.size(); ++i) {
      int startTime = startTimes[i];
      int endTime = endTimes[i];
      if (endTime2StartTimeIndexes.find(endTime) == endTime2StartTimeIndexes.end()) {
        endTime2StartTimeIndexes[endTime] = set<int>();
      }
      endTime2StartTimeIndexes[endTime].insert(i);
      maxEndTime = max(maxEndTime, endTime);
    }
    
    vector<int> *dp = new vector<int>(maxEndTime + 1);
    for (int i = 1; i < (*dp).size(); ++i) {
      int candidate = (*dp)[i - 1];
      if (endTime2StartTimeIndexes.find(i) != endTime2StartTimeIndexes.end()) {
        for (int j : endTime2StartTimeIndexes[i]) {
          int startTime = startTimes[j];
          int profit = profits[j];
          candidate = max(candidate, (*dp)[startTime] + profit);
        }
      }
      (*dp)[i] = candidate;
    }
      
    return (*dp)[(*dp).size() - 1];
  }
};

int main() {
  vector<tuple<vector<int>, vector<int>, vector<int>, int>> parameters {
    make_tuple(
        vector<int> { 1, 2, 3, 3 },
        vector<int> { 3, 4, 5, 6 },
        vector<int> { 50, 10, 40, 70 },
        120
    ),
    make_tuple(
        vector<int> { 1, 2, 3, 4, 6 },
        vector<int> { 3, 5, 10, 6, 9 },
        vector<int> { 20, 20, 100, 70, 60 },
        150
    ),
    make_tuple(
        vector<int> { 1, 1, 1 },
        vector<int> { 2, 3, 4 },
        vector<int> { 5, 6, 4 },
        6
    ),
    make_tuple(
        vector<int> { 1, 2, 3 },
        vector<int> { 10, 10, 10 },
        vector<int> { 5, 6, 4 },
        6
    )
  };

  MaximumProfitinJobScheduling solution;
  for (auto& parameter : parameters) {
    auto& startTimes = get<0>(parameter);
    auto& endTimes = get<1>(parameter);
    auto& profits = get<2>(parameter);
    auto& expected = get<3>(parameter);

    {
      auto actual = solution.jobSchedulingMemoryLimit(startTimes, endTimes, profits);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.jobSchedulingTimeLimit(startTimes, endTimes, profits);
      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }
  }
}
