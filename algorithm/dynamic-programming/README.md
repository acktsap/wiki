# Dynamic Programming

## Concept

- Memoization : do not compute twice. save & use it
- Referential transparent function : when input is same, return is same
- Dynamic Programming : speed up with memoization; only applicable to referential transparent function
- Time Complexity : # of subproblem * # of repetation to solve it

## Strategy

1. Solve with exhausive Search
2. use memoization to resolve duplication

## Recursive Pattern

```cpp
// initialized to -1
int cache[2500][2500];

int someObscureFunction(int a, int b) {
  // base case
  if( ... ) return ...;

  // if it's been already computed, return that value
  // int& ret : a trick
  int& ret = cache[a][b];
  if( ret != -1 ) return ret;

  // compute the answer
  ...

  return ret;
}

int main() {
  // initialize to -1
  memset(cache, -1, sizeof(cache));
}
```

## Iterative Pattern

1. Find a solution using exhausive search
2. Memoization on duplication
