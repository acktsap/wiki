# Arrays and Strings

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

Check out the loop invariant based on i, j

Example 1 : Two sum

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

## Tips

Always check IndexOutOfBoundException before getting it.

```java
while (index < s.length() && s.charAt(index) == ' ') {
  ++index;
}
```

