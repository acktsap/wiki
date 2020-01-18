import java.util.Arrays;

// Average () : n^2 / 4 ~ O(n^2) 
// Worst () : n*(n - 1) / 2 ~ O(n^2)
// space : in place
public class InsertionSort {

  private interface Sort {
    void sort(int[] E);
  }

  private static class InsertionSortImpl implements Sort {

    @Override
    public void sort(int[] E) {
      insertionSort(E, E.length);
    }

    protected void insertionSort(int[] E, int n) {
      for (int xindex = 1; xindex < n; ++xindex) {
        int curr = E[xindex];
        int key = curr;
        int xLoc = shiftVac(E, xindex, key);
        E[xLoc] = curr;
      }
    }
    
    protected int shiftVac(int[] E, int xindex, int key) {
      int vacant = xindex;
      int xLoc = 0;    // assume failure
      while (vacant > 0) {
        if (E[vacant - 1] <= key) {    // < : x, <= : o
          xLoc = vacant; 
          break;
        }
        E[vacant] = E[vacant - 1];
        --vacant;
      }
      return xLoc;
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

    final Sort sort = new InsertionSortImpl();
    for (final Object[] parameter : parameters) {
      final int[] input = (int[]) parameter[0];
      final int[] expected = (int[]) parameter[1];
      sort.sort(input);
      assert Arrays.equals(expected, input);
    }
  }

}
