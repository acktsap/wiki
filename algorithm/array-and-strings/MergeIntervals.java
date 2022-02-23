import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/*
 * Descryption
 *
 * https://leetcode.com/problems/merge-intervals/
 *
 * Given an array of intervals where intervals[i] = [start_i, end_i],
 * merge all overlapping intervals, and return an array of the non-overlapping intervals
 * that cover all the intervals in the input.
 * 
 * 
 * Example 1:
 * 
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 *
 * Example 2:
 * 
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *  
 * 
 * Constraints:
 * 
 * - 1 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= start_i <= end_i <= 10^4
 *
 *
 *
 * Review
 *
 * sorting도 그리 나쁜 선택은 아니다
 *
 *
 */
class MergeIntervals {
  /*
    Sort by interval[0]
    Loop:
      merge into [last[0], Max(last[1], next[1])] if last[1] >= next[0]
      add next else
      
    - time: O(n*log(n))
    - space: O(n)
  */
  public int[][] merge(int[][] intervals) {
    int[][] sorted = new int[intervals.length][2];
    for (int i = 0; i < intervals.length; ++i) {
      sorted[i] = Arrays.copyOf(intervals[i], intervals[i].length);
    }
    Arrays.sort(sorted, (l, r) -> Integer.compare(l[0], r[0]));
    
    List<int[]> merged = new ArrayList<>();
    merged.add(sorted[0]);
    for (int i = 1; i < sorted.length; ++i) {
      int[] last = merged.get(merged.size() - 1);
      if (last[1] >= sorted[i][0]) {
        last[1] = Math.max(last[1], sorted[i][1]);
      } else {
        merged.add(sorted[i]);
      }
    }

    return merged.toArray(new int[merged.size()][2]);
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] { {1,5}, {3,6}, {8,10}, {15,18}, {12,14} },
        new int[][] { {1,6}, {8,10}, {12,14}, {15,18} },
      },
      {
        new int[][] { {1,10}, {3,6}, {8,10}, {15,18}, {12,14} },
        new int[][] { {1,10}, {12,14}, {15,18} },
      },
      {
        new int[][] { {1,11}, {3,6}, {8,10}, {15,18}, {12,14} },
        new int[][] { {1,11}, {12,14}, {15,18} },
      },
      {
        new int[][] { {1,13}, {3,6}, {8,10}, {15,18}, {12,14} },
        new int[][] { {1,14}, {15,18} },
      },
    };

    var solution = new MergeIntervals();
    for (Object[] parameter : parameters) {
      var intervals = (int[][]) parameter[0];
      var expected = (int[][]) parameter[1];

      {
        var actual = solution.merge(intervals);
        if (!Arrays.deepEquals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Arrays.deepToString(expected) +
              ", but was: " + Arrays.deepToString(actual));
        }
      }
    }
  }
}
