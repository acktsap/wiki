import java.util.Arrays;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/longest-common-subsequence/
 *
 * Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.
 * 
 * A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.
 * 
 * For example, "ace" is a subsequence of "abcde".
 * A common subsequence of two strings is a subsequence that is common to both strings.
 * 
 * Example 1:
 * 
 * Input: text1 = "abcde", text2 = "ace" 
 * Output: 3  
 * Explanation: The longest common subsequence is "ace" and its length is 3.
 *
 * Example 2:
 * 
 * Input: text1 = "abc", text2 = "abc"
 * Output: 3
 * Explanation: The longest common subsequence is "abc" and its length is 3.
 *
 * Example 3:
 * 
 * Input: text1 = "abc", text2 = "def"
 * Output: 0
 * Explanation: There is no such common subsequence, so the result is 0.
 * 
 * Constraints:
 * 
 * 1 <= text1.length, text2.length <= 1000
 * text1 and text2 consist of only lowercase English characters.
 *
 *
 * Approach & Proof 
 *
 * dp[i][j] = 1 + dp[i - 1][j - 1]               --- if text1[i] == text2[j]
 *          = max(dp[i - 1][j], dp[i][j - 1])    --- if text1[i] != text2[j]
 *
 *
 * Complexity
 *
 *  - Time  : O(i * j
 *  - Space : O(i * j)
 *
 *
 * Review
 *
 * hint에 답이...
 * index 생각해서 일부로[i + 1][j + 1]의 배열을 만들었었지 예전에 그래
 *
 *
 */
class LongestCommonSubsequence {
  public int longestCommonSubsequence(String text1, String text2) {
    int[][] cache = new int[text1.length() + 1][text2.length() + 1];

    for (int i = 1; i <= text1.length(); ++i) {
      for (int j = 1; j <= text2.length(); ++j) {
        char ch1 = text1.charAt(i - 1);
        char ch2 = text2.charAt(j - 1);
        if (ch1 == ch2) {
          cache[i][j] = 1 + cache[i - 1][j - 1];
        } else {
          cache[i][j] = Math.max(cache[i - 1][j], cache[i][j - 1]);
        }
      }
    }
    
    return cache[text1.length()][text2.length()];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        "abcde",
        "ace",
        3,
      },
      {
        "abc",
        "abc",
        3,
      },
      {
        "abc",
        "def",
        0,
      },
    };

    var solution = new LongestCommonSubsequence();
    for (Object[] parameter : parameters) {
      String text1 = (String) parameter[0];
      String text2 = (String) parameter[1];
      int expected = (int) parameter[2];

      int actual = solution.longestCommonSubsequence(text1, text2);
      if (!Objects.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Objects.toString(expected) +
            ", but was: " + Objects.toString(actual));
      }
    }
  }
}
