#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 *
 * Given an array of integers nums sorted in non-decreasing order,
 * find the starting and ending position of a given target value.
 * 
 * If target is not found in the array, return [-1, -1].
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 *  
 * 
 * Example 1:
 * 
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 *
 * Example 2:
 * 
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 *
 * Example 3:
 * 
 * Input: nums = [], target = 0
 * Output: [-1,-1]
 *  
 * 
 * Constraints:
 * 
 * - 0 <= nums.length <= 10^5
 * - -10^9 <= nums[i] <= 10^9
 * - nums is a non-decreasing array.
 * - -10^9 <= target <= 10^9
 *
 *
 *
 * Review
 *
 * log(n)은 역시 binary search지.
 * 복잡해보이면 두개로 나눠서 생각하는게 최고다 역시.
 * 이거 괜히 한번에 구하려고 했다가 복잡해졌을듯.
 *
 *
 */
class FindFirstandLastPositionofElementinSortedArray {
public:
  /*
    log(n)? 누가봐도 binary search~
    
    한번에 찾으려 하지 말고 두개 나눠서 찾자. 시작과 끝 따로
    그래도 2*log(n)이라서 O(log(n))이다.
    한개 찾아서 앞뒤로 가려고 하면 최악의 경우 n이 된다 (값이 모두 같은 경우).
    
    - time: O(log(n))
    - space: O(1)
  */
  vector<int> searchRange(vector<int>& nums, int target) {
    int first = searchFirst(nums, target);
    int last = searchLast(nums, target);
    
    return { first, last };
  }
  
  int searchFirst(vector<int>& nums, int target) {
    int from = 0;
    int to = nums.size() - 1;
    
    int firstIndex = -1;
    while (from <= to) {
      int mid = (from + to) / 2;
      if (nums[mid] == target) {
        firstIndex = mid;
        to = mid - 1;
      } else if (nums[mid] < target) {
        from = mid + 1;
      } else { // nums[mid] > target
        to = mid - 1;
      }
    }
    
    return firstIndex;
  }
     
  int searchLast(vector<int>& nums, int target) {
    int from = 0;
    int to = nums.size() - 1;
    
    int lastIndex = -1;
    while (from <= to) {
      int mid = (from + to) / 2;
      if (nums[mid] == target) {
        lastIndex = mid;
        from = mid + 1;
      } else if (nums[mid] < target) {
        from = mid + 1;
      } else { // nums[mid] > target
        to = mid - 1;
      }
    }
    
    return lastIndex;
  }
};

int main() {
  vector<tuple<vector<int>, int, vector<int>>> parameters {
    make_tuple(
        vector<int> { 5, 7, 7, 8, 8, 10 },
        8,
        vector<int> { 3, 4 }
    ),
    make_tuple(
        vector<int> { 5, 7, 7, 8, 8, 10 },
        6,
        vector<int> { -1, -1 }
    ),
    make_tuple(
        vector<int> { },
        8,
        vector<int> { -1, -1 }
    ),
    make_tuple(
        vector<int> { 1, 2, 2, 2, 2, 2, 2, 2, 2 },
        2,
        vector<int> { 1, 8 }
    )
  };

  FindFirstandLastPositionofElementinSortedArray solution;
  for (auto& parameter : parameters) {
    auto& nums = get<0>(parameter);
    auto& target = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
      auto actual = solution.searchRange(nums, target);
      if (actual != expected) {
        cout << "Expected: ";
        copy(expected.begin(), expected.end(), ostream_iterator<int>(cout, ", "));
        cout << " but was: ";
        copy(actual.begin(), actual.end(), ostream_iterator<int>(cout, ", "));
        cout << endl;
        assert (false);
      }
    }
  }
}
