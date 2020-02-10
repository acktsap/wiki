
====== Divide and Conquer ======




# Divide & Conquer

## Structure

  1. Divide into several problems
  2. Solve each sub-problem
  3. Compute final solution using solution of sub-problems
  Consists of "Divide", "Merge", "base case"(can be solved directly)
  Should be represented in reculsive function

## Divide & Conquer vs bruce force

  bruce force : divide into one part & left big part
  divice & conquer : divide into sub-problem of almost same size

    example)

    /* recursiveSum by bruce force, takes O(n) */
    int recursiveSum(int n) {
        if( n == 1 ) return 1;  // base case
        return n + recursiveSum(n - 1);
    }

    /*
        compute sum from 1 to n
        fastSum(n)
            = 1 + 2 + ... + n
            = (1 + 2 + ... + n/2) + {(n/2 + 1) + ... + (n/2 + n/2)}
            = (1 + 2 + ... + n/2) + (n/2)*(n/2) + (1 + 2 + ... + n/2)
            = fastSum(n/2) + (n/2)*(n/2) + fastSum(n/2)
            = 2*fastSum(n/2) + (n/2)*(n/2), n >= 2 && n % 2 == 0
        fastSum(n) = fastSum(n - 1) + n, n >= 2 & n % 2 == 1
        fastSum(n) = 1, n == 1

        O(log(n))
    */
    int fastSum(const int n) {
        // base case
        if( n == 1 ) return 1;

        // divide into several problems
        if( n % 2 == 1 ) return faseSum(n - 1) + n;
        return 2 * faseSum(n / 2) + (n / 2) * (n / 2);
    }


# Example 1 : merge K list

     /* Let l1.size + l2.size = n, then W(n) ~ O(n) */
    ListNode* merge(ListNode* l1, ListNode* l2) {
        ListNode dummy(0);

        ListNode *curr = &dummy;
        while( l1 && l2 ) {
            if( l1->val < l2->val ) {
                curr->next = l1;
                l1 = l1->next;
            } else {
                curr->next = l2;
                l2 = l2->next;
            }
            curr = curr->next;
        }

        curr->next = l1 ? l1 : l2;

        return dummy.next;
    }

    /* W(n) ~ O(nlog(k)) */
    ListNode* mergeKLists(vector<ListNode*>& lists) {
        if( lists.size() == 0 ) return nullptr;
        if( lists.size() == 1 ) return lists.front();
        if( lists.size() == 2 ) return merge(lists.front(), lists.back());

        int mid = lists.size() / 2;
        vector<ListNode*> frontPart(lists.begin(), lists.begin() + mid);
        vector<ListNode*> backPart(lists.begin() + mid, lists.end());

        return merge(mergeKLists(frontPart), mergeKLists(backPart));
    }
