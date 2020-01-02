# Base patterns

## Solving

1. Understand carefully
2. Modeling (including data structure, solving strategy)
3. Validate it
4. Test with corner case (like -1, 0, 1)

## Data structure

### Canonicalization

A process for converting data that has more than one possible representation into a standard form.\
It reduces complexity by abstraction (or modeling) for computational world.

eg. 2/4, 3/6 -> 1/2

### Separate data with an algorithm

```cpp
// from
string getMonthName(int month) {
  if (mount == 1) return "January";
  if (mount == 2) return "February";
  if (mount == 3) return "March";
  return "December";
}

// to
string monthName[] = { "January", "February", "March", "December" };
string getMonthName(int month) {
  return monthName[month - 1];
}

// from
void move(int direction) {
  if (direction == 1) moveLeft();
  if (direction == 2) moveRight();
}

// to
int movement[] = { -1, 1 };
void move(int direction) {
  moveTo(movement[direction - 1]);
}
```

## Writing

### Use half-open interval in a range

`[a, b)` (a <= value && value < b)

- Easy to represent empty range with `[a, a)`
- Easy to evaluate length of range by `b - a`

### Always init on declaration

```cpp
// from
int arr[];

// to
int arr[] = { 1, 2, 3 };
```

### No floating point operation if possible

Floating point operation is slow, inaccurate

```cpp
// from
sqrt((a - b) * (a - b)) == r

// to
(a - b) * (a - b) == (r * r)
```

## Debugging

### Logging middle result

```cpp
int sum = 0;
for (int i = 0; i < 3; ++i) {
  sum += i;
  printf("i: %d, sum: %d\n", i, sum);
}
```

### Use assert on precondition

```cpp
#include <assert.h>

void f (int next, int sum) {
  assert (next < 3)
}
```
