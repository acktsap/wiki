# Dynamic Programming

- [Terms](#terms)
- [Concept](#concept)
  - [Characteristics](#characteristics)
    - [When to use](#when-to-use)
  - [Framework](#framework)
- [Bottom-up (Tabulation)](#bottom-up-tabulation)
- [Top-down (Memoization) : Prefer it](#top-down-memoization--prefer-it)
- [Multidimensional](#multidimensional)
- [Reference](#reference)

## Terms

- Memoization : Do not compute twice. Cache it.
- Referential transparent function : When input is same, return is same.

## Concept

- Dynamic Programming : Speed up by using cache of sub-problem; Only possible in referential transparent function.
- Time Complexity : the number of of subproblem * the number of of repetation to solve it.

### Characteristics

- Overlapping subproblems
  - Smaller versions of the original problem that are reused multiple times.
- Optimal substructure
  - An optimal solution can be formed from optimal solutions to the overlapping subproblems.

#### When to use

- When asking for optimum value (maximum or minimum) of something or # of ways to do something.
- Future decision depends on earlier decision.
  - Greedy의 경우 이 조건이 만족못함. dp인지 확인해보기 위해 greedy를 가정하고 greedy에 대한 counterexample을 들어봐라.
- When brute force is too long (2^n).

### Framework

- A data structure and a way of visiting each DP state.
- The recurrence relation (Explicit Formula ,점화식).
- The base cases.

## Bottom-up (Tabulation)

- Use iteration.
- Pros
  - Faster then memoization (no recursion overhead).
- Cons
  - Should set logical ordering of sub-problem.

```java
/*
  Fibonacci example

  dp[i] = dp[i - 1] + dp[i]     n >= 2
          1                     n == 1
          0                     n == 0
*/

int[] dp = new dp[n];
dp[0]= 0;
dp[1]= 1;
for (int i = 2; i < n; ++i) {
  dp[i] = d[i - 1] + dp[i - 2];
}
```

## Top-down (Memoization) : Prefer it

- Use recursion.
- Pros
  - No need to order sub-problem.
- Cons
  - Recursion overhead.

```java
/*
  Fibonacci example

  dp[i] = dp[i - 1] + dp[i]     n >= 2
          1                     n == 1
          0                     n == 0
*/

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

## Multidimensional

- Multiple dimension state.

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

## Reference

- leetcode
- [dynamic-programming-vs-memoization](https://cs.stackexchange.com/questions/99513/dynamic-programming-vs-memoization/99517)
