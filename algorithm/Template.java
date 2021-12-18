import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/xxx
 *
 *
 * Approach & Proof 
 *
 *
 * Complexity
 *
 *  - Time  :
 *  - Space :
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

      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
