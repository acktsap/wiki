# Disjoint Set

- [What for](#what-for)
- [Adt](#adt)
  - [Adding new set](#adding-new-set)
  - [Finding set representatives](#finding-set-representatives)
    - [By recursion](#by-recursion)
    - [By iteration](#by-iteration)
    - [Path splitting](#path-splitting)
    - [Path halving](#path-halving)
  - [Merging set](#merging-set)
    - [Union by size](#union-by-size)
    - [Union by rank](#union-by-rank)
- [Time Complexity](#time-complexity)
- [Implementation](#implementation)
  - [Disjoint set forest](#disjoint-set-forest)
- [Usage](#usage)
- [Problems](#problems)
- [See also](#see-also)

## What for

- Union Find, merge–find Set 이라고도 불림.
- 서로소 집합을 저장하기 위한 자료구조.

## Adt

### Adding new set

```cpp
function MakeSet(x) is
    if x is not already in the forest then
        x.parent := x
        x.size := 1     // if nodes store size
        x.rank := 0     // if nodes store rank
    end if
end function
```

### Finding set representatives

#### By recursion

find & update parent.

```cpp
function Find(x) is
    if x.parent ≠ x then
        x.parent := Find(x.parent) // update
        return x.parent
    else
        return x
    end if
end function
```

#### By iteration

(memory reduced)

```cpp
function Find(x) is
    root := x
    while root.parent ≠ root do
        root := root.parent
    end while

    while x.parent ≠ root do
        parent := x.parent
        x.parent := root
        x := parent
    end while

    return root
end function
```

#### Path splitting

```cpp
function Find(x) is
    while x.parent ≠ x do
        tmp := x.parent
        x.parent := x.parent.parent
        x := tmp
    end while
    return x
end function
```

#### Path halving

parent 2개씩 점프

```cpp
function Find(x) is
    while x.parent ≠ x do
        x.parent := x.parent.parent
        x := x.parent
    end while
    return x
end function
```

```cpp
int findParent(vector<int>& parents, int vertex) {
  int current = vertex;
  while(parents[current] != current) {
    parents[current] = parents[parents[current]];
    current = parents[current];
  }
  return current;
}
```

### Merging set

#### Union by size

size : number of descendants (including the node itself).

```cpp
function Union(x, y) is
    // Replace nodes by roots
    x := Find(x)
    y := Find(y)

    if x = y then
        return  // x and y are already in the same set
    end if

    // If necessary, rename variables to ensure that
    // x has at least as many descendants as y
    if x.size < y.size then
        (x, y) := (y, x)
    end if

    // Make x the new root
    y.parent := x
    // Update the size of x
    x.size := x.size + y.size
end function
```

#### Union by rank

rank : an upper bound for its height (0 at first).

```cpp
function Union(x, y) is
    // Replace nodes by roots
    x := Find(x)
    y := Find(y)

    if x = y then
        return  // x and y are already in the same set
    end if

    // If necessary, rename variables to ensure that
    // x has rank at least as large as that of y
    if x.rank < y.rank then
        (x, y) := (y, x)
    end if

    // Make x the new root
    y.parent := x
    // If necessary, increment the rank of x
    if x.rank = y.rank then
        x.rank := x.rank + 1
    end if
end function
```

## Time Complexity

- Find에서 업데이트 안하기 & Union에서 관리 안하면: O(n)
- Path Compression & halving 하면 : near-constant amortized time.

## Implementation

### Disjoint set forest

- A specialized type of forest which performs unions and finds in near-constant amortized time.

## Usage

- register allocation : local variable, expression을 한정된 process register에 할당하는 과정.

## Problems

- [RedundantConnection](./leetcode/RedundantConnection.cpp)
- [NumberofOperationstoMakeNetworkConnected](./leetcode/NumberofOperationstoMakeNetworkConnected.cpp)

## See also

- [Disjoint Set (wiki)](https://en.wikipedia.org/wiki/Disjoint-set_data_structure)
- [Register allocation (wiki)](https://en.wikipedia.org/wiki/Register_allocation)
- [Find() operation for disjoint sets using "Path Halving" (stackoverflow)](https://stackoverflow.com/questions/63540010/find-operation-for-disjoint-sets-using-path-halving)
- [Learn Disjoint Set (leetcode)](https://leetcode.com/explore/learn/card/graph/618/disjoint-set/3881/)