import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ListNode {
  public static ListNode of(final int[] arr) {
    if (arr.length == 0) {
      return null;
    }

    final ListNode dummy = new ListNode(0);
    ListNode pre = dummy;
    for (final int val : arr) {
      pre.next = new ListNode(val);
      pre = pre.next;
    }
    return dummy.next;
  }

  int val;
  ListNode next;
  ListNode(int x) { val = x; }

  public int[] toArray() {
    final List<Integer> list = new ArrayList<>();
    ListNode curr = this;
    while (null != curr) {
      list.add(curr.val);
      curr = curr.next;
    }
    return list.stream().mapToInt(i -> i).toArray();
  }
}
