
====== Two-Pointer-Algorithm ======


    Two pointer algorithm
    Example 1[Two sum]
    Example 2[Move zeros]
    Example 3[Find cycle on a linked list]



 o Two pointer algorithm

    Just use two pointer in a fancy way


 o Example 1[Two sum]

    /**
     * @param numbers Sorted positive values
     * @param target
     * @return List of index pair whose sum of each value == target
     */
    vector<int> twoSum(vector<int>& numbers, int target) {
        int i = 0, j = number.size() - 1;
        while( i < j ) {
            if( numbers[i] + numbers[j] < target ) --j;
            else if( numbers[i] + numbers[j] > target ) ++i;
            else vector<int>{ i, j };
        }

        return vector<int>();
    }


 o Example 2[Move zeros]

    /**
     * Move 0 of nums to the end of it while maintaining the relative order by in-place
     * eg. [0, 1, 0, 3, 12] -> [1, 3, 12, 0, 0]
     * @param nums numbers containing 0 and other integers
     */
    void moveZeroes(vector<int>& nums) {
        // i : index of leftmost 0
        // j : index of rightmost non-zero value starting from index i
        int i = 0, j = 1;
        while( i < nums.size() && j < nums.size() ) {
            if( nums[i] != 0 ) ++i;
            else if( nums[i] == 0 && nums[j] != 0 ) swap(nums[i++], nums[j]);
            ++j;
        }
    }


 o Example 3[Find cycle on a linked list]

    /**
     * @param head head of a linked list
     * @return whether there is a cycle or not
     */
    bool hasCycle(ListNode *head) {
        ListNode* slower = head;
        ListNode* faster = head;
        while( faster && faster->next && faster->next->next ) {
            slower = slower->next;
            faster = faster->next->next;
            if( slower == faster ) return true; // if there is a cycle, slower will follow faster one
        }
        return false;
    }
