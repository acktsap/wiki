import java.util.Arrays;
import java.util.stream.Collectors;

/*
 * Descryption
 *
 * Merge k sorted linked lists and return it as one sorted list.
 * Analyze and describe its complexity.
 *
 * https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * Approach & Proof 
 *
 * Let k be a size of array and '..' be a range
 *
 * f(1) = lists[0]
 * f(m..m+1) = merge(lists[m], lists[m+1])
 * f(1..k) = merge(f(1..k/2), f(k/2..k))
 *
 *
 * Complexity
 *
 * Let n be an average length of list.
 *  - Time
 *    - merge comparision of each depth * # of depth = k * n * log(k)
 *    - k*n -> k레벨에서는 대충 k개의 list가 n번 비교를 함
 *  - Space : n * k (한쪽으로 쏠린 케이스)
 *
 *
 * Review
 *
 * Collectors : java.util.stream안에 있다
 *
 * ListNode dummy = new ListNode(0);
 * ListNode curr = dummy;
 * ..
 * return dummy.next;
 * dummy는 건드리지 마라.. curr을 건들려라
 *
 */
class MergeKSortedList {
  public ListNode mergeKLists(ListNode[] lists) {
    return mergeInRange(lists, 0, lists.length);
  }

  protected ListNode mergeInRange(ListNode[] lists, int start, int end) {
    if ((end - start) == 0) {
      return null;
    }
    if ((end - start) == 1) {
      return lists[start];
    }
    if ((end - start) == 2) {
      return merge(lists[start], lists[start + 1]);
    }

    int mid = (start + end) / 2;
    return merge(mergeInRange(lists, start, mid), mergeInRange(lists, mid, end));
  }

  protected ListNode merge(ListNode l1, ListNode l2) {
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
        new int[][]
        {
          new int[] { 1, 4, 5 },
          new int[] { 1, 3, 4 },
          new int[] { 2, 6 },
        },
        new int[] { 1, 1, 2, 3, 4, 4, 5, 6 },
      },
      {
        new int[][]
        {
          new int[] { 1, 2, 4, 5, 5, 10, 12, 20},
          new int[] { 1, 3, 4 },
          new int[] { 2, 6, 10 },
          new int[] { 2, 7, 8 },
          new int[] { 1, 9, 11, 13 },
        },
        new int[] { 1, 1, 1, 2, 2, 2, 3, 4, 4, 5, 5, 6, 7, 8, 9, 10, 10, 11, 12, 13, 20 },
      },
    };

    MergeKSortedList solution = new MergeKSortedList();
    for (Object[] parameter : parameters) {
      int[][] lists = (int[][]) parameter[0];
      int[] expected = (int[]) parameter[1];
      ListNode[] input = Arrays.asList(lists).stream()
            .map(ListNode::of)
            .collect(Collectors.toList())
            .toArray(new ListNode[] { });
      int[] actual = solution.mergeKLists(input).toArray();
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but was: " + Arrays.toString(actual));
      }
    }
  }
}
