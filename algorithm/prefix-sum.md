# Arrays and Strings

- [Prefix Sum](#prefix-sum)
  - [Example](#example)
- [Problem](#problem)
- [References](#references)

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

## Problem

todo

## References

-
