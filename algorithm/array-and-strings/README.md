# Arrays and Strings

- [Two Pointer](#two-pointer)
  - [Example 1 : Two sum](#example-1--two-sum)
- [Prefix Sum](#prefix-sum)
  - [Example](#example)
- [Binary Search](#binary-search)
- [References](#references)

## Two Pointer

- One slow-runner and the other fast-runner.
  ```java
  // find first i
  int i = 0;
  while (i < arr.length && {some_condition}) {
    ++i;
  }

  // do logic
  int j = i + 1;
  while (j < arr.length) {
    // do something
    // no loop here if possible
  }
  ```
- One pointer starts from the beginning while the other pointer starts from the end.
  ```java
  int i = 0;
  int j = arr.length - 1;
  while (i < j) {
    // do something
    // no loop here if possible
  }
  ```
- Check out the loop invariant based on i, j

### Example 1 : Two sum

```cpp
/**
 * @param numbers Sorted positive values
 * @param target
 * @return List of index pair whose sum of each value == target
 */
vector<int> twoSum(vector<int>& numbers, int target) {
  int i = 0
  int j = number.size() - 1;
  while (i < j) {
    if (numbers[i] + numbers[j] < target) --j;
    else if (numbers[i] + numbers[j] > target) ++i;
    else return vector<int>{ i, j };
  }

  return vector<int>();
}
```

## Prefix Sum

- Use cumulative sum of an array.

### Example

```java
int[] nums = new int[] { 1, 0, 3, 2, 5 };
int[] prefixSum = new int[nums.length + 1]; // trick: put 0 to 0-th entry
for (int i = 1; i < prefixSum.length; ++i) {
  prefixSum[i] = nums[i - 1] + prefixSum[i - 1];
}
```

## Binary Search

- 이미 **정렬 되어 있는** 데이터를 반씩 탐색해서 원하는 값이 있는지 찾아내는 기법.
- Time: O(log(n))

```cpp
bool searchRecursively(vector<int> nums, int from, int to, int target) {
  if (from > to) {
    return false;
  }

  int mid = (from + to) / 2;
  if (nums[mid] == target) {
    return true;
  } else if (nums[mid] < target) {
    return search(nums, mid + 1, to, target);
  } else { // nums[mid] > target
    return search(nums, from, mid - 1, target);
  }
}

bool searchIteratively(vector<int> nums, int from, int to, int target) {
  int start = from;
  int end = to;
  while (start <= end) {
    int mid = (start + end) / 2;
    if (nums[mid] == target) {
      return true;
    } else if (nums[mid] < target) {
      start = mid + 1;
    } else { // nums[mid] > target
      end = mid - 1;
    }
  }

  return false;
}
```

## References

-
