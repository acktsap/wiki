# Sliding Window

- [Problems](#problems)
- [Reference](#reference)

## Concept

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

## Problems

## Reference