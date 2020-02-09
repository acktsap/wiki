import java.util.Arrays;

public class Sortings {

  interface Sort {
    void sort(int[] arr);
  }

  /* 4 simple algorithms */

  // O(n) on best (loop only for statement)
  // O(n^2) on worst (1 + 2 + 3 + 4 + ... + n - 1 ~ O(N^2))
  static class InsertionSort implements Sort {
    public void sort(int[] arr) {
      // 1. for all 1 ... arr.length - 1
      for (int i = 1; i < arr.length; ++i) {
        int keep = arr[i];
        int vacant = i;
        while (0 <  vacant && arr[vacant - 1] > keep) {
          // 2. shift vacant
          arr[vacant] = arr[vacant - 1];
          vacant = vacant - 1;
        }
        // 3. insert to vacant
        arr[vacant] = keep;
      }
    }
  }

  // O(n^2) on both best, worst
  static class SelectionSort implements Sort {
    public void sort(int[] arr) {
      int tmp = 0;
      // 1. for all 0 ... arr.length - 1
      for (int i = 0; i < arr.length - 1; ++i) {
        // 2. find min
        int min = i;
        for (int j = i + 1; j < arr.length; ++j) {
          if (arr[j] < arr[min]) {
            min = j;
          }
        }
        // 3. swap min
        if (min != i) {
          tmp = arr[i];
          arr[i] = arr[min];
          arr[min] = tmp;
        }
      }
    }
  }

  // O(n^2) on both best, worst
  static class BubbleSort implements Sort {
    public void sort(int[] arr) {
      int tmp = 0;
      for (int i = arr.length - 1; i > 0; --i) {
        for (int j = 0; j < i; ++j) {
          if (arr[j] > arr[j + 1]) {
            tmp = arr[j + 1];
            arr[j + 1] = arr[j];
            arr[j] = tmp;
          }
        }
      }
    }
  }

  // O(nlog(n)) on best
  // O(n^2) on worst
  static class ShellSort implements Sort {
    public void sort(int[] arr) {
      for (int gap = arr.length / 2; gap > 0; gap /= 2) {
        // odd gap is better
        if (gap % 2 == 0) {
          ++gap;
        }
        // for all sublists
        for (int i = 0; i < gap; ++i) {
          // insertion sort
          for (int j = i + gap; j < arr.length; j += gap) {
            int keep = arr[j];
            int vacant = j;
            while (0 <= (vacant - gap) && arr[vacant - gap] > keep) {
              arr[vacant] = arr[vacant - gap];
              vacant = vacant - gap;
            }
            arr[vacant] = keep;
          }
        }
      }
    }
  }


  /* 3 hard algorithms */

  // O(nlog(n)) on best, worst case
  static class MergeSort implements Sort {
    protected int[] tmp;

    public synchronized void sort(int[] arr) {
      this.tmp = new int[arr.length];
      sort(arr, 0, arr.length - 1);
    }

    protected void sort(int[] arr, int start, int end) {
      if (start < end) {
        int mid = (start + end) / 2;
        // divive
        sort(arr, start, mid);
        sort(arr, mid + 1, end);
        // conquer
        merge(arr, start, mid, end);
      }
    }

    protected void merge(int[] arr, int start, int mid, int end) {
      int curr = start;
      int left = start;
      int right = mid + 1;
      // compare & copy to temp
      while (left <= mid && right <= end) {
        if (arr[left] < arr[right]) {
          tmp[curr] = arr[left];
          ++left;
        } else {
          tmp[curr] = arr[right];
          ++right;
        }
        ++curr;
      }
      // if left is left
      while (left <= mid) {
        tmp[curr] = arr[left];
        ++curr;
        ++left;
      }
      // if right is left
      while (right <= end) {
        tmp[curr] = arr[right];
        ++curr;
        ++right;
      }
      // copy from temp
      for (int i = start; i <= end; ++i) {
        arr[i] = tmp[i];
      }
    }
  }

  static class HeapSort implements Sort {
    public void sort(int[] arr) {
      // construct heap
      for (int i = arr.length / 2 - 1; i >= 0; --i) {
        heapify(arr, arr.length, i);
      }

      // max to last
      for (int i = arr.length - 1; i > 0; --i) {
        int tmp = arr[0];
        arr[0] = arr[i];
        arr[i] = tmp;
        heapify(arr, i, 0);
      }
    }

    protected void heapify(int[] arr, int size, int target) {
      int largest = target;
      int left = 2 * largest + 1;
      int right = 2 * largest + 2;

      if (left < size && arr[left] > arr[largest]) {
        largest = left;
      }
      if (right < size && arr[right] > arr[largest]) {
        largest = right;
      }

      if (largest != target) {
        int tmp = arr[target];
        arr[target] = arr[largest];
        arr[largest] = tmp;
        heapify(arr, size, largest);
      }
    }
  }


  /* main */

  public static void main(String[] args) {
    final Sort[] sorts = {
      new InsertionSort(),
      new SelectionSort(),
      new BubbleSort(),
      new ShellSort(),
      new MergeSort(),
      new HeapSort(),
    };
    Arrays.asList(sorts).stream().forEach(s -> {
      final Object[][] parameters = {
        { new int[] { 2, 322, 0, 44, 100, 9, 10, 50 },
          new int[] { 0, 2, 9, 10, 44, 50, 100, 322 } },
        { new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 },
          new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } },
        { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
          new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } },
        { new int[] { 1, 5, 3, 5, 5, 6, 7, 4, 9 },
          new int[] { 1, 3, 4, 5, 5, 5, 6, 7, 9 } }
      };
      for (final Object[] parameter : parameters) {
        final int[] input = (int[]) parameter[0];
        final int[] expected = (int[]) parameter[1];
        s.sort(input);
        if (!Arrays.equals(expected, input)) {
          throw new IllegalStateException("expected: " + Arrays.toString(expected) +
              ", actual: " + Arrays.toString(input) + "(Algorithm: " + s.getClass() + ")");
        }
      }
    });
  }

}
