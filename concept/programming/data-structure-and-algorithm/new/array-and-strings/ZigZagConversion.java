/*
 * Descryption
 *
 * https://leetcode.com/problems/zigzag-conversion
 *
 * The string "PAYPALISHIRING" is written in a zigzag pattern
 * on a given number of rows like this:
 * 
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 
 * And then read line by line: "PAHNAPLSIIGYIR"
 * 
 * Write the code that will take a string and make this conversion given a number of rows:
 * 
 * string convert(string s, int numRows);
 * 
 * 
 * Example 1:
 * 
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 * 
 * Example 2:
 * 
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * 
 * Example 3:
 * 
 * Input: s = "A", numRows = 1
 * Output: "A"
 * 
 * 
 * Constraints:
 * 
 *     1 <= s.length <= 1000
 *     s consists of English letters (lower-case and upper-case), ',' and '.'.
 *     1 <= numRows <= 1000
 * 
 *
 *
 * Approach & Proof 
 *
 * There should be some mathematical model.
 *
 * 0                  2n-2           - 2n-2 gap
 * 1             2n-3 2n-1           - (2n-2-2*1) gap, 2n-2 gap
 * 2         2n-4     2n-2           - (2n-2-2*2) gap, 2n-2 gap
 * .       
 * k    ?               ?            - (2n-2-2*k) gap, 2n-2 gap
 * .   .
 * n-1                2n-2+(n-1)     - 2n-2 gap
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(n)
 *
 *
 * Review
 *
 * 증명은 수학적 귀납법으로 할 수 있을 듯.
 *
 *
 */
class ZigZagConversion {
  public String convert(String s, int numRows) {
    if (numRows == 1) {
      return s;
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numRows; ++i) {
      int j = i;
      int gap = 2 * numRows - 2;
      while (j < s.length()) {
        sb.append(s.charAt(j));
        if (i != 0 && i != (numRows - 1) && (j + (gap - 2 * i)) < s.length()) {
          sb.append(s.charAt(j + (gap - 2 * i)));
        }
        j += gap;
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        "A",
        1,
        "A"
      },
      {
        "PAYPALISHIRING",
        3,
        "PAHNAPLSIIGYIR"
      },
      {
        "PAYPALISHIRING",
        4,
        "PINALSIGYAHRPI"
      },
    };

    var solution = new ZigZagConversion();
    for (Object[] parameter : parameters) {
      String input = (String) parameter[0];
      int numRows = (int) parameter[1];
      String expected = (String) parameter[2];

      var actual = solution.convert(input, numRows);
      if (!actual.equals(expected)) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
