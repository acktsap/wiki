import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/*
 * Descryption
 *
 * https://leetcode.com/problems/binary-tree-inorder-traversal/
 *
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * 
 * Example:
 * 
 * Input: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 * 
 * Output: [1,3,2]
 *
 * Approach & Proof
 *
 * dfs(left)
 * ret.push(curr.val)
 * dfs(right)
 *
 * Complexity
 *
 *  - Time  : O(n), traverse each tree node
 *  - Space : O(n), call depth can be n in worst case
 * 
 * Review
 *
 * null check를 함수 호출 전에 할 것인가 아니면 호출 후 할 것인가?
 * -> 둘다 하는게 좋을거 같음
 *
 */
class BinaryTreeInorderTraversal {
  public List<Integer> inorderTraversal(final TreeNode root) {
    if (null == root) {
      return Collections.emptyList();
    }
    
    final List<Integer> traversals = new LinkedList<>();
    dfs(root, traversals);
    return traversals;
  }
  
  protected void dfs(final TreeNode curr, final List<Integer> traversals) {
    if (null == curr) {
      return;
    }
    
    if (null != curr.left) dfs(curr.left, traversals);
    traversals.add(curr.val);
    if (null != curr.right) dfs(curr.right, traversals);
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new Integer[] { 1, null, 2, 3 },
        new Integer[] { 1, 3, 2 }
      },
    };
    final BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();
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
