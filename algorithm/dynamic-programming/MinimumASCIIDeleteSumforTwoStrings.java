import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
 *
 * Given two strings s1 and s2, return the lowest ASCII sum of deleted characters to make two strings equal.
 * 
 * 
 * Example 1:
 * 
 * Input: s1 = "sea", s2 = "eat"
 * Output: 231
 *
 * Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
 * Deleting "t" from "eat" adds 116 to the sum.
 * At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
 *
 *
 * Example 2:
 * 
 * Input: s1 = "delete", s2 = "leet"
 * Output: 403
 *
 * Explanation: Deleting "dee" from "delete" to turn the string into "let",
 * adds 100[d] + 101[e] + 101[e] to the sum.
 * Deleting "e" from "leet" adds 101[e] to the sum.
 * At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
 * If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
 *  
 * 
 * Constraints:
 * 
 * 1 <= s1.length, s2.length <= 1000
 * s1 and s2 consist of lowercase English letters.
 *
 *
 *
 * Review
 *
 * 와.. 역발상..
 *
 */
class MinimumASCIIDeleteSumforTwoStrings {

  /*
    longest -> dp
    
    dp[i][j] : max amoung text1[:i], text2[:j]
    
    dp[i][j] = dp[i - 1][j - 1] + text1[i]         if text1[i] == text2[j]
                 : 한개 추가
               max(dp[i - 1][j], dp[i][j - 1])     if text1[i] != text2[j]
                 : text1[i], text2[j] 둘중 한개 죽이고 나머지 살려
    
    solution = sum_ascii(s1) + sum_ascii(s2) - 2 * dp[i][j]
    
    - time:  O(n*m)
    - space:  O(n*m)
  */
  public int minimumDeleteSum(String s1, String s2) {
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];
    
    for (int i = 1; i <= s1.length(); ++i) {
      for (int j = 1; j <= s2.length(); ++j) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + (int) s1.charAt(i - 1);
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }
    
    int sum = 0;
    for (int i = 0; i < s1.length(); ++i) {
      sum += (int) s1.charAt(i);
    }
    for (int i = 0; i < s2.length(); ++i) {
      sum += (int) s2.charAt(i);
    }
    
    return sum - 2 * dp[s1.length()][s2.length()];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        "sea",
        "eat",
        231,
      },
      {
        "delete",
        "leet",
        403,
      },
    };

    var solution = new MinimumASCIIDeleteSumforTwoStrings();
    for (Object[] parameter : parameters) {
      var s1 = (String) parameter[0];
      var s2 = (String) parameter[1];
      var expected = (int) parameter[2];

      {
        var actual = solution.minimumDeleteSum(s1, s2);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
