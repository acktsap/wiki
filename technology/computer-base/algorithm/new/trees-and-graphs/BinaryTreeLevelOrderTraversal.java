import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/*
 * Descryption
 *
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 *
 * Given a binary tree, return the level order traversal of its nodes' values.
 * (ie, from left to right, level by level).
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
 * return its level order traversal as:
 * 
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 *
 *
 * Approach & Proof
 *
 * dfs하면서 depth별로 add val to list.get(depth)를 하자
 * dfs, do something : 
 *
 *
 * Complexity
 *
 *  - Time  : O(# of node)
 *  - Space : O(depth)
 *
 *
 * Review
 *
 * dfs로도 풀 수 있다는데 그러면 좀 길어지지 않나?
 *
 */
class BinaryTreeLevelOrderTraversal {
  public List<List<Integer>> levelOrder(final TreeNode root) {
    if (null == root) {
      return Collections.emptyList();
    }

    final List<List<Integer>> ret = new LinkedList<>();
    dfs(root, ret, 0); // depth starts from 0
    return ret;
  }
  
  protected void dfs(final TreeNode node, final List<List<Integer>> ret, final int depth) {
    // check next (if necessary)
    if (null == node) {
      return;
    }
    
    // do something
    if (ret.size() <= depth) {
      ret.add(new LinkedList<>());
    }
    ret.get(depth).add(node.val);
    
    // visit next
    dfs(node.left, ret, depth + 1);
    dfs(node.right, ret, depth + 1);
  }
  
  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new Integer[] { 1, null, 2, 3 },
        new Integer[] { 1, 3, 2 }
      },
    };
    final BinaryTreeLevelOrderTraversal solution = new BinaryTreeLevelOrderTraversal();
    for (final Object[] parameter : parameters) {
      final Integer[] tree = (Integer[]) parameter[0];
      final Integer[] expected = (Integer[]) parameter[1];
      final Integer[] actual = solution.inorderTraversal(TreeNode.of(tree)).toArray(new Integer[] {});
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but actual: " + Arrays.toString(actual));
      }
    }
  }
}
