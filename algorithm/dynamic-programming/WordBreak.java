import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/word-break/
 *
 * Given a string s and a dictionary of strings wordDict,
 * return true if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 *  
 *
 * Example 1:
 * 
 * Input: s = "leetcode", wordDict = ["leet","code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 * 
 * Input: s = "applepenapple", wordDict = ["apple","pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 * Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * 
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: false
 *  
 *
 * Constraints:
 * 
 * 1 <= s.length <= 300
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 20
 * s and wordDict[i] consist of only lowercase English letters.
 * All the strings of wordDict are unique.
 *
 *
 *
 * Approach & Proof 
 *
 * 0. Common
 *
 * f(0..n) = (match(word1) && f(m..n)) || (match(word2) && f(m..n)) || ...
 *
 * 1. Brute force
 *
 * Recursion and backtracking.
 *
 * completixy
 *
 * - Time  : O(2^n)???? 
 * - Space : O(n) - recursion depth
 *
 * 2. dp
 *
 * Tabulation
 *
 * dp[n] : any combination is possible until n
 *
 * dp[n] = (dp[n - word1.length] && match(word1)) || (dp[n - word.length] && match(word2)) || ...
 *
 * - Time  : O(n * n * wordDict.length) - match + dp loop + dict loop
 * - Space : O(n) - array length
 *
 *
 * Review
 *
 * dp[0]에 값을 set하면 편해지는 케이스가 많다 확실히
 * dp랑memoization이 엄밀하게는 다르구나..
 *
 *
 */
class WordBreak {
  public boolean wordBreakDp(String s, List<String> wordDict) {
    int[] dp = new int[s.length() + 1];
    Arrays.fill(dp, -1);
    dp[0] = 1;
    
    for (int i = 1; i <= s.length(); ++i) {
      int result = 0;
      for (String word : wordDict) {
        if ((i - word.length()) < 0) {
          continue;
        }
        
        if (dp[i - word.length()] == 1 && match(s, i - word.length(), word)) {
          result = 1;
          break;
        }
      }
      
      dp[i] = result;
    }
    
    return dp[s.length()] == 1;
  }

  public boolean wordBreakBruteForce(String s, List<String> wordDict) {
    return wordBreakBruteForce(s, 0, wordDict);
  }

  protected boolean wordBreakBruteForce(String s, int start, List<String> wordDict) {
    if (start >= s.length()) {
      return true;
    }
    
    for (String word : wordDict) {
      if (match(s, start, word) && wordBreakBruteForce(s, start + word.length(), wordDict)) {
        return true;
      }
    }
    
    return false;
  }
  
  protected boolean match(String target, int base, String word) {
    int i = 0;
    
    while ((i + base) < target.length() && i < word.length()) {
      if (target.charAt(base + i) != word.charAt(i)) {
        return false;
      }
      ++i;
    }
   
    return i == word.length();
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        "leetcode",
        Arrays.asList("leet", "code"),
        true,
      },
      {
        "applepenapple",
        Arrays.asList("apple", "pen"),
        true,
      },
      {
        "applepenapplea",
        Arrays.asList("apple", "pen"),
        false,
      },
      {
        "catsandog",
        Arrays.asList("cats", "dog", "sand", "and", "cat"),
        false,
      },
    };

    var solution = new WordBreak();
    for (Object[] parameter : parameters) {
      String s = (String) parameter[0];
      List<String> wordDict =  (List<String>) parameter[1];
      boolean expected = (boolean) parameter[2];

      {
        boolean actual = solution.wordBreakDp(s, wordDict);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

      {
        boolean actual = solution.wordBreakBruteForce(s, wordDict);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
