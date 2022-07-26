import java.lagn.Math;

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
 * 
 * Approach & Proof 
 *
 * 1. Bottom Up
 *
 * f(node) = 0     if node == null
 * f(node) = 1 + max(f(node.left), f(node.right))   if node != null
 *
 * complexity
 *
 *  - Time  : O(# of node)
 *  - Space : O(max depth of node)
 *
 *
 * 2. Top down
 *
 * f(node) = 0     if node is null
 * f(node) if node is root,
 * f(node) = 1 + max(f(node.left), f(node.right))   if node != null
 *
 * complexity
 *
 *  - Time  : O(# of node)
 *  - Space : O(max depth of node)
 * 
 *
 * Review
 *
 *
 */
class MaximumDepthOfBinaryTree {
  public int maxDepthBottomUp(TreeNode root) {
    if (root == null) {
      return 0;
    }
    
    int left = maxDepthBottomUp(root.left);
    int right = maxDepthBottomUp(root.right);

    return 1 + Math.max(left, right);
  }

  public int maxDepthTopDown(TreeNode root) {
    return maxDepthTopDown(root, 0);
  }

  protected int maxDepthTopDown(TreeNode root, int depth) {
    if (root == null) {
      return 0;
    }
    
    int max = 0;
    
    if (root.left == null && root.right == null) {
      max = Math.max(depth + 1, max);
    }
    
    max = Math.max(maxDepthTopDown(root.left, depth + 1), max);
    max = Math.max(maxDepthTopDown(root.right, depth + 1), max);
    
    return max;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        TreeNode.of(3, 9, 20, null, null, 15, 7), 
        2,
      },
    };

    var solution = new MaximumDepthOfBinaryTree();
    for (Object[] parameter : parameters) {
      TreeNode tree = (TreeNode) parameter[0];
      int expected = (int) parameter[1];

      {
        int actual = solution.maxDepthBottomUp(tree);
        if (expected != actual) {
          throw new IllegalStateException("Expected: " + expected +
              ", but was: " + actual);
        }
      }

      {
        int actual = solution.maxDepthTopDown(tree);
        if (expected != actual) {
          throw new IllegalStateException("Expected: " + expected +
              ", but was: " + actual);
        }
      }
    }
  }
}
