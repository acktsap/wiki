#include <vector>
#include <tuple>
#include <queue>
#include <queue>
#include <memory>
#include <iostream>

using namespace std;

struct TreeNode {
  int val;
  TreeNode* left;
  TreeNode* right;
  TreeNode() : val(0), left(nullptr), right(nullptr) {}
  TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
  TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

// ignore the memory leak.. it's just coding test
TreeNode* make_tree(const vector<int>& elements) {
  if (elements.size() == 0) {
    return nullptr;
  }

  TreeNode *root = new TreeNode(elements[0]);
  queue<TreeNode*> q;
  q.push(root);
  for (int i = 0; i < elements.size(); ++i) {
    if (elements[i] == INT_MIN) {
      continue;
    }

    TreeNode *curr = q.front();
    q.pop();

    int left = 2*i + 1;
    int right = 2*i + 2;
    if (left < elements.size() && elements[left] != INT_MIN) {
      TreeNode *leftNode = new TreeNode(elements[left]);
      curr->left = leftNode;
      q.push(leftNode);
    }
    if (right < elements.size() && elements[right] != INT_MIN) {
      TreeNode *rightNode = new TreeNode(elements[right]);
      curr->right = rightNode;
      q.push(rightNode);
    }
  }

  return root;
}

vector<int> bfs(TreeNode* root) {
  queue<TreeNode*> q;
  q.push(root);

  vector<int> ret;
  ret.push_back(root->val);
  while (!q.empty()) {
    TreeNode *curr = q.front();
    q.pop();

    if (curr->left != nullptr) {
      q.push(curr->left);
      ret.push_back(curr->left->val);
    } else {
      ret.push_back(INT_MIN);
    }

    if (curr->right != nullptr) {
      q.push(curr->right);
      ret.push_back(curr->right->val);
    } else {
      ret.push_back(INT_MIN);
    }
  }

  return ret;
}

/*
int main() {
  vector<vector<int>> parameters {
    vector<int> { 2, 1, 3 },
    vector<int> { 5, 1, 4, INT_MIN, INT_MIN, 3, 6 },
    vector<int> { 4, 2, 7, 1, 3, INT_MIN, INT_MIN, INT_MIN, INT_MIN, INT_MIN, INT_MIN },
    vector<int> { 40, 20, 60, 10, 30, 50, 70 }
  };

  for (const auto& parameter : parameters) {
    auto root = make_tree(parameter);
    auto actual = bfs(root);

    for (auto a : actual) {
      cout << a << " ";
    }
    cout << endl;
  }

  return 0;
}
*/
