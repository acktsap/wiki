import java.util.Arrays;
import java.util.Objects;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/*
 * Descryption
 *
 * 강의의 시작시간과 경과시간이 주어셨을때 강의를 들을 수 있는 최대한의 수
 *
 * 시작시간 : [ 1, 3, 3, 5, 7 ]
 * 경과시간 : [ 2, 1, 2, 3, 2 ]
 *
 *
 *
 * Review
 *
 *
 */
class MaxCourse {
  /*
    무슨 무슨 최대? dp?

    overlapping subproblem이 보인다.. 그리고 이전꺼랑 더하면 된다..

    dp[i] : i까지 오는 강의중 최대
    dp[i] = 
      if can reach
        candiate = i까지 올 수 있는 강의들중 max + 1
      max(candidate, dp[i - 1])

    - time: O(n^2)
    - space: O(maxReachTime + n)
  */
  public int calculate(int[] startTimes, int[] elapsedTimes) {
    int maxReachTime = 0;
    Map<Integer, Set<Integer>> canReachToStartTime = new HashMap<>();
    for (int i = 0; i < startTimes.length; ++i) {
      int reachTime = startTimes[i] + elapsedTimes[i];

      Set<Integer> reachTimeSet = canReachToStartTime.get(reachTime);
      if (reachTimeSet == null) {
        Set<Integer> set = new HashSet<>();
        set.add(startTimes[i]);
        canReachToStartTime.put(reachTime, set);
      } else {
        reachTimeSet.add(startTimes[i]);
      }

      maxReachTime = Math.max(maxReachTime, reachTime);
    }

    int[] dp = new int[maxReachTime + 1];
    for (int i = 1; i < dp.length; ++i) {
      int candidate = 0;
      Set<Integer> canReachSet = canReachToStartTime.get(i);
      if (canReachSet != null) {
        for (Integer startTime : canReachSet) {
          candidate = Math.max(dp[i], dp[startTime] + 1);
        }
      }
      dp[i] = Math.max(candidate, dp[i - 1]);
    }
    // System.out.println(canReachToStartTime);
    // System.out.println(Arrays.toString(dp));

    return dp[maxReachTime];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 3, 3, 5, 7 },
        new int[] { 2, 1, 2, 3, 2 },
        3,
      },
      {
        new int[] { 1, 3, 4, 5, 7 },
        new int[] { 2, 1, 2, 3, 2 },
        4,
      },
      {
        new int[] { 1 },
        new int[] { 3 },
        1,
      },
      {
        new int[] { 35 },
        new int[] { 7 },
        1,
      },
      {
        new int[] { 1, 3, 4, 5 },
        new int[] { 1, 4, 1, 1 },
        3,
      },
      {
        new int[] { 5, 7, 9 },
        new int[] { 10, 2, 1 },
        2,
      },
      {
        new int[] { 1, 3, 5 },
        new int[] { 10, 1, 2 },
        2,
      },
      {
        new int[] { 1, 2, 3, 4, 5, 9 },
        new int[] { 8, 1, 1, 1, 1, 1 },
        5,
      },
      {
        new int[] { 3, 4 },
        new int[] { 5, 4 },
        1,
      },
      {
        new int[] { 4, 6, 6, 8, 10, 15 },
        new int[] { 10, 2, 17, 2, 1, 2 },
        4,
      },
      {
        new int[] { 1, 3, 4, 5, 8 },
        new int[] { 7, 1, 1, 1, 1 },
        4,
      },
    };

    var solution = new MaxCourse();
    for (Object[] parameter : parameters) {
      var startTimes = (int[]) parameter[0];
      var elapsedTimes = (int[]) parameter[1];
      var expected = (int) parameter[2];

      {
        var actual = solution.calculate(startTimes, elapsedTimes);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
