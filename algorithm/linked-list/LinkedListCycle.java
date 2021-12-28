import java.util.Objects;
import java.util.function.Supplier;

/*
 * Descryption
 *
 * https://leetcode.com/problems/linked-list-cycle/
 *
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * 
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.
 * Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 * 
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 *  
 * Example 1:
 * 
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 *
 * Example 2:
 * 
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
 *
 * Example 3:
 * 
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *  
 * Constraints:
 * 
 * The number of the nodes in the list is in the range [0, 10^4].
 * -10^5 <= Node.val <= 10^5
 * pos is -1 or a valid index in the linked-list.
 *
 *
 * Approach & Proof 
 *
 * Floyd's Cycle approach : if there is a cycle, slower will follow faster one
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
 *
 */
class LinkedListCycle {
  public boolean hasCycle(ListNode head) {
    ListNode slower = head;
    ListNode faster = head;
    while (faster != null && faster.next != null) { // check faster only
      slower = slower.next;
      faster = faster.next.next;
      if (slower == faster) {
        return true;
      }
    }
    
    return false;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new Supplier<ListNode>() {
          @Override
          public ListNode get() {
            ListNode node = ListNode.of(1, 2, 3, 4, 5);
            node.next.next.next.next = node.next;
            return node;
          }
        },
        true,
      },
      {
        new Supplier<ListNode>() {
          @Override
          public ListNode get() {
            ListNode node = ListNode.of(1, 2);
            node.next.next = node;
            return node;
          }
        },
        true,
      },
      {
        new Supplier<ListNode>() {
          @Override
          public ListNode get() {
            ListNode node = ListNode.of(1, 2, 3, 4, 5);
            return node;
          }
        },
        false,
      },
      {
        new Supplier<ListNode>() {
          @Override
          public ListNode get() {
            return null;
          }
        },
        false,
      },
    };

    var solution = new LinkedListCycle();
    for (Object[] parameter : parameters) {
      Supplier<ListNode> input = (Supplier<ListNode>) parameter[0];
      boolean expected = (boolean) parameter[1];

      boolean actual = solution.hasCycle(input.get());
      if (!Objects.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
