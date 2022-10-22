#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>
#include "TreeNode.cpp"

using namespace std;

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements);

/*
 * Descryption
 *
 * https://leetcode.com/problems/binary-tree-right-side-view/
 *
 * Given the root of a binary tree, imagine yourself standing on the right side of it,
 * return the values of the nodes you can see ordered from top to bottom.
 * 
 *  
 * Example 1:
 * 
 * 
 * Input: root = [1,2,3,null,5,null,4]
 * Output: [1,3,4]
 *
 * Example 2:
 * 
 * Input: root = [1,null,3]
 * Output: [1,3]
 *
 * Example 3:
 * 
 * Input: root = []
 * Output: []
 *  
 * 
 * Constraints:
 * 
 * - The number of nodes in the tree is in the range [0, 100].
 * - -100 <= Node.val <= 100
 *
 *
 *
 * Review
 *
 *
 */
class BinaryTreeRightSideView {
public:
  /*
    ?? 이거 그냥 구현 아님?? 아니네 중간에 비어있는 경우도 생각해야 한다.
    탐색하면서 각 level에 보이는걸 그대로 적기..?
    어떻게 탐색하든 상관없지 싶음.. pre-order로 해보자
    
    Let n be # of node
    - time: O(n)
    - space: O(log(n)) (call stack)
  */
  vector<int> rightSideView(TreeNode* root) {
    vector<int> orderCandidate(100, -999); // at most 100 node, node value is [-100, 100]
    traversePreOrder(root, 0, orderCandidate);
    
    vector<int> order;
    for (int next : orderCandidate) {
      if (next != -999) {
        order.push_back(next);
      }
    }
    return order;
  }
  
  void traversePreOrder(TreeNode* curr, int level, vector<int>& order) {
    if (curr == nullptr) {
      return;
    }
    
    order[level] = curr->val;
    traversePreOrder(curr->left, level + 1, order);
    traversePreOrder(curr->right, level + 1, order);
  }
};

int main() {
  vector<tuple<vector<int>, vector<int>>> parameters {
    make_tuple(
        vector<int> { 1, 2, 3, nullptr, 5, nullptr, 4 },
        vector<int> { 1, 3, 4 }
    ),
    make_tuple(
        vector<int> { 1, nullptr, 3 },
        vector<int> { 1, 3 }
    ),
    make_tuple(
        vector<int> { } ,
        vector<int> { }
    )
  };

  BinaryTreeRightSideView solution;
  for (auto& parameter : parameters) {
    auto& treeElements = get<0>(parameter);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.rightSideView(make_tree(treeElements));
      if (actual != expected) {
        cout << "Expected" << endl << expected;
        cout << "Actual" << endl << actual;
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
  os << endl;

  return os;
}
