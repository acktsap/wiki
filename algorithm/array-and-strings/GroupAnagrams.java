import java.math.BigInteger;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map;

/*
 * Descryption
 *
 * https://leetcode.com/problems/group-anagrams/
 *
 * Given an array of strings strs, group the anagrams together.
 *
 * You can return the answer in any order.
 * 
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 * 
 *
 * Example 1:
 * 
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * Example 2:
 * 
 * Input: strs = [""]
 * Output: [[""]]
 *
 * Example 3:
 * 
 * Input: strs = ["a"]
 * Output: [["a"]]
 *  
 * 
 * Constraints:
 * 
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters.
 *
 *
 *
 * Review
 *
 * hash를 처음에 ascii code로 하려고 했었다.. 실패..
 *
 *
 */
class GroupAnagrams {
  /*
    hash: sorted string
    
    Loop invariant

    - hash2List : holds hash(str) to corresponding list

    - n: length of strs
    - k: maximum length of string
    - time: O(n*klog(k))
    - space: O(n*k)
  */
  public List<List<String>> groupAnagramsSort(String[] strs) {
    Map<String, List<String>> hash2List = new HashMap<>();
    
    for (String str : strs) {
      String hash = hashSort(str);
      List<String> list = hash2List.get(hash);
      if (list == null) {
        List<String> newList = new ArrayList<>();
        newList.add(str);
        hash2List.put(hash, newList);
      } else {
        list.add(str);
      }
    }
    
    return new ArrayList<>(hash2List.values());
  }
  
  protected String hashSort(String str) {
    char[] sorted = str.toCharArray();
    Arrays.sort(sorted);
    return new String(sorted);
  }

  /*
    hash: count for each alpha string
    
    Loop invariant

    - hash2List : holds hash(str) to corresponding list

    - n: length of strs
    - k: maximum length of string
    - time: O(n*k)
    - space: O(n*k)
  */
  public List<List<String>> groupAnagramsCount(String[] strs) {
    Map<String, List<String>> hash2List = new HashMap<>();
    
    for (String str : strs) {
      String hash = hashCount(str);
      List<String> list = hash2List.get(hash);
      if (list == null) {
        List<String> newList = new ArrayList<>();
        newList.add(str);
        hash2List.put(hash, newList);
      } else {
        list.add(str);
      }
    }
    
    return new ArrayList<>(hash2List.values());
  }
  
  protected String hashCount(String str) {
    int[] count = new int[26];
    for (int i = 0; i < str.length(); ++i) {
      count[str.charAt(i) - 'a']++;
    }
    return Arrays.toString(count);
  }
  
  /*
    hash: 26개 알파벳을 26번째 소수까지 할당하고 그냥 곱
    
    Loop invariant

    hash2List : holds hash(str) to corresponding list

    - n: length of strs
    - k: maximum length of string
    - time: O(n*k)
    - space: O(n*k)
  */
  public List<List<String>> groupAnagramsPrimeNumber(String[] strs) {
    Map<BigInteger, List<String>> hash2List = new HashMap<>();
    
    for (String str : strs) {
      BigInteger hash = hashMultiply(str);
      List<String> list = hash2List.get(hash);
      if (list == null) {
        List<String> newList = new ArrayList<>();
        newList.add(str);
        hash2List.put(hash, newList);
      } else {
        list.add(str);
      }
    }
    
    return new ArrayList<>(hash2List.values());
  }

  protected int[] primeNumbers = new int[] {
    2,3,5,7,11,
    13,17,19,23,29,
    31,37,41,43,47,
    53,59,61,67,71,
    73,79,83,89,97,
    101
  };

  protected BigInteger hashMultiply(String str) {
    BigInteger result = BigInteger.ONE;
    for (int i = 0; i < str.length(); ++i) {
      result = result.multiply(BigInteger.valueOf(primeNumbers[str.charAt(i) - 'a']));
    }
    return result;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new String[] { "cab", "tin", "pew", "duh", "may", "ill", "buy", "bar", "max", "doc", "aduh", "aill" },
        Arrays.asList(
          Arrays.asList("aill"),
          Arrays.asList("aduh"),
          Arrays.asList("max"),
          Arrays.asList("buy"),
          Arrays.asList("doc"),
          Arrays.asList("may"),
          Arrays.asList("ill"),
          Arrays.asList("duh"),
          Arrays.asList("tin"),
          Arrays.asList("bar"),
          Arrays.asList("pew"),
          Arrays.asList("cab")
        )
      },
      {
        new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }, 
        Arrays.asList(
          Arrays.asList("bat"),
          Arrays.asList("nat", "tan"),
          Arrays.asList("ate", "eat", "tea")
        )
      },
      {
        new String[] { 
          "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
          "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab"
        },
        Arrays.asList(
          Arrays.asList("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
          Arrays.asList("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab")
        )
      },
    };

    var solution = new GroupAnagrams();
    for (Object[] parameter : parameters) {
      var strs = (String[]) parameter[0];
      var expected = (List<List<String>>) parameter[1];
      expected.forEach(list -> Collections.sort(list));
      Collections.sort(expected, (l, r) -> l.get(0).compareTo(r.get(0)));

      {
        var actual = solution.groupAnagramsSort(strs);
        actual.forEach(list -> Collections.sort(list));
        Collections.sort(actual, (l, r) -> l.get(0).compareTo(r.get(0)));

        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

      {
        var actual = solution.groupAnagramsCount(strs);
        actual.forEach(list -> Collections.sort(list));
        Collections.sort(actual, (l, r) -> l.get(0).compareTo(r.get(0)));

        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }

      {
        var actual = solution.groupAnagramsPrimeNumber(strs);
        actual.forEach(list -> Collections.sort(list));
        Collections.sort(actual, (l, r) -> l.get(0).compareTo(r.get(0)));

        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
