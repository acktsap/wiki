# Algorithm Design

- [Algorithm Design](#algorithm-design)
  - [Rule](#rule)
  - [Modeling](#modeling)
    - [Data Structure](#data-structure)
      - [Canonicalization](#canonicalization)
      - [Separate data with an algorithm](#separate-data-with-an-algorithm)
    - [Explicit Formula (점화식)](#explicit-formula-점화식)
  - [Proving](#proving)
    - [Loop dominates Time Complexity](#loop-dominates-time-complexity)
    - [Mathematical Induction (수학적귀납법)](#mathematical-induction-수학적귀납법)
    - [Loop Invariant](#loop-invariant)
    - [Test with corner case](#test-with-corner-case)
  - [Implemenation](#implemenation)
    - [Use half-open interval in a range](#use-half-open-interval-in-a-range)
    - [Always init on declaration](#always-init-on-declaration)
    - [No floating point operation if possible](#no-floating-point-operation-if-possible)
    - [Validate function precondition](#validate-function-precondition)
  - [Debugging](#debugging)
    - [Logging middle result](#logging-middle-result)
    - [Use assert on precondition](#use-assert-on-precondition)

## Rule

What is algorithm? What is goal of computer science?\
Programming, as an enginner, is solving complexity (time, space, human).
Algorithm is to solving time, space complexity. Especialy, time completixy.

FOCUS on intention, how it's organized. It reveals computational thinking.

There are some well-known algorithm for solving problem.\
Programing is writing code. Code has some great patterns.\
Write those well-known algorithms into patterns.

Use java only. Use primitive type and array only if possible.

DO NOT make it complex. Design a simple algorithm.

## Modeling

Write it down with pseudo code 

### Data Structure

#### Canonicalization

A process for converting data that has more than one possible representation into a standard form.\
It reduces complexity by abstraction (or modeling) for computational world.

eg. 2/4, 3/6 -> 1/2

#### Separate data with an algorithm

```java
// from
String getMonthName(int month) {
  if (mount == 1) return "January";
  if (mount == 2) return "February";
  return "March";
}

// to
String[] monthName = { "January", "February", "March" };
String getMonthName(int month) {
  return monthName[month - 1];
}
```

```java
// from
void move(int direction) {
  if (direction == 1) moveLeft();
  if (direction == 2) moveRight();
}

// to
int[] movement = { -1, 1 };
void move(int direction) {
  moveTo(movement[direction - 1]);
}
```

### Explicit Formula (점화식)

- D(n) : divide
- C(n) : combine
- T(n) = D(n) + sigma[i_to_k](T(i)) + C(n)
- Time Complexity : find basic operation & compute with it

```java
// Sum until n
f(n) = f(n - 1) + n   2 <= n
f(n) = 1              1 == n

// Time complexity (basic operation : +)
T(n) = T(n - 1) + 1
     = T(n - 2) + 2
     = T(n - 3) + 3
     =    ...
     = T(1) + n - 1
     = n - 1 since T(1) = 0
```

## Proving

### Loop dominates Time Complexity

- O(n), Big-O Notation : 가장 빨리 증가하는 항만 제외하고 다 버림
- Approximately, 100_000_000 loop ~ 1 second. Not sure.

```java
// O(1) : Constant
System.out.println("Hello world");

// O(N * log(N))
Collections.sort(collection);
Arrays.sort(arr);

// O(N) : Linear
for (int i = 0; i < N; ++i) {
  // do something
}

// O(N^2)
for (int i = 0; i < N; ++i) {
  for (int j = 0; j < N; ++j) {
    // do something
  }
}
```

### Mathematical Induction (수학적귀납법)

1. Clarify the step
2. Prove an algorithm on n == 1
3. Prove that if an algorithm satisfies on n, then also n + 1

```java
// model
f(n) = 1               1 == n
f(n) = f(n - 1) + n    2 <= n

// proof
For n == 1, f(1) = 1
Assume f(n) = f(n - 1) + n for 2 <= n
Then
f(n + 1) = (f(n - 1) + n) + n + 1
         = f(n) + n + 1
```

### Loop Invariant

TODO

### Test with corner case

Just like -1, 0, 1 in `if (n <= 0) ...`

## Implemenation

### Use half-open interval in a range

`[a, b)` (a <= value && value < b)

- Easy to represent empty range with `[a, a)`
- Easy to evaluate length of range by `b - a`

### Always init on declaration

```java
// from
int[] invalid;

// to
int[] valid = new int[] { 1, 2, 3 };
```

### No floating point operation if possible

Floating point operation is slow, inaccurate

```java
// from
if (sqrt((a - b) * (a - b)) == r) {
  return true;
}

// to
if ((a - b) * (a - b) == (r * r)) {
  return true;
}
```

### Validate function precondition

- Check function parameters preconditions
- Check function preconditions before invoking other function

```java
/* precondition : node != null */
void dfs(TreeNode curr) {
  // check parameter
  if (null == curr) {
    return;
  }

  System.out.println(curr.val);

  // check precondition before invoking other function
  if (null != node.left) {
    dfs(node.left);
  }
  if (null != node.right) {
    dfs(node.leftright);
  }
}
```

## Debugging

### Logging middle result

```java
int sum = 0;
for (int i = 0; i < 3; ++i) {
  sum += i;
  System.out.format("i: %d, sum: %d%n", i, sum);
}
```

### Use assert on precondition

```cpp
void f(int next, int sum) {
  assert next < 3
}
```
