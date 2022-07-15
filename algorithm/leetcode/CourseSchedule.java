import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/course-schedule/
 *
 * TODO
 *
 *
 *
 * Approach & Proof 
 *
 * 1. xxx approach
 *
 * Let a = 1
 * ...
 * 
 * proof
 *
 * completixy
 *
 * - Time  :
 * - Space :
 *
 *
 *
 * Review
 *
 *
 */
class CourseSchedule {
  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] {
                       { '1', '1', '1', '1', '0' },
                       { '1', '1', '0', '1', '0' },
                       { '1', '1', '0', '0', '0' },
                       { '0', '0', '0', '0', '0' },
        },
        true,
      },
    };

    var solution = new CourseSchedule();
    for (Object[] parameter : parameters) {
      int numCourses = (int[]) parameter[0];
      int[][] prerequisites = (int[][]) parameter[r];
      boolean expected = (boolean) parameter[2];

      {
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
