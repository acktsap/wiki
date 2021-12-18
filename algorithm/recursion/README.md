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

## Tail Recursion

Tail recursion is a recursion where the recursive call is the final instruction in the recursion function.

And there should be only one recursive call in the function.

이렇게 하면 재귀를 할 때 이렇게 하면 할당된 stack을 재사용할 수 있어서 call stack chain이 생기지 않음

```java
public class Main {
  private static int helper_non_tail_recursion(int start, int [] ls) {
    if (start >= ls.length) {
      return 0;
    }
    // not a tail recursion because it does some computation after the recursive call returned.
    return ls[start] + helper_non_tail_recursion(start+1, ls);
  }

  public static int sum_non_tail_recursion(int [] ls) {
    if (ls == null || ls.length == 0) {
      return 0;
    }
    return helper_non_tail_recursion(0, ls);
  }

  //---------------------------------------------

  // 여기서 start, ls, acc는 한번 할당된 stack에 있는것을 그대로 쓰면 됨
  private static int helper_tail_recursion(int start, int [] ls, int acc) {
    if (start >= ls.length) {
      return acc;
    }
    // this is a tail recursion because the final instruction is the recursive call.
    return helper_tail_recursion(start+1, ls, acc+ls[start]);
  }
    
  public static int sum_tail_recursion(int [] ls) {
    if (ls == null || ls.length == 0) {
      return 0;
    }
    return helper_tail_recursion(0, ls, 0);
  }
}
```
