import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/xxx
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * Approach & Proof 
 *
 * 두개씩 하면서 재귀로 풀자
 *
 * f(head) = swap(head, head.next) + f(head.next.next)
 * f(head) = null   where null == head
 * f(head) = head   where null == head.next
 *
 * Complexity
 *
 *  - Time  : O(n / 2)
 *  - Space : O(1)
 *
 * Review
 *
 * head.next = swapPairs()이렇게 하는게 좋은가?
 * holds를 굳이 안써도 되긴 하는데 가독성을 위해 쓴다
 */
class SwapNodesInPairs {
  public ListNode swapPairs(final ListNode head) {
    if (null == head) return null;
    if (null == head.next) return head;

    final ListNode hold = head.next.next;
    final ListNode next = head.next;
    next.next = head;
    head.next = swapPairs(hold);
    return next;
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 3, 4 },
        new int[] { 2, 1, 4, 3 },
      },
    };

    final SwapNodesInPairs solution = new SwapNodesInPairs();
    for (final Object[] parameter : parameters) {
      final int[] input = (int[]) parameter[0];
      final int[] expected = (int[]) parameter[1];
      final int[] actual = solution.swapPairs(ListNode.of(input)).toArray();
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
