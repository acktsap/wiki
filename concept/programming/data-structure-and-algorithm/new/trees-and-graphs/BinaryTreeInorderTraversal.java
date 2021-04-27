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
 *
 * Approach & Proof
 *
 * dfs(left)
 * ret.push(curr.val)
 * dfs(right)
 *
 *
 * Complexity
 *
 *  - Time  : O(n), traverse each tree node
 *  - Space : O(n), call depth can be n in worst case
 * 
 *
 * Review
 *
 * null check를 함수 호출 전에 할 것인가 아니면 호출 후 할 것인가?
 * -> 경우에 따라 둘다 하는게 좋을거 같음. 여기서는 root에서만
 *
 */
class BinaryTreeInorderTraversal {
  public List<Integer> inorderTraversal(TreeNode root) {
    if (null == root) {
      return Collections.emptyList();
    }
    
    List<Integer> traversals = new LinkedList<>();
    dfs(root, traversals);
    return traversals;
  }
  
  protected void dfs(TreeNode curr, List<Integer> traversals) {
    if (null == curr) {
      return;
    }
    
    dfs(curr.left, traversals);
    traversals.add(curr.val);
    dfs(curr.right, traversals);
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new Integer[] { 1, null, 2, 3 },
        new Integer[] { 1, 3, 2 }
      },
    };
    BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();
    for (Object[] parameter : parameters) {
      Integer[] tree = (Integer[]) parameter[0];
      Integer[] expected = (Integer[]) parameter[1];
      Integer[] actual = solution.inorderTraversal(TreeNode.of(tree)).toArray(new Integer[] {});
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but actual: " + Arrays.toString(actual));
      }
    }
  }
}
