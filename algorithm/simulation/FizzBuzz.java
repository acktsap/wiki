import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;


/*
 * Descryption
 *
 * https://leetcode.com/problems/fizz-buzz/
 *
 * Given an integer n, return a string array answer (1-indexed) where:
 * 
 * answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
 * answer[i] == "Fizz" if i is divisible by 3.
 * answer[i] == "Buzz" if i is divisible by 5.
 * answer[i] == i (as a string) if none of the above conditions are true.
 *  
 * 
 * Example 1:
 * 
 * Input: n = 3
 * Output: ["1","2","Fizz"]
 * Example 2:
 * 
 * Input: n = 5
 * Output: ["1","2","Fizz","4","Buzz"]
 * Example 3:
 * 
 * Input: n = 15
 * Output: ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]
 *  
 * 
 * Constraints:
 * 
 * 1 <= n <= 10^4
 *
 *
 * Review
 *
 * 그냥 구현
 *
 */
class FizzBuzz {
  /*
    Just simulate it.
    
    - time : O(n)
    - space: O(n)
  */
  public List<String> fizzBuzz(int n) {
    List<String> ret = new ArrayList<>(n);
    
    for (int i = 0; i < n; ++i) {
      int next = i + 1;
      if (next % 3 == 0 && next % 5 == 0) {
        ret.add("FizzBuzz");
      } else if (next % 3 == 0) {
        ret.add("Fizz");
      } else if (next % 5 == 0) {
        ret.add("Buzz");
      } else {
        ret.add(Integer.toString(next));
      }
    }
    
    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        3,
        new String[] { "1", "2", "Fizz" },
      },
      {
        5,
        new String[] { "1", "2", "Fizz", "4", "Buzz" }
      },
      {
        15,
        new String[] { "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz" }
      },
    };

    var solution = new FizzBuzz();
    for (Object[] parameter : parameters) {
      int n = (int) parameter[0];
      List<String> expected = Arrays.asList((String[]) parameter[1]);

      {
        var actual = solution.fizzBuzz(n);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
