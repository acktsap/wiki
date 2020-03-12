# Recursive

## Basic

A recursive function should have the following properties so that it does not result in an infinite loop:

1. A base cases : f(i,j) = 1   where j = 1 or j = i
2. Recurrence Relation : f(i, j) = f(i − 1, j − 1) + f(i − 1, j)

## Divide & Conquer

1. Divide into several problems
2. Solve each sub-problem
3. Compute final solution using solution of sub-problems\
   Consists of "Divide", "Merge", "base case"(can be solved directly)\
   Should be represented in recursive function

### VS Brute Force

- bruce force : divide into one part & left big part
- divice & conquer : divide into sub-problem of almost same size


```java

/* recursiveSum by bruce force, takes O(n) */
int recursiveSum(int n) {
  if( n == 1 ) return 1;  // base case
  return n + recursiveSum(n - 1);
}

/*
  compute sum from 1 to n
  fastSum(n)
      = 1 + 2 + ... + n
      = (1 + 2 + ... + n/2) + {(n/2 + 1) + ... + (n/2 + n/2)}
      = (1 + 2 + ... + n/2) + (n/2)*(n/2) + (1 + 2 + ... + n/2)
      = fastSum(n/2) + (n/2)*(n/2) + fastSum(n/2)
      = 2*fastSum(n/2) + (n/2)*(n/2), n >= 2 && n % 2 == 0
  fastSum(n) = fastSum(n - 1) + n, n >= 2 & n % 2 == 1
  fastSum(n) = 1, n == 1

  O(log(n))
*/
int fastSum(const int n) {
  // base case
  if( n == 1 ) return 1;

  // divide into several problems
  if( n % 2 == 1 ) return faseSum(n - 1) + n;
  return 2 * faseSum(n / 2) + (n / 2) * (n / 2);
}
```



