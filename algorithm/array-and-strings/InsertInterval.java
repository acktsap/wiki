import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

/*
 * Descryption
 *
 * https://leetcode.com/problems/insert-interval/
 *
 * You are given an array of non-overlapping intervals
 * where intervals[i] = [start_i, end_i] represent the start and the end of the i-th interval
 * and intervals is sorted in ascending order by start_i.
 *
 * You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
 * 
 * Insert newInterval into intervals such that intervals is still sorted in ascending order by start_i
 * and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).
 * 
 * Return intervals after the insertion.
 * 
 *
 * Example 1:
 * 
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 *
 * Example 2:
 * 
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 *  
 * 
 * Constraints:
 * 
 * - 0 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= start_i <= end_i <= 10^5
 * - intervals is sorted by starti in ascending order.
 * - newInterval.length == 2
 * - 0 <= start <= end <= 10^5
 *
 *
 *
 * Review
 *
 * 역시 모든 케이스를 나열해보고 모든 케이스에 대해서 제출하기 전에 넣어봐야 한다..
 * 꼭 한개 loop로 처리한다고 생각하지 말고 loop를 나눠서 한다고도 생각해보자
 *
 */
class InsertInterval {
  // TODO: insertGood 추가 (https://leetcode.com/problems/insert-interval/discuss/1791668/Java-O(n)-time-and-O(n)-space-solution) 참고


  /*
    Loop Invariant
    
    - joined : holds merged one until i
    
    if (!merged)
      if (newInterval[1] < next[0])
        add new, next
        merged = true
      else if (next[1] < newInterval[0])
        add next
      else 
        add min(newInterval[0], next[0]) : max(newInterval[1], next[1])
        merged = true
    else
      if joined.last[1] < next[0]
        add next
      else if joined.last[1] == next[0]
        joined.last[1] = next[1]
      else // joined.last[1] > next[0]
        last[1] = Math.max(last[1], next[0])
    
    - time: O(n)   
    - space: O(n)
  */
  public int[][] insertPoor(int[][] intervals, int[] newInterval) {
    List<int[]> joined = new ArrayList<>();
    
    boolean isMerged = false;
    for (int[] next : intervals) {
      if (!isMerged) {
        if (newInterval[1] < next[0]) {
          joined.add(newInterval);
          joined.add(next);
          isMerged = true;
        } else if (next[1] < newInterval[0]) {
          joined.add(next);
        } else {
          int[] merged = new int[2];
          merged[0] = Math.min(newInterval[0], next[0]);
          merged[1] = Math.max(newInterval[1], next[1]);
          joined.add(merged);
          isMerged = true;
        }
      } else {
        int[] last = joined.get(joined.size() - 1);
        if (last[1] == next[0]) {
          last[1] = next[1];
        } else if (last[1] < next[0]) {
          joined.add(next);
        } else {
          last[1] = Math.max(last[1], next[1]);
        }
      }
    }
    
    if (!isMerged) {
      joined.add(newInterval);
    }
    
    int[][] ret = new int[joined.size()][];
    for (int i = 0; i < joined.size(); ++i) {
      ret[i] = joined.get(i);
    }
    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] { {1,3}, {6,9} },
        new int[] { 2, 5 },
        new int[][] { {1,5}, {6,9} }
      },
      {
        new int[][] { {1,2}, {3,5}, {6,7}, {8,10}, {12,16} },
        new int[] { 4, 8 },
        new int[][] { {1,2}, {3,10}, {12,16} }
      },
      {
        new int[][] { {1,2}, {3,5}, {6,7}, {8,10}, {12,16} },
        new int[] { 18, 20 },
        new int[][] { {1,2}, {3,5}, {6,7}, {8,10}, {12,16}, {18,20} }
      },
      {
        new int[][] { {3,5}, {6,7} },
        new int[] { 1, 2 },
        new int[][] { {1,2}, {3,5}, {6,7} }
      },
      {
        new int[][] { {3,5}, {6,7} },
        new int[] { 1, 4 },
        new int[][] { {1,5}, {6,7} }
      },
      {
        new int[][] { {2,5}, {6,7} },
        new int[] { 3, 4 },
        new int[][] { {2,5}, {6,7} }
      },
      {
        new int[][] { {2,5}, {8,10} },
        new int[] { 3, 7 },
        new int[][] { {2,7}, {8,10} }
      },
      {
        new int[][] { {2,5}, {6,10} },
        new int[] { 3, 7 },
        new int[][] { {2,10} }
      },
    };

    var solution = new InsertInterval();
    for (Object[] parameter : parameters) {
      var intervals = (int[][]) parameter[0];
      var newInterval = (int[]) parameter[1];
      var expected = (int[][]) parameter[2];

      {
        var actual = solution.insertPoor(intervals, newInterval);

        if (!Arrays.deepEquals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Arrays.deepToString(expected) +
              ", but was: " + Arrays.deepToString(actual));
        }
      }
    }
  }
}
