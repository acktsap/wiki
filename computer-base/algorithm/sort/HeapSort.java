import java.util.Arrays;

public class HeapSort {

  void sort(int[] arr) {
    for (int i = 1; i < arr.length; ++i) {
      insertHeap(arr, i);
    }
    int tmp = 0;
    for (int i = arr.length - 1; i > 0; --i) {
      // swap top with bottom
      tmp = arr[i];
      arr[i] = arr[0];
      arr[0] = tmp;
      heapify(arr, i - 1);
    }
  }

  // last에 해당하는 index를 이미 정돈된 heap에 집어넣는다
  void insertHeap(int[] arr, int last) {
    int tmp = 0;
    int curr = last;
    int parent = (curr - 1) / 2;
    // curr == 0 -> it's on top
    while (0 < curr && arr[parent] < arr[curr]) {
      // swap value
      tmp = arr[parent]
      arr[parent] = arr[curr];
      arr[curr] = tmp;
      // up pointer
      curr = parent;
      parent = (curr - 1) / 2;
    }
  }

  // index 0이 이상한 녀석임 거기서 시작해서 heap을 정돈한다
  void heapify(int[] arr, int last) {
    int curr = 0;
    while (curr < last) {
      // left child
      int left = 2 * curr + 1;
      int right = 2 * curr + 2;
      if (arr[curr] < arr[left]) {
        curr = left;
      } else if (arr[curr] < arr[right]) {
        curr = right;
      } else {
        break;
      }
    }
  }
  
  public static void main(String[] args) {
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

    final HeapSort sort = new HeapSort();
    for (final Object[] parameter : parameters) {
      final int[] input = (int[]) parameter[0];
      final int[] expected = (int[]) parameter[1];
      sort.sort(input);
      if (!Arrays.equals(expected, input)) {
        throw new IllegalStateException("expected: " + Arrays.toString(expected) +
            ", actual: " + Arrays.toString(input));
      }
    }
  }

}
