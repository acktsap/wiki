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
        ListNode.of(1, 2),
        ListNode.of(3, 4),
        ListNode.of(1, 2, 3, 4),
      },
      {
        ListNode.of(1, 2, 4),
        ListNode.of(1, 3, 4),
        ListNode.of(1, 1, 2, 3, 4, 4),
      },
    };

    MergeTwoSortedList solution = new MergeTwoSortedList();
    for (Object[] parameter : parameters) {
      ListNode l1 = (ListNode) parameter[0];
      ListNode l2 = (ListNode) parameter[1];
      ListNode expected = (ListNode) parameter[2];
      ListNode actual = solution.mergeTwoLists(l1, l2);
      if (!expected.equals(actual)) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
