/*
 * Descryption
 *
 * https://leetcode.com/problems/single-number/
 *
 * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
 * 
 * Note:
 * 
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 * 
 * Example 1:
 * 
 * Input: [2,2,1]
 * Output: 1
 * 
 * Example 2:
 * 
 * Input: [4,1,2,1,2]
 * Output: 4
 *
 *
 * Approach & Proof 
 *
 * 0부터 시작해서 num에 대해서 xor한다
 * 남는 bit가 하나만 발생한 것
 *
 * Proof
 *
 * 특정 bit가 0이 한번, 1이 2번이라고 하자. 그러면 답은 0이어야 한다.
 * xor는 교환법칙을 만족한다. 1 ^ 1하면 0이다. 그러면 목적 bit에 따라
 * 0 ^ 1 또는 0 ^ 0이 된다. 0 ^ 1 == 1, 0 ^ 0 == 0이다.
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(1)
 *
 *
 * Review
 *
 * 2번 나오면 xor -> bit가 0이 된다는 것을 잘 이용했네!
 * 꼼수!!
 *
 *
 */
class SingleNumber {
  public int singleNumber(int[] nums) {
    int ret = 0;
    for (int num : nums) {
      ret = ret ^ num;
    }
    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 2, 2, 1 },
        1,
      },
      {
        new int[] { 4, 1, 2, 1, 2 },
        4,
      },
    };
    SingleNumber solution = new SingleNumber();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      int expected = (int) parameter[1];
      int actual = solution.singleNumber(input);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
