import java.util.Arrays;

 // 1. Pivot을 선정
 // 2. Pivot 을 기준으로 작으면 왼쪽, 크면 오른쪽
 // 3. 정렬이 될 때 까지 구별한 왼쪽과 오른쪽으 나누어서 분할처리
public class QuickSort {

  void sort(int[] E) {
    quickSort(E, 0, E.length);
  }

  void quickSort(int[] E, int first, int last) {
    if (first < last) {    // same : no comparison
      int pivot = E[first];
      int splitPoint = partition(E, pivot, first, last);
      E[splitPoint] = pivot;
      quickSort(E, first, splitPoint - 1);
      quickSort(E, splitPoint + 1, last);
    }
  }

  int partition(int[] E, int pivot, int first, int last) {
    int low = first;
    int high = last;
    while (low < high) {    // same : no comparison
      int highVac = extendLargeRegion(E, pivot, low, high);
      int lowVac = extendSmallRegion(E, pivot, low + 1, highVac);
      low = lowVac + 1; high = highVac;
    }
    return low;     // splitPoint
  }

  int extendLargeRegion(int[] E, int pivot, int lowVac, int high) {
    int curr, highVac;
    curr = high;
    highVac = lowVac;    // assume failure
    while (curr > lowVac) {
      if (E[curr] < pivot) {
        E[lowVac] = E[curr];
        highVac = curr;
        break;
      }
      curr--;
    }
    return highVac;
  }

  int extendSmallRegion(int[] E, int pivot, int low, int highVac) {
      int curr, lowVac;
      curr = low;
      lowVac = highVac;    // assume failure
      while (curr < highVac) {
          if (E[curr] >= pivot) {
              E[highVac] = E[curr];
              lowVac = curr;
              break;
          }
          curr++;
      }
      return lowVac;
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

    final QuickSort sort = new QuickSort();
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
