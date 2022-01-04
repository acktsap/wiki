# Dynamic Programming

## Concept

- Memoization : Do not compute twice. Cache it.
- Referential transparent function : When input is same, return is same.
- Dynamic Programming : Speed up with memoization; Only possible in referential transparent function.
- Time Complexity : # of subproblem * # of repetation to solve it.

## Strategy

1. Solve with brute force using Explicit Formula (점화식).
2. Use cache to resolve duplication.

## When to use

When problem has characteristics

- Overlapping subproblems : smaller versions of the original problem that are reused multiple times.
- Optimal substructure : An optimal solution can be formed from optimal solutions to the overlapping subproblems.

Rule of Thumb

- When asking for optimum value (maximum or minimum) of something or # of ways to do something.
- Future decision depends on earlier decision.
- When brute force is too long (2^n).

## Bottom-up (Tabulation)

Use iteration.

```java
// Fibonacci example

int[] dp = new dp[n];
dp[0]= 0;
dp[1]= 1;
for (int i = 2; i < n; ++i) {
  dp[i] = d[i - 1] + dp[i - 2];
}
```

## Top-down (Memoization)

Use recursion.

```java
// Fibonacci example
int[] dp = new dp[n];
Arrays.fill(dp, -1);

void f(int n) {
  if (n == 0) {
    return 0;
  }
  if (n == 1) {
    return 1;
  }

  if (dp[n] == -1) {
    dp[n] = f(n - 1) + f(n - 2);
  }
  
  return dp[n];
}
```

## Multidimensional Example

Recursion.

```cpp
// initialized to -1
int cache[2500][2500];

int someObscureFunction(int a, int b) {
  // base case
  if (...) return ...;

  // if it's been already computed, return that value
  // int& ret : a trick
  int ret = cache[a][b];
  if (ret != -1) return ret;

  // compute the answer
  ...
  cache[a][b] = ...
  ...

  return cache[a][b];
}

int main() {
  // initialize to -1
  memset(cache, -1, sizeof(cache));
}
```

