import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * 
 * Example 1:
 * 
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 *
 * Example 2:
 * 
 * Input: head = [1,2]
 * Output: [2,1]
 *
 * Example 3:
 * 
 * Input: head = []
 * Output: []
 *  
 * Constraints:
 * 
 * The number of nodes in the list is the range [0, 5000].
 * -5000 <= Node.val <= 5000
 * 
 * Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
 *
 *
 * Approach & Proof 
 *
 * Iterative
 *
 * - Loop Invariant
 *   - reversed : head of reversed node
 *   - curr : head of left node
 *
 * Recursive
 *
 * Let f(head) returns head of resersed list.
 *
 * f(head) = f(head.next) points to head
 *         = head   --- if head.next is null
 *         = null   --- if head is null
 * 
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
 * 재귀에서 reversed head 를 return 하는데 어떻게 끝에 head를 넣지하지? 했었는데
 * 이미 넣을때 마지막에 넣어야 하는 값을 알고 있었음
 *
 *
 */
class ReverseLinkedList {
  public ListNode reverseListIterative(ListNode head) {
    ListNode reversed = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
      curr.next = reversed;
      
      reversed = curr;
      curr = next;
    }
    
    return reversed;
  }

  public ListNode reverseListRecursive(ListNode head) {
    if (head == null) {
      return null;
    }
    
    if (head.next == null) {
      return head;
    }

    ListNode next = head.next;
    ListNode reversed = reverseListRecursive(head.next);
    next.next = head;
    head.next = null;
   
    return reversed;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        ListNode.of(1, 2, 3, 4, 5),
        ListNode.of(5, 4, 3, 2, 1),
      },
      {
        ListNode.of(1, 2),
        ListNode.of(2, 1),
      },
      {
        ListNode.of(1),
        ListNode.of(1),
      },
      {
        null,
        null,
      },
    };

    var solution = new ReverseLinkedList();
    for (Object[] parameter : parameters) {
      ListNode input = (ListNode) parameter[0];
      ListNode expected = (ListNode) parameter[1];

      ListNode actualIterative = solution.reverseListIterative(ListNode.copyOf(input));
      if (!Objects.equals(expected, actualIterative)) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actualIterative);
      }

      ListNode actualRecursive = solution.reverseListRecursive(ListNode.copyOf(input));
      if (!Objects.equals(expected, actualRecursive)) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actualRecursive);
      }
    }
  }
}
