
====== Priority_Queue ======


# Priority queue

Element is popped according to its priority


# Heap

  An implementation of prioriry_queue

## property

  1. Binary tree
  2. Both the child of node is less than(or greater than) its parent
  3. push, pop operation takes O(log(n))


# Implementation with array[feel it, maxHeap]

## with an vector

  // keep the depth minimal -> height ~ O(log(n))
  // left child of heap[i] : heap[2 * i + 1]
  // right child of heap[i] : heap[2 * i + 2]
  // parent of heap[i] : heap[(i - 1) / 2] (index is rounded)
  vector<T> heap;    // in this case, maxHeap

### push an new element

  // O(log(n))
  // add at the end & adjust by comparision with it's parent
  template<T>
  void pushHeap(vector<T>& heap, T newValue) {
    heap.push_back(newValue);

    int here = heap.size() - 1;
    while( here > 0 && heap[(here - 1) / 2] < heap[here] ) {
        swap(heap[here], heap[(here - 1) / 2]);
        here = (here - 1) / 2;
    }
  }

### pop a head element

  // O(log(n))
  // move last element at the first & adjust by comparision with it's child
  template<T>
  void popHeap(vector<T>& heap) {
    if( heap.empty() ) return;

    heap[0] = heap.back();
    heap.pop_back();

    int here = 0;
    while( true ) {
      int left = here * 2 + 1;
      int right = here * 2 + 2;

      if( left >= heap.size() ) break;    // leaf case

      int next = here;
      if( heap[next] < heap[left] ) next = left;
      // if previous statement is computed, compare with previous result
      if( right < heap.size() && heap[next] < heap[right] ) next = right;

      if( next == here ) return;   // heap is alright

      swap(heap[here], heap[next]);
      here = next;
    }
  }


# STL prioriry_queue

  #include <queue>

  class cmp {
  public:
      bool operator() (const pair<int, int>& lhs, const pair<int, int>& rhs) const {
          return lhs.second < rhs.second;
      }
  };

  int main() {
    priority_queue< pair<int, int>, vector< pair<int, int> >, cmp > heap;

    heap.push(make_pair(1, 2));
    heap.push(make_pair(5, 4));
    heap.push(make_pair(3, 4));

    heap.top(); // pair<5, 4>
    heap.pop(); // pair<5, 4> is popped
  }
