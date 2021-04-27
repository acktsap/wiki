import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * Reverse a singly linked list.
 * 
 * Example:
 * 
 * Input: 1->2->3->4->5->NULL
 *
 * Output: 5->4->3->2->1->NULL
 *
 * Follow up:
 * 
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 * 
 *
 * Approach & Proof 
 *
 * Loop Invariant
 *
 * reverse(curr: ListNode, reversed: ListNode): ListNode
 *  - curr : holds next target (not reversed)
 *  - reversed : holds reversed one which was head of curr. next must not be curr
 *  - ret : reversed one
 *
 * 1. keep curr.next
 * 2. curr.next = reversed
 * 3. return reversed(keep, curr)
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
 * 재귀는 반복문으로도 바꿔보는게 좋을듯. 어차피 loop invarient는 같음
 *
 *
 */
class ReverseLinkedList {
  public ListNode reverseListRecursive(ListNode head) {
    if (null == head) {
      return null;
    }

    if (null == head.next) {
      return head;
    }

    ListNode next = head.next;
    head.next = null;
    return concat(next, head);
  }

  protected ListNode concat(ListNode curr, ListNode reversed) {
    if (null == curr) {
      return reversed;
    }

    ListNode keep = curr.next;
    curr.next = reversed;
    return concat(keep, curr);
  }

  public ListNode reverseListIterative(ListNode head) {
    if (null == head) {
      return null;
    }

    if (null == head.next) {
      return head;
    }

    ListNode curr = head.next;
    head.next = null;
    ListNode reversed = head;
    while (null != curr) {
      ListNode keep = curr.next;
      curr.next = reversed;
      reversed = curr;
      curr = keep;
    }
    return reversed;
  }


  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1 },
        new int[] { 1 },
      },
      {
        new int[] { 1, 2 },
        new int[] { 2, 1 },
      },
      {
        new int[] { 1, 2, 3, 4, 5 },
        new int[] { 5, 4, 3, 2, 1 },
      },
    };

    ReverseLinkedList solution = new ReverseLinkedList();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      int[] expected = (int[]) parameter[1];

      int[] actual1 = solution.reverseListRecursive(ListNode.of(input)).toArray();
      if (!Arrays.equals(expected, actual1)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual1));
      }

      int[] actual2 = solution.reverseListIterative(ListNode.of(input)).toArray();
      if (!Arrays.equals(expected, actual2)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual2));
      }
    }
  }
}
