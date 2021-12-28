import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ListNode {
  static ListNode of(int... arr) {
    if (arr.length == 0) {
      return null;
    }

    ListNode dummy = new ListNode(0);
    ListNode pre = dummy;
    for (int val : arr) {
      pre.next = new ListNode(val);
      pre = pre.next;
    }
    return dummy.next;
  }

  static ListNode copyOf(ListNode node) {
    if (node == null) {
      return null;
    }

    ListNode dummy = new ListNode(0);
    ListNode copied = new ListNode(node.val);
    dummy.next = copied;

    ListNode next = node.next;
    while (next != null) {
      copied.next = new ListNode(next.val);
      copied = copied.next;
      next = next.next;
    }

    return dummy.next;
  }

  int val;
  ListNode next;
  ListNode(int x) { val = x; }

  int[] toArray() {
    List<Integer> list = new ArrayList<>();
    ListNode curr = this;
    while (null != curr) {
      list.add(curr.val);
      curr = curr.next;
    }
    return list.stream().mapToInt(i -> i).toArray();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof ListNode)) {
      return false;
    }

    ListNode left = this;
    ListNode right = (ListNode) other;
    while (left != null && right != null) {
      if (left.val != right.val) {
        return false;
      }

      left = left.next;
      right = right.next;
    }

    return left == null && right == null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    ListNode curr = this;
    while (curr != null) {
      if (curr != this) {
        sb.append(", ");
      }
      sb.append(curr.val);
      curr = curr.next;
    }
    sb.append("]");
    return sb.toString();
  }
}
