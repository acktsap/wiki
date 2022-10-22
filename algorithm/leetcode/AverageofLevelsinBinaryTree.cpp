#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>
#include "treenode.cpp"

using namespace std;

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements);

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid);

/*
 * Descryption
 *
 * https://leetcode.com/problems/average-of-levels-in-binary-tree/
 *
 * Given the root of a binary tree, return the average value of the nodes on each level in the form of an array.
 * Answers within 10-5 of the actual answer will be accepted.
 *  
 * 
 * Example 1:
 * 
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [3.00000,14.50000,11.00000]
 * Explanation: The average value of nodes on level 0 is 3, on level 1 is 14.5, and on level 2 is 11.
 * Hence return [3, 14.5, 11].
 *
 * Example 2:
 * 
 * Input: root = [3,9,20,15,7]
 * Output: [3.00000,14.50000,11.00000]
 *  
 * 
 * Constraints:
 * 
 * - The number of nodes in the tree is in the range [1, 104].
 * - -2^31 <= Node.val <= 2^31 - 1
 *
 *
 * Review
 *
 *
 */
class AverageofLevelsinBinaryTree {
public:
  /*
    use bfs
    
    loop invariant
    
    - q : holds next level
    - ret : holds processed average value
    
    complexity
    
    - time: O(N), N : # of node
    - space: O(max count at one level)
  */
  vector<double> averageOfLevels(TreeNode* root) {
    queue<TreeNode*> q;
    q.push(root);
    
    vector<double> ret;
    while (!q.empty()) {
      int allCount = q.size();
      int count = allCount;
      double sum = 0.0;
      while (0 < count) {
        TreeNode* next = q.front();
        q.pop();
        
        sum += next->val;
        if (next->left != nullptr) {
          q.push(next->left);
        }
        if (next->right != nullptr) {
          q.push(next->right);
        }
        --count;
      }
      ret.push_back(sum / (double) allCount);
    }
    
    return ret;
  }
};

int main() {
  vector<tuple<vector<int>, vector<double>>> parameters {
    make_tuple(
        vector<optional<int>> { 3, 9, 20, {}, {}, 15, 7 }, 
        vector<double> { 3.00000, 14.50000, 11.00000 }
    ),
    make_tuple(
        vector<int> { 3, 9, 20, 15, 7 }, 
        vector<double> {  3.00000, 14.50000, 11.00000 }
    )
  };

  AverageofLevelsinBinaryTree solution;
  for (auto& parameter : parameters) {
    auto& treeElements = get<0>(parameter);
    auto treeNode = make_tree(treeElements);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.averageOfLevels(treeNode);
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
