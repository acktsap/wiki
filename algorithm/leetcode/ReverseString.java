import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/reverse-string/
 *
 * Write a function that reverses a string.
 * The input string is given as an array of characters char[].
 *
 * Do not allocate extra space for another array,
 * you must do this by modifying the input array in-place with O(1) extra memory.
 *
 *
 * Approach & Proof 
 *
 * Recursively
 *
 * shrink해가면서 재귀를 사용해보자
 *
 * f(str. left, right) = swap(left, right) + f(str, left - 1, right + 1);
 * f(str. left, right) = do nothing    where right < left
 *
 *
 * Complexity
 *
 * Let n be length of str
 *
 *  - Time  : O(n / 2)
 *  - Space : log(n)
 *
 *
 * Review
 *
 * 간단하구만
 *
 */
class ReverseString {
  public void reverseString(char[] str) {
    swap(str, 0, str.length - 1);
  }

  protected void swap(char[] str, int left, int right) {
    if (left >= right) {
      return;
    }

    char tmp = str[left];
    str[left] = str[right];
    str[right] = tmp;
    swap(str, left + 1, right - 1);
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new char[] { 'h', 'e', 'l', 'l', 'o' },
        new char[] { 'o', 'l', 'l', 'e', 'h' },
      },
    };

    ReverseString solution = new ReverseString();
    for (Object[] parameter : parameters) {
      char[] input = (char[]) parameter[0];
      char[] expected = (char[]) parameter[1];
      char[] actual = Arrays.copyOf(expected, expected.length);
      solution.reverseString(input);
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
