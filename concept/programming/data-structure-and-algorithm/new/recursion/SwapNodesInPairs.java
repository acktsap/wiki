import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/swap-nodes-in-pairs/
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 *
 *
 * Approach & Proof 
 *
 * 두개씩 하면서 재귀로 풀자
 *
 * f(head) = swap(head, head.next) + f(head.next.next)
 * f(head) = null   where null == head
 * f(head) = head   where null != head && null == head.next
 *
 *
 * Complexity
 *
 *  - Time  : O(n / 2)
 *  - Space : O(1)
 *
 *
 * Review
 *
 * head.next = swapPairs()이렇게 하는게 좋은가?
 * holds를 굳이 안써도 되긴 하는데 가독성을 위해 쓴다
 *
 */
class SwapNodesInPairs {
  public ListNode swapPairs(ListNode head) {
    if (null == head) {
      return null;
    }
    if (null == head.next) {
      return head;
    }

    ListNode hold = head.next.next;
    ListNode next = head.next;
    next.next = head;
    head.next = swapPairs(hold);
    return next;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 3, 4 },
        new int[] { 2, 1, 4, 3 },
      },
    };

    SwapNodesInPairs solution = new SwapNodesInPairs();
    for (Object[] parameter : parameters) {
      int[] input = (int[]) parameter[0];
      int[] expected = (int[]) parameter[1];
      int[] actual = solution.swapPairs(ListNode.of(input)).toArray();
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
