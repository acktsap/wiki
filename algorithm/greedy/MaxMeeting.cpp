#include <iostream>
#include <sstream>
#include <vector>
#include <tuple>
#include <set>
#include <map>
#include <algorithm>

using namespace std;

/*
 * https://www.acmicpc.net/problem/1931
 *
 * 회의실 배정
 *
 * 한 개의 회의실이 있는데 이를 사용하고자 하는 N개의 회의에 대하여 회의실 사용표를 만들려고 한다.
 * 각 회의 I에 대해 시작시간과 끝나는 시간이 주어져 있고, 각 회의가 겹치지 않게 하면서 회의실을 사용할 수 있는 회의의 최대 개수를 찾아보자.
 * 단, 회의는 한번 시작하면 중간에 중단될 수 없으며 한 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있다.
 * 회의의 시작시간과 끝나는 시간이 같을 수도 있다. 이 경우에는 시작하자마자 끝나는 것으로 생각하면 된다.
 *
 *
 * 입력
 *
 * 첫째 줄에 회의의 수 N(1 ≤ N ≤ 100,000)이 주어진다.
 * 둘째 줄부터 N+1 줄까지 각 회의의 정보가 주어지는데 이것은 공백을 사이에 두고 회의의 시작시간과 끝나는 시간이 주어진다.
 * 시작 시간과 끝나는 시간은 2^31-1보다 작거나 같은 자연수 또는 0이다.
 *
 * 출력
 *
 * 첫째 줄에 최대 사용할 수 있는 회의의 최대 개수를 출력한다.
 *
 *
 * 예제 입력 1
 *
 * 11
 * 1 4
 * 3 5
 * 0 6
 * 5 7
 * 3 8
 * 5 9
 * 6 10
 * 8 11
 * 8 12
 * 2 13
 * 12 14
 *
 * 예제 출력 1
 *
 * 4
 *
 * 힌트
 * (1,4), (5,7), (8,11), (12,14) 를 이용할 수 있다.
 *
 *
 *
 * Review
 *
 * 이전 개념 poc로 dp로 하긴 했는데 n^2는 확실히 별로다.
 *
 *
 */

/*
  끝나는 시간을 기준으로 dp.. 기존 concept poc

  dp[i] : i 시간에 최대한 많이 할 수 있는 회의의 수
  dp[i] = max(dp[i - 1], max(1 + dp[j])), j는 i까지 갈 수 있는 j들

  - time: O(n^2)
  - space: O(n)
*/
int resolveDp(vector<int>& startTimes, vector<int>& endTimes) {
  int maxEndTime = 0;
  map<int, set<int>> endTime2StartTimeSet;
  map<int, int> time2SelfReachingTimeCount;

  for (int i = 0; i < startTimes.size(); ++i) {
    int startTime = startTimes[i];
    int endTime = endTimes[i];

    if (startTime != endTime) {
      if (endTime2StartTimeSet.find(endTime) == endTime2StartTimeSet.end()) {
        endTime2StartTimeSet[endTime] = set<int>();
      }
      endTime2StartTimeSet[endTime].insert(startTime);
    } else {
      time2SelfReachingTimeCount[startTime]++;
    }
    maxEndTime = max(maxEndTime, endTime);
  }

  vector<int> dp(maxEndTime + 1, 0);

  for (int i = 0; i < dp.size(); ++i) {
    int candidate = 0;
    if (endTime2StartTimeSet.find(i) != endTime2StartTimeSet.end()) {
      for (int startTime : endTime2StartTimeSet[i]) {
        candidate = max(candidate, 1 + dp[startTime]);
      }
    }

    dp[i] = (i == 0 ? candidate : max(dp[i - 1], candidate));

    if (time2SelfReachingTimeCount.find(i) != time2SelfReachingTimeCount.end()) {
      dp[i] += time2SelfReachingTimeCount[i];
    }
  }

  return dp[dp.size() - 1];
}

// todo: im
int resolveGreedy(vector<int>& startTimes, vector<int>& endTimes) {
  return 0;
}

int main() {
  vector<tuple<string, int>> parameters {
    make_tuple(
        "11\n"
        "1 4\n"
        "3 5\n"
        "0 6\n"
        "5 7\n"
        "3 8\n"
        "5 9\n"
        "6 10\n"
        "8 11\n"
        "8 12\n"
        "2 13\n"
        "12 14\n",
        4
    ),
    make_tuple(
        "5\n"
        "1 1\n"
        "1 1\n"
        "1 1\n"
        "1 2\n"
        "1 3\n",
        4
    ),
    make_tuple(
        "12\n"
        "1 4\n"
        "3 5\n"
        "0 6\n"
        "5 7\n"
        "3 8\n"
        "5 9\n"
        "6 10\n"
        "8 8\n"
        "8 11\n"
        "8 12\n"
        "2 13\n"
        "12 14\n",
        5
    ),
    make_tuple(
        "3\n"
        "3 3\n"
        "3 3\n"
        "3 3\n",
        3
    )
  };

  for (auto& parameter : parameters) {
    auto& input= get<0>(parameter);
    auto& expected = get<1>(parameter);

    stringstream cin(input);

    int n;
    cin >> n;
    vector<int> startTimes(n);
    vector<int> endTimes(n);

    for (int i = 0; i < n; ++i) {
      cin >> startTimes[i] >> endTimes[i];
    }

    {
      int actual = resolveDp(startTimes, endTimes);
      cout << actual << endl;

      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }

    {
      int actual = resolveGreedy(startTimes, endTimes);
      cout << actual << endl;

      if (actual != expected) {
        cout << "Expected: " << expected;
        cout << " but was: " << actual;
        cout << endl;
        assert (false);
      }
    }
  }

  return 0;
}
