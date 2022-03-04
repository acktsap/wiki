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
 * Constraints:
 *
 * - 1 <= nums.length <= 3 * 104
 * - -3 * 104 <= nums[i] <= 3 * 104
 * - Each element in the array appears twice except for one element which appears only once.
 *
 *
 * Review
 *
 * 2번 나오면 xor -> bit가 0이 된다는 것을 잘 이용했네!
 * 꼼수!!
 *
 */
class SingleNumber {
  /*
     0부터 시작해서 num에 대해서 xor한다
     남는 bit가 하나만 발생한 것

    - time: O(n)
    - space : O(1)
  */
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

    var solution = new SingleNumber();
    for (Object[] parameter : parameters) {
      var input = (int[]) parameter[0];
      var expected = (int) parameter[1];

      {
        var actual = solution.singleNumber(input);
        if (expected != actual) {
          throw new IllegalStateException("Expected: " + expected +
              ", but was: " + actual);
        }
      }
    }
  }
}
