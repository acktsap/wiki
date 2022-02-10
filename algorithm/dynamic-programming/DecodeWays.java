import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/decode-ways/
 *
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 * 
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 *
 * To decode an encoded message,
 * all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways).
 * For example, "11106" can be mapped into:
 * 
 * - "AAJF" with the grouping (1 1 10 6)
 * - "KJF" with the grouping (11 10 6)
 *
 * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
 * 
 * Given a string s containing only digits, return the number of ways to decode it.
 * 
 * The test cases are generated so that the answer fits in a 32-bit integer.
 * 
 * 
 * Example 1:
 * 
 * Input: s = "12"
 * Output: 2
 * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
 *
 * Example 2:
 * 
 * Input: s = "226"
 * Output: 3
 * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 * Example 3:
 * 
 * Input: s = "06"
 * Output: 0
 * Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").
 *  
 * 
 * Constraints:
 * 
 * 1 <= s.length <= 100
 * s contains only digits and may contain leading zero(s).
 *
 *
 *
 * Review
 *
 * none
 *
 */
class DecodeWays {
  /*
    dp tabulation
    
    dp[i] : # possible ways to decode until i
    
    dp[i] = if (dp[i - 2] != 0 && s[i-2:i-1] matches alpha) dp[i - 2]) else 0 +
            if (dp[i - 1] != 0 && s[i-1:i-1] matches alpha) dp[i - 1]) else 0

    - time: O(s.length)
    - space: O(s.length)
  */
  public int numDecodingsTabulation(String s) {
    int[] dp = new int[s.length() + 1];
    dp[0] = 1; // trick
    dp[1] = canDecode(s, 0, 0) ? 1 : 0;
    
    for (int i = 2; i <= s.length(); ++i) {
      int next = 0;
      if (canDecode(s, i - 2, i - 1)) {
        next += dp[i - 2];
      }
      if (canDecode(s, i - 1, i - 1)) {
        next += dp[i - 1];
      }
      dp[i] = next;
    }
    
    return dp[s.length()];
  }
  
  /*
    dp tabulation with constant space (keep beforeTwo, beforeOne)
    
    dp[i] : # possible ways to decode until i
    
    dp[i] = if (dp[i - 2] != 0 && s[i-2:i-1] matches alpha) dp[i - 2]) else 0 +
            if (dp[i - 1] != 0 && s[i-1:i-1] matches alpha) dp[i - 1]) else 0

    - time: O(s.length)
    - space: O(1)
  */
  public int numDecodingsTabulationConstantSpace(String s) {
    int[] dp = new int[s.length() + 1];
    dp[0] = 1; // trick
    dp[1] = canDecode(s, 0, 0) ? 1 : 0;
    
    for (int i = 2; i <= s.length(); ++i) {
      int next = 0;
      if (canDecode(s, i - 2, i - 1)) {
        next += dp[i - 2];
      }
      if (canDecode(s, i - 1, i - 1)) {
        next += dp[i - 1];
      }
      dp[i] = next;
    }
    
    return dp[s.length()];
  }

  protected boolean canDecode(String s, int from, int to) {
    if (from == to) {
      char encoded = s.charAt(from);
      return encoded != '0'; // consider only digits (0~9)
    } else {
      char first = s.charAt(from);
      char second = s.charAt(to);
      
      if (first == '0') {
        return false;
      } else if (first == '1') {
        return true;
      } else if (first == '2') {
        return (int)(second - '0') <= 6;
      } else {
        return false;
      }
    }
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        "12",
        2
      },
      {
        "226",
        3
      },
      {
        "1",
        1
      },
      {
        "0",
        0
      },
      {
        "06",
        0
      },
      {
        "226710290",
        0
      },
      {
        "22671029",
        3
      },
    };

    var solution = new DecodeWays();
    for (Object[] parameter : parameters) {
      String s = (String) parameter[0];
      int expected = (int) parameter[1];

      {
        int actual = solution.numDecodingsTabulation(s);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
