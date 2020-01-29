# Sorting Algorithm

- Strategy
- Average Time Complexity
- Worst Time Complexity
- Space Complexity
- Pointer

- [Sorting Algorithm](#sorting-algorithm)
  - [Insertion Sort](#insertion-sort)
  - [Quick Sort](#quick-sort)
  - [Merge Sort](#merge-sort)
  - [Heap Sort](#heap-sort)
    - [Heap Structrue](#heap-structrue)
      - [Insertion](#insertion)
      - [Deletion](#deletion)
    - [Heap Sort Strategy](#heap-sort-strategy)
  - [Shell Sort](#shell-sort)
  - [Bucket Sort](#bucket-sort)
  - [Radix Sort](#radix-sort)
  - [References](#references)

## Insertion Sort

![insertion-sort](./img/insertion-sort.png)

- Average 
  - A(n) = n^2 / 4 ~ O(n^2)
- Worst
  - Keys are in reverse order. Have to complare all of keys before current target.
  - W(n) = 1 + 2 + 3 + ... + (n - 1) = n*(n - 1) / 2 ~ O(n^2)
- Space : in place
- Single pointer

## Quick Sort

## Merge Sort

## Heap Sort

### Heap Structrue

![heap-structure](./img/heap-structure.png)

Heap (Left-Complete Binary Tree)

1. h - 1 depth까지 complete
2. leaf는 h나 h - 1의 depth에 있음
2. h depth의 leaf는 왼쪽에 몰려 있음

- Partial Order Tree : 각 node가 가지의 children보다 크거나 같은 (또는 작거나 같은) Tree. Heap은 이것임
- 구현 : array로 할 수 있음. `i*2 + 1` 가 left child, `i*2 + 2` 가 right child, `(i - 1) / 2`가 parent가 됨

#### Insertion

1. 힙에 새로운 요소가 들어오면, 일단 새로운 노드를 힙의 마지막 노드에 이어서 삽입한다.
2. Heapfiy

![heap-structure-insert](./img/heap-structure-insert.png)

#### Deletion

1. 최대 힙에서 최댓값은 루트 노드이므로 루트 노드가 삭제된다.
2. 삭제된 루트 노드에는 힙의 마지막 노드를 가져온다.
3. Heapfiy

![heap-structure-delete](./img/heap-structure-delete.png)

### Heap Sort Strategy

1. Construct heap
2. for (i = arr.length - 1; i > 0; --i)
  - Swap(arr[1], arr[i]) // arr[1] holds larger one, now E[i], E[i + 1], ... E[n - 1] are sorted
  - Heapify(arr) // arr[1] may not larger one

- Average
  - Heapfiy time is log(n). Repeat n times ~ O(n*log(n))
- Worst : ?
- Space : In-place and Depth of recursion is up to lg(n) ~ O(log(n))
- Two pointer holds curr and parent on heapify

## Shell Sort

## Bucket Sort

## Radix Sort

## References

- https://gmlwjd9405.github.io/2018/05/06/algorithm-insertion-sort.html
- https://gmlwjd9405.github.io/2018/05/10/algorithm-heap-sort.html