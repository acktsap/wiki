/*
 * Descryption
 *
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * 
 * Note: A leaf is a node with no children.
 * 
 * Example:
 * 
 * Given binary tree [3,9,20,null,null,15,7],
 * 
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 
 * return its depth = 3.
 * 
 *
 * Approach & Proof 
 *
 * f(null) = 0
 * f(node) = 1 + Math.max(f(node.left), f(node.right))
 *
 * Complexity
 *
 *  - Time  : O(# of node)
 *  - Space : O(max depth of node)
 *
 * Review
 *
 * 재귀에서는 닥치고 점화식을 세우는 것이 짱이네
 *
 */
class MaximumDepthOfBinaryTree {
  public int maxDepth(TreeNode root) {
    if (null == root) {
      return 0;
    }
    
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    return 1 + Math.max(left, right);
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new Integer[] { 3, 9, 20, null, null, 15, 7 }, 
        2,
      },
    };
    MaximumDepthOfBinaryTree solution = new MaximumDepthOfBinaryTree();
    for (Object[] parameter : parameters) {
      Integer[] input = (Integer[]) parameter[0];
      int expected = (int) parameter[1];
      int actual = solution.maxDepth(TreeNode.of(input));
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
