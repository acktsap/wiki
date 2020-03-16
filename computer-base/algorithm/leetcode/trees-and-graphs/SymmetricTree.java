/*
 * Descryption
 *
 * https://leetcode.com/problems/symmetric-tree/
 *
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * 
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 * 
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * 
 * But the following [1,2,2,null,3,null,3] is not:
 * 
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 * 
 * Approach & Proof 
 *
 * f(null, null) = true
 * f(left, null) = false
 * f(null, right) = false
 * f(left, right) = (left.val == right.val) && f(left.left, right.right) 77 f(left.right, right.left)
 *
 * Complexity
 *
 *  - Time  : O(max depth of node)
 *  - Space : O(max depth of node)
 *
 * Review
 *
 * dfs를 하는데 node를 2개 들고 한다는 발상!!<F2>
 *
 */
class SymmetricTree {
  public boolean isSymmetric(final TreeNode root) {
    if (null == root) {
      return true;
    }
    return isSymmetric(root.left, root.right);
  }
  
  protected boolean isSymmetric(final TreeNode left, final TreeNode right) {
    if (null == left && null == right) {
      return true;
    }
    if ((null == left && null != right) ||
        (null != left && null == right)) {
      return false;
    }
    
    final boolean curr = left.val == right.val;
    return curr && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
  }
  
  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      {
        new Integer[] { 1, 2, 2, 3, 4, 4, 3 }, 
        true,
      },
      {
        new Integer[] { 1, 2, 2, null, 3, null, 3 }, 
        false,
      },
    };
    final SymmetricTree solution = new SymmetricTree();
    for (final Object[] parameter : parameters) {
      final Integer[] input = (Integer[]) parameter[0];
      final boolean expected = (boolean) parameter[1];
      final boolean actual = solution.isSymmetric(TreeNode.of(input));
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
