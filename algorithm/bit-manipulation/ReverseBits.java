import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/reverse-bits/
 *
 * Reverse bits of a given 32 bits unsigned integer.
 * 
 * Note:
 * 
 * Note that in some languages, such as Java, there is no unsigned integer type.
 * In this case, both input and output will be given as a signed integer type.
 * They should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
 * In Java, the compiler represents the signed integers using 2's complement notation.
 * Therefore, in Example 2 above, the input represents the signed integer -3
 * and the output represents the signed integer -1073741825.
 *  
 * 
 * Example 1:
 * 
 * Input: n = 00000010100101000001111010011100
 * Output:    964176192 (00111001011110000010100101000000)
 * Explanation:
 * The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596,
 * so return 964176192 which its binary representation is 00111001011110000010100101000000.
 * 
 * Example 2:
 * 
 * Input: n = 11111111111111111111111111111101
 * Output:   3221225471 (10111111111111111111111111111111)
 * Explanation:
 * The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293,
 * so return 3221225471 which its binary representation is 10111111111111111111111111111111.
 *  
 * 
 * Constraints:
 * 
 * The input must be a binary string of length 32
 *  
 * 
 * Follow up: If this function is called many times, how would you optimize it?
 *
 *
 * Review
 *
 *
 */
class ReverseBits {

  /*
    check 0~31 bit of n
    insert to ret

    - time: O(length of n)
    - space: O(length of n)
  */
  public int reverseBitsBitByBit(int n) {
    int ret = 1 & n;
    for (int i = 1; i < 32; ++i) {
      ret = (ret << 1);
      ret += (1 & (n >> i));
    }
    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        43261596,
        964176192,
      },
      {
        -3,
        -1073741825,
      },
    };

    var solution = new ReverseBits();
    for (Object[] parameter : parameters) {
      var input = (int) parameter[0];
      var expected = (int) parameter[1];

      {
       var actual = solution.reverseBitsBitByBit(input);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
