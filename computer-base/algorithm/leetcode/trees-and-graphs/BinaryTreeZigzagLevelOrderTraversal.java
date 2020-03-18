import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;

/*
 * Descryption
 *
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 *
 * Given a binary tree, return the zigzag level order traversal of its nodes' values.
 * (ie, from left to right, then right to left for the next level and alternate between).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * 
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 
 * return its zigzag level order traversal as:
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 *
 *
 * Approach & Proof
 *
 * BFS는queue에 다음에 탐색할 것을 누적해서 집어넣는 원리임
 *
 * while (!queue.isEmpty())
 *   do something
 *   add new element to queue
 *
 * 이 원리를 Stack을 사용하는 식으로 조금 응용해보자
 *
 * Stack 이용해서 다음 레벨에 대해서 쌓으면 매번 역순으로 됨
 * 여기서 left를 먼저 넣을건지 right을 먼저 넣을건지는
 * 해당 레벨이 역순인지 아닌지에 따라 달려 있음
 *  - 순차 : left -> right
 *  - 역순 : right -> left
 *
 *
 * Complexity
 *
 *  - Time  : O(n), # of nodes in a tree
 *  - Space : O(max node count of single level)
 *
 *
 * Review
 *
 * Java에 Stack구현체가 Vector를 상속한게 마음에 안들어서 Deque를 사용
 * LinkedList가 Deque를 구현. Stack ADT에 대칭되는 사용하는 api
 *
 * - push -> add 
 * - top -> getLast 
 * - pop -> removeLast 
 *
 *
 */
class BinaryTreeZigzagLevelOrderTraversal {
  public List<List<Integer>> zigzagLevelOrder(final TreeNode root) {
    if (null == root) {
      return Collections.emptyList();
    }
    
    final List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<TreeNode> nextLevel = new LinkedList<>();
    nextLevel.add(root);
    int level = 0;
    while (!nextLevel.isEmpty()) {
      final Deque<TreeNode> currLevel = nextLevel;
      nextLevel = new LinkedList<>();
      ret.add(new LinkedList<>());
      while (!currLevel.isEmpty()) {
        final TreeNode curr = currLevel.getLast();
        ret.get(level).add(curr.val);
        if (level % 2 == 0) {
          if (null != curr.left) nextLevel.add(curr.left);
          if (null != curr.right) nextLevel.add(curr.right);
        } else {
          if (null != curr.right) nextLevel.add(curr.right);
          if (null != curr.left) nextLevel.add(curr.left);
        }
        currLevel.removeLast();
      }
      ++level;
    }
    return ret;
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new Integer[] { 3, 9, 20, null, null, 15, 7 },
        new int[][] { 
          { 3 },
          { 20, 9 },
          { 15, 7 },
        },
      },
    };
    final BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();
    for (final Object[] parameter : parameters) {
      final Integer[] tree = (Integer[]) parameter[0];
      final int[][] expected = (int[][]) parameter[1];
      final int[][] actual = solution.inorderTraversal(TreeNode.of(tree)).stream()
                                 .map(i -> i.toArray(new Integer[]{}));
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but actual: " + Arrays.toString(actual));
      }
    }
  }
}
