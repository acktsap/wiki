/*
 * Descryption
 *
 * Given the root node of a binary search tree (BST) and a value.
 * You need to find the node in the BST that the node's value equals the given value.
 * Return the subtree rooted with that node. If such node doesn't exist, you should return NULL.
 * 
 * For example, 
 * 
 * Given the tree:
 *         4
 *        / \
 *       2   7
 *      / \
 *     1   3
 * 
 * And the value to search: 2
 * 
 * You should return this subtree:
 * 
 *       2     
 *      / \   
 *     1   3
 * 
 * In the example above, if we want to search the value 5, since there is no node with value 5, we should return NULL.
 * 
 * Note that an empty tree is represented by NULL, therefore you would see the expected output (serialized tree format) as [], not null.
 *
 * https://leetcode.com/problems/search-in-a-binary-search-tree/
 *
 *
 * Approach & Proof 
 *
 * Base case
 *   f(null, val) = null
 *   f(root, val) = root                if root.val == val
 *
 * Recursive
 *   f(root, val) = f(root.left, val)   if root.val > val
 *   f(root, val) = f(root.right, val)  if root.val < val
 *
 *
 * Complexity
 *
 *  - Time  : O(# of node)
 *  - Space : none
 *
 *
 * Review
 *
 * 그냥도 풀 수 있는데 점화식을 세우니까 낫네
 *
 *
 */
class SearchInBinarySearchTree {
  public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) {
      return null;
    }
    
    if (root.val == val) {
      return root;
    } else if (root.val > val) {
      return searchBST(root.left, val);
    } else { // root.val < val
      return searchBST(root.right, val);
    }
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        TreeNode.of(4, 2, 7, 1, 3),
        TreeNode.of(2, 1, 3)
      },
      {
        TreeNode.of(2, 1, 3),
        null
      },
    };
    SearchInBinarySearchTree solution = new SearchInBinarySearchTree();
    for (Object[] parameter : parameters) {
      TreeNode input = (TreeNode) parameter[0];
      TreeNode expected = (TreeNode) parameter[1];
      TreeNode actual = solution.searchBST(input);
      if (actual.equals(expected)) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
