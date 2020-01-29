import java.util.Arrays;

public class InsertionSort {

  void sort(int[] arr) {
    for (int i = 1; i < arr.length; ++i) {
      int keep = arr[i];
      int vacant = i;
      while (0 < vacant && arr[vacant - 1] >= keep) {
        // shirt vacant
        arr[vacant] = arr[vacant - 1];
        --vacant;
      }
      arr[vacant] = keep;
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

    final InsertionSort sort = new InsertionSort();
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
