import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/merge-two-sorted-lists/
 *
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 * 
 * Example:
 * 
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 * 
*
 * Approach & Proof
 *
 * loop invarient
 *
 *  - pre holds pre pointer
 *  - left holds l1 pointer or null
 *  - right holds l2 pointer or null
 *
 * Complexity
 *
 *  - Space : O(n)
 *  - Time  : O(n)
 * 
 * Review
 *
 * - Use dummy head list for pre (and return dummy.next)
 * - 이거 그냥 외워야 하는 패턴급임
 *
 */
class MergeTwoSortedList {
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode pre = dummy;
    ListNode left = l1;
    ListNode right = l2;
    while (null != left && null != right) {
      if (left.val < right.val) {
        pre.next = new ListNode(left.val);
        pre = pre.next;
        left = left.next;
      } else {
        pre.next = new ListNode(right.val);
        pre = pre.next;
        right = right.next;
      }
    }
    while (null != left) {
      pre.next = new ListNode(left.val);
      pre = pre.next;
      left = left.next;
    }
    while (null != right) {
      pre.next = new ListNode(right.val);
      pre = pre.next;
      right = right.next;
    }
    return dummy.next;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2 },
        new int[] { 3, 4 },
        new int[] { 1, 2, 3, 4 },
      },
      {
        new int[] { 1, 2, 4 },
        new int[] { 1, 3, 4 },
        new int[] { 1, 1, 2, 3, 4, 4 },
      },
    };
    MergeTwoSortedList solution = new MergeTwoSortedList();
    for (Object[] parameter : parameters) {
      int[] l1 = (int[]) parameter[0];
      int[] l2 = (int[]) parameter[1];
      int[] expected = (int[]) parameter[2];
      int[] actual = solution.mergeTwoLists(ListNode.of(l1), ListNode.of(l2)).toArray();
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", actual: " + Arrays.toString(actual));
      }
    }
  }
}
