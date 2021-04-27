import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * 
 * 
 * Given a sorted array nums, remove the duplicates in-place
 * such that each element appear only once and return the new length.
 * 
 * Do not allocate extra space for another array,
 * you must do this by modifying the input array in-place with O(1) extra memory.
 * 
 * Example 1:
 * 
 * Given nums = [1,1,2],
 * 
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 * 
 * It doesn't matter what you leave beyond the returned length.
 *
 * Example 2:
 * 
 * Given nums = [0,0,1,1,1,2,2,3,3,4],
 * 
 * Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
 * 
 * It doesn't matter what values are set beyond the returned length.
 * 
 *
 * Approach & Proof 
 *
 * Two pointer approach
 *
 * Loop invariant
 *
 *  - < i : sorted
 *  - i : holds next position to insert
 *  - j : holds next position to compare
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
 * 인자 체크하자... 빈거랑 사이즈 작은거.. 감 잃었냐
 *
 * 그리고 제출 하기 전에 테스트 케이스 최대한 넣어보자
 *
 * Arrays에서는 ArrayIndexOutOfBound 조심!!!!
 *
 *
 */
class RemoveDuplicateFromSortedArray {
  public int removeDuplicates(int[] nums) {
    if (null == nums || nums.length == 0) {
      return 0;
    }

    int i = 1;
    while (i < nums.length && nums[i - 1] != nums[i]) { // find first pointer
      ++i;
    }
    int j = i + 1;
    while (j < nums.length) {
      if (nums[i - 1] == nums[j]) {
        ++j;
      } else {
        nums[i] = nums[j];
        ++i;
        ++j;
      }
    }
    return i;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { },
        new int[] { },
      },
      {
        new int[] { 1 },
        new int[] { 1 },
      },
      {
        new int[] { 1, 1 },
        new int[] { 1 },
      },
      {
        new int[] { 1, 1, 2 },
        new int[] { 1, 2 },
      },
      {
        new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 },
        new int[] { 0, 1, 2, 3, 4 },
      },
    };

    RemoveDuplicateFromSortedArray solution = new RemoveDuplicateFromSortedArray();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      int[] expected = (int[]) parameter[1];
      int size = solution.removeDuplicates(input);
      int[] actual = Arrays.copyOfRange(input, 0, size);
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
