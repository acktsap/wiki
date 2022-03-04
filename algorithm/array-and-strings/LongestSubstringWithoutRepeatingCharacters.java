import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Given a string s, find the length of the longest substring without repeating characters.
 * 
 * Example 1:
 * 
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * 
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * 
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * 
 * Constraints:
 * 
 * 0 <= s.length <= 5 * 10^4
 * s consists of English letters, digits, symbols and spaces.
 *
 *
 *
 * Approach
 *
 * 1. Two pointer sliding window approach (poor)
 *
 * Let i is start of current window and j is end of current window
 *
 * i, j = 0
 * while j < s.length - 1:
 *   ++i if next duplicates entry && i < j
 *   ++i, ++j if next duplicates entry && i == j
 *   ++j if next does not duplicate entry
 *
 * proof
 *
 * - sliding window technique
 *
 * complexity
 *
 * - Time  : O(2n) ~ O(n)
 * - Space : O(n) (set usage)
 *
 *
 * 2. Two pointer sliding window approach (good)
 *
 * Let i be start of current window and j points to next element.
 * Then (j - 1) ~ i is current window
 *
 * i, j = 0
 * while j < s.length:
 *   if s[j] not in set:
 *     add to set
 *     ++j
 *     renew result
 *   else:
 *     remove from set
 *     ++i
 *
 * proof
 *
 * - sliding window technique
 *
 * Complexity
 *
 * - Time  : O(2n) ~ O(n)
 * - Space : O(n) (set usage)
 *
 *
 *
 * Review
 *
 * 조건이 복잡할때는 있는 정보를 가지고 어떻게 단순화 할 수 있을까 하고 생각해보자
 * j를 다음 element로 하면 다 해결되는 문제였다..
 * sliding window가 원원래 [i, j) 조건이네 <- 맞음??
 *
 */
class LongestSubstringWithoutRepeatingCharacters {
  public int lengthOfLongestSubstringPoor(String s) {
    if (s.length() == 0) {
      return 0;
    }

    if (s.length() == 1) {
      return 1;
    }

    int ret = 0;

    Set<Character> set = new HashSet<>();
    int i = 0;
    int j = 0;
    set.add(s.charAt(j));
    while (j < s.length() - 1) {
      char next = s.charAt(j + 1);
      if (set.contains(next)) {
        if (i == j) {
          set.remove(s.charAt(i));
          set.add(next);
          ++i;
          ++j;
        } else { // i < j
          set.remove(s.charAt(i));
          ++i;
        }
      } else {
        set.add(next);
        ++j;
      }

      ret = Math.max(ret, j - i + 1);
    }

    return ret;
  }

  public int lengthOfLongestSubstringGood(String s) {
    int ret = 0;

    int[] set = new int[256];
    int i = 0;
    int j = 0;
    while (j < s.length()) {
      if (set[s.charAt(j)] == 0) {
        ret = Math.max(ret, j - i + 1);
        ++set[s.charAt(j)];
        ++j;
      } else {
        --set[s.charAt(i)];
        ++i;
      }
    }

    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        "abcabcddddsfabsfgwafad",
        6
      },
      {
        "abcabcbb",
        3
      },
      {
        "bbbbb",
        1
      },
      {
        "pwwkew",
        3
      },
      {
        "",
        0
      },
      {
        "a",
        1
      },
      {
        "aa",
        1
      },
      {
        "ab",
        2
      },
    };

    var solution = new LongestSubstringWithoutRepeatingCharacters();
    for (Object[] parameter : parameters) {
      String input = (String) parameter[0];
      int expected = (int) parameter[1];

      {
        int actual = solution.lengthOfLongestSubstringPoor(input);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

      {
        int actual = solution.lengthOfLongestSubstringGood(input);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
