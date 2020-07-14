/*
 * Descryption
 *
 * https://leetcode.com/problems/reverse-integer/
 *
 * Given a 32-bit signed integer, reverse digits of an integer.
 * 
 * Example 1:
 * 
 * Input: 123
 * Output: 321
 * 
 * Example 2:
 * 
 * Input: -123
 * Output: -321
 * 
 * Example 3:
 * 
 * Input: 120
 * Output: 21
 * 
 * Note:
 * Assume we are dealing with an environment which could only
 * store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1].
 * For the purpose of this problem, assume that your
 * function returns 0 when the reversed integer overflows.
 * 
 *
 * Approach & Proof 
 *
 * loop invariant
 *
 *  - ret holds processed value (eg. 3 in 123)
 *  - curr holds left (eg. 12 in 123)
 *
 *
 * Complexity
 *
 *  - Time  : O(1)
 *  - Space : O(1)
 *
 *
 * Review
 *
 * Overflow check를 '(10 * val) / 10 == val'으로 한다는게 재밌음
 *
 */
class ReverseInteger {
  public int reverse(final int x) {
    int ret = 0;
    int curr = x;
    while (curr != 0) {
      if ((10 * ret) / 10 != ret) {
        return 0;
      }
      ret = 10 * ret + curr % 10;
      curr /= 10;
    }
    return ret;
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      { 0, 0 },
      { 123, 321 },
      { -123, -321 },
      { 120, 21 },
      { 1234556678, 0 },
      { 1534236469, 0 },
    };
    final ReverseInteger solution = new ReverseInteger();
    for (final Object[] parameter : parameters) {
      final int input = (int) parameter[0];
      final int expected = (int) parameter[1];
      final int actual = solution.reverse(input);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected + ", but actual: " + actual);
      }
    }
  }
}
