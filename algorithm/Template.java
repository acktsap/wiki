import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/xxx
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
class Template {
  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new char[][] {
                       { '1', '1', '1', '1', '0' },
                       { '1', '1', '0', '1', '0' },
                       { '1', '1', '0', '0', '0' },
                       { '0', '0', '0', '0', '0' },
        },
        1
      },
    };

    var solution = new Template();
    for (Object[] parameter : parameters) {
      char[][] grid = (char[][]) parameter[0];
      int expected = (int) parameter[1];

      {
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
