import java.lang.StringBuilder;

/*
 * Descryption
 *
 * n을 받아서 369게임에 맞게 출력. 짝은 '-'로 출력.
 *
 * Approach & Proof 
 *
 * n을 10으로 나머지 연산하고 해당 자리 처리
 * n /= 10하고 n이 0일때 까지 반복
 *
 * Complexity
 *
 *  - Time  : O(1)
 *  - Space : O(1)
 *
 * Review
 *
 * 0에 대해서 처리를 처음에 못함. 0 % 3도 0이거늘. 역시 테스트 케이스를 잘 작성해야해
 */
class ThreeSixNineGame {
  public String game(final int n) {
    final StringBuilder sb = new StringBuilder();
    int left = n;
    while (0 != left) {
      final int curr = left % 10;
      if (0 != curr && curr % 3 == 0) {
        sb.append('-');
      }
      left /= 10;
    }
    return sb.length() == 0 ? Integer.toString(n) : sb.toString();
  }

  public static void main(final String[] args) {
    final Object[][] parameters = new Object[][] {
      { 0, "0" },
      { 1, "1" },
      { 13, "-" },
      { 16, "-" },
      { 19, "-" },
      { 20, "20" },
      { 30, "-" },
      { 333, "---" },
      { 369, "---" },
      { 361_234_369, "------" },
      { 13_006_905, "---" },
      { 2_147_483_647, "--" },
    };
    final ThreeSixNineGame solution = new ThreeSixNineGame();
    for (final Object[] parameter : parameters) {
      final int input = (int) parameter[0];
      final String expected = (String) parameter[1];
      final String actual = solution.game(input);
      if (!expected.equals(actual)) {
        throw new IllegalStateException(String.format("Expected: %d, but actual: %d (input: %d)", expected, actual, input));
      }
    }
  }
}
