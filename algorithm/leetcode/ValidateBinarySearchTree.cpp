#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>
#include "TreeNode.cpp"

using namespace std;

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements);

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid);

/*
 * Descryption
 *
 * https://leetcode.com/problems/validate-binary-search-tree/
 *
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 *
 * Example 1:
 *
 * Input: root = [2,1,3]
 * Output: true
 *
 * Example 2:
 *
 * Input: root = [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 *
 *
 * Constraints:
 *
 * - The number of nodes in the tree is in the range [1, 10^4].
 * - -2^31 <= Node.val <= 2^31 - 1
 *
 *
 *
 * Review
 *
 *
 */
class ValidateBinarySearchTree {
public:
  /*
    Recursively
    Pre-order

    validate by range

    - time: O(n)
    - space: O(10^4)
  */
  bool isValidBST(TreeNode* root) {
    return isValidBst(root, LONG_MIN, LONG_MAX);
  }

  bool isValidBst(TreeNode* node, long min, long max) {
    if (node == nullptr) {
      return true;
    }

    TreeNode* left = node->left;
    TreeNode* right = node->right;
    if (node->val <= min || max <= node->val) {
      return false;
    }

    if (left != nullptr && node->val <= left->val) {
      return false;
    }

    if (right != nullptr && right->val <= node->val) {
      return false;
    }

    return isValidBst(left, min, node->val) && isValidBst(right, node->val, max);
  }
};

int main() {
  vector<tuple<vector<int>, bool>> parameters {
    make_tuple(
        vector<int> { 2, 1, 3 },
        true
    ),
    make_tuple(
        vector<int> { 5, 1, 4, INT_MIN, INT_MIN, 3, 6 },
        false
    )
  };

  ValidateBinarySearchTree solution;
  for (auto& parameter : parameters) {
    auto& treeElements = get<0>(parameter);
    auto& expected = get<1>(parameter);
    auto treeNode = make_tree(treeElements);

    {
      auto actual = solution.isValidBST(treeNode);
      if (actual != expected) {
        cout << "Expected" << endl << expected << endl;
        cout << "Actual" << endl << actual << endl;
        assert (false);
      }
    }
  }
}

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements){
  if (elements.size() == 0) {
    return os;
  }

  os << elements[0];
  for (int i = 1; i < elements.size(); i++) {
      os << ", " << elements[i];
  }

  return os;
}

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid){
  for(int i = 0; i < grid.size(); i++) {
    os << grid[i][0];
    for (int j = 1; j < grid[i].size(); j++) {
        os << ", " << grid[i][j];
    }
    os << endl;
  }

  return os;
}
