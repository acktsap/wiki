import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/*
 * Descryption
 *
 * 소인수분해
 *
 *
 * Approach & Proof 
 *
 *
 * Complexity
 *
 *
 * Review
 *
 *
 */
class PrimeFactorization {

  public List<Integer> factor(int n) {
    List<Integer> ret = new ArrayList<>();
    ret.add(1);

    if (n == 1) {
      return ret;
    }

    int curr = n;
    int div = 2;
    while (curr != 1) {
      while (curr % div == 0) {
        ret.add(div);
        curr /= div;
      }
      div++;
    }

    return ret;
  }

  public static void main(String[] args) {
    Object[][] parameters = {
      { 1, new Integer[] { 1 } },
      { 2, new Integer[] { 1, 2 } },
      { 6, new Integer[] { 1, 2, 3 } },
      { 17, new Integer[] { 1, 17 } },
      { 24, new Integer[] { 1, 2, 2, 2, 3 } }
    };
    PrimeFactorization algo = new PrimeFactorization();
    for (Object[] parameter : parameters) {
      Integer n = (Integer) parameter[0];
      List<Integer> expected = Arrays.asList((Integer[]) parameter[1]);
      List<Integer> actual = algo.factor(n);
      assert actual.equals(expected) :  "args: " + n + ", expected: " + expected + ", actual: " + actual;
    }
  }

}
