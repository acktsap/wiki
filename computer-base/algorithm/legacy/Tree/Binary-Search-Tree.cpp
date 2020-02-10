
====== Binary-Search-Tree ======



 o delete node with with key

     /**
      * struct TreeNode {
      *     int val;
      *     TreeNode *left;
      *     TreeNode *right;
      *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
      * };
      *
      * return tree with node of key deleted
      */
     TreeNode* deleteNode(TreeNode* root, int key) {
         if( root == nullptr ) return nullptr;

         if( key < root->val ) {
             root->left = deleteNode(root->left, key);
         } else if( root->val < key ) {
             root->right = deleteNode(root->right, key);
         } else {
             // it covers leaf case, if any of the child are null, return other side
             // and therefore, if both are null, return nullptr
             if( root->left == nullptr ) return root->right;
             else if( root->right == nullptr ) return root->left;

             // root->left && root->right
             // then replace target element with min of right subtree
             TreeNode* minOfRight = root->right;
             while( minOfRight->left ) {
                 minOfRight = minOfRight->left;
             }
             root->val = minOfRight->val;   // set deleted node with min of right
             root->right = deleteNode(root->right, minOfRight->val);    // remove min of right
         }

         return root;
     }
