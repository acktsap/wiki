import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

// 소인수분해
public class PrimeFactorization {

  List<Integer> factor(final int n) {
    final List<Integer> ret = new ArrayList<>();

    // exceptional case
    if (n == 1) {
      ret.add(1);
      return ret;
    }

    int div = 2;
    int left = n;
    while (left != 1 && div <= n) {
      while (left % div == 0) {
        ret.add(div);
        left /= div;
      }
      div++;
    }

    return ret;
  }

  public static void main(String[] args) {
    final Object[][] parameters = {
      { 1, new Integer[] { 1 } },
      { 2, new Integer[] { 2 } },
      { 6, new Integer[] { 2, 3 } },
      { 17, new Integer[] { 17 } },
      { 24, new Integer[] { 2, 2, 2, 3 } }
    };
    for (Object[] parameter : parameters) {
      final Integer n = (Integer) parameter[0];
      final List<Integer> expected = Arrays.asList((Integer[]) parameter[1]);
      final List<Integer> actual = new PrimeFactorization().factor(n);
      assert actual.equals(expected) :  "args: " + n + ", expected: " + expected + ", actual: " + actual;
    }
  }

}
