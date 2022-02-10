import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/sqrtx/
 *
 * Given a non-negative integer x, compute and return the square root of x.
 * 
 * Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result is returned.
 * 
 * Note: You are not allowed to use any built-in exponent function or operator, such as pow(x, 0.5) or x ** 0.5.
 * 
 * 
 * Example 1:
 * 
 * Input: x = 4
 * Output: 2
 *
 * Example 2:
 * 
 * Input: x = 8
 * Output: 2
 * Explanation: The square root of 8 is 2.82842..., and since the decimal part is truncated, 2 is returned.
 *  
 * 
 * Constraints:
 * 
 * 0 <= x <= 2^31 - 1
 *
 *
 *
 * Approach & Proof 
 *
 * 1. brute force
 *
 * Increase value while val * val <= x 
 *
 * completixy
 *
 * - Time  : O(sqrt(x))
 * - Space : O(1)
 *
 *
 * 2. binary search
 *
 * todo
 *
 *
 * 3. Recursion
 *
 * todo
 *
 *
 * Review
 *
 * 여기서 binary search를 한다고? Recursion도?
 * 진짜 갈길이 멀구나..
 *
 *
 */
class Sqrtx {
  public int mySqrtBruteForce(int x) {
    int ret = 1;
    
    while (ret <= x / ret) { // divide to prevent overflow
      ++ret;
    }
    
    return ret - 1;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        0,
        0
      },
      {
        1,
        1
      },
      {
        3,
        1
      },
      {
        4,
        2
      },
      {
        8,
        2
      },
      {
        2147483647,
        46340
      },
    };

    var solution = new Sqrtx();
    for (Object[] parameter : parameters) {
      int x = (int) parameter[0];
      int expected = (int) parameter[1];

      {
        int actual = solution.mySqrtBruteForce(x);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
