import java.util.Arrays;

public class InsertionSort {

  void sort(int[] arr) {
    for (int i = 1; i < arr.length; ++i) {
      final int target = arr[i];
      int vacant = i;
      // starting from vacant - 1
      for (int j = vacant - 1; j >= 0; --j) {
        if (arr[j] < target) {
          break;
        }
        // shift arr[j] to vacant
        arr[vacant] = arr[j]; 
        vacant = j;
      }
      // insert target to vacant
      arr[vacant] = target;
    }
  }

  public static void main(String[] args) {
    final Object[][] parameters = {
      { new int[] { 2, 322, 0, 44, 100, 9, 10, 50 },
        new int[] { 0, 2, 9, 10, 44, 50, 100, 322 } },
      { new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 },
        new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } },
      { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } }
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
