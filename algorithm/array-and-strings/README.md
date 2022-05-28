# Arrays and Strings

- [Two Pointer](#two-pointer)
  - [Example 1 : Two sum](#example-1--two-sum)
- [Sliding window](#sliding-window)
  - [vs Two Pointer](#vs-two-pointer)
  - [Rule of Thumb](#rule-of-thumb)
  - [Example 1 : Max subarray of length 3](#example-1--max-subarray-of-length-3)
- [Prefix Sum](#prefix-sum)
  - [Example](#example)
- [Tips](#tips)
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

## Sliding window 

- Window size : [i, j]
- Greedy하게 매번 처리되는 중복된 요소를 버리지 않고 재사용함으로써 낭비되는 계산을 하지 않음으로써 효율적으로 처리하는 방법.

### vs Two Pointer

- Sliding window는 window 안의 모든 element를 가지고 비교하지만 (eg. sum) two pointer는 각각의 pointer가 가리키고 있는 값만 비교함.

### Rule of Thumb

- State: sss
- Stretch window until xxx, change the state
- Shrink window until yyy, change the state

```java
i, j = 0
while j < s.length - 1:
  if next does not duplicate entry:
    ++j  // stretch
  if next duplicates entry && i < j:
    ++i // shrink
  if next duplicates entry && i == j:
    ++i, ++j 
  ...
```

### Example 1 : Max subarray of length 3 

```java
int sum = 0;

int i = 0;
int j = 0;
while (j < arr.length && (j - i) < 3) {
  sum += arr[j];
  ++j;
}

while (j < arr.length) {
  int next = sum + arr[j] - arr[i];
  sum = Math.max(sum, next);
  ++i;
  ++j;
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

## Tips

Always check IndexOutOfBoundException before getting it.

```java
while (index < s.length() && s.charAt(index) == ' ') {
  ++index;
}
```

Sorting is the great way to select greedily easily

## References

- [[알고리즘] 슬라이딩 윈도우 알고리즘](https://blog.fakecoding.com/archives/algorithm-slidingwindow/)

