/*
 * Descryption
 *
 * https://leetcode.com/problems/integer-to-roman/
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * 
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 
 * For example, 2 is written as II in Roman numeral, just two one's added together.
 * 12 is written as XII, which is simply X + II.
 * The number 27 is written as XXVII, which is XX + V + II.
 * 
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is written as IV.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 * 
 *     I can be placed before V (5) and X (10) to make 4 and 9. 
 *     X can be placed before L (50) and C (100) to make 40 and 90. 
 *     C can be placed before D (500) and M (1000) to make 400 and 900.
 * 
 * Given an integer, convert it to a roman numeral.
 * 
 *
 * Example 1:
 * 
 * Input: num = 3
 * Output: "III"
 * 
 * Example 2:
 * 
 * Input: num = 4
 * Output: "IV"
 * 
 * Example 3:
 * 
 * Input: num = 9
 * Output: "IX"
 * 
 * Example 4:
 * 
 * Input: num = 58
 * Output: "LVIII"
 * Explanation: L = 50, V = 5, III = 3.
 * 
 * Example 5:
 * 
 * Input: num = 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 * 
 * Constraints:
 *     1 <= num <= 3999
 * 
 *
 *
 * Approach & Proof 
 *
 * Divide & conquer 전략 + Greety algoritihm, 뺄 수 있는 것들중 최댓값으로 처리
 *
 * loop invariant
 *
 * - left : 남은 숫자
 *
 *
 * Complexity
 *
 *  - Time  : O(symbols.length*<length of num>)
 *  - Space : O(1)
 *
 *
 * Review
 *
 * 오랜만에 해보는데 생각보단 할만한 문제였다.
 * 오랜만에 하니까 이걸 리얼에서 적용할 정도로 확장성 있게 하려면 어떻게 할까 부터 생각드네.
 * 처음에 map이 생각나긴 했는데 리얼에 한다면 LinkedHashMap 정도를 적용하면 되지 않을까 싶다.
 *
 *
 * 근데 매번 루프 도는 것 보다 베스트 답안이 있네
 *
 *
 */
class IntegerToRoman {
  String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
  int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

  String intToRomanRecursion(int num) {
    for (int i = 0; i < symbols.length; ++i) {
      if (num >= values[i]) {
        return symbols[i] + intToRomanRecursion(num - values[i]);
      }
    }

    return "";
  }

  String intToRomanIterative(int num) {
    StringBuilder sb = new StringBuilder();
    int left = num;
    while (left > 0) {
      for (int i = 0; i < symbols.length; ++i) {
        int next = values[i];
        if (left >= next) {
          sb.append(symbols[i]);
          left -= next;
          break;
        }
      }
    }
    return sb.toString();
  }

  String intToRomanBest(int num) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < symbols.length; i++) {
      int next = values[i];
      while (num >= next) {
        sb.append(symbols[i]);
        num -= next;
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      { 3, "III" },
        { 4, "IV" },
        { 9, "IX" },
        { 55, "LV" },
        { 58, "LVIII" },
        { 1994, "MCMXCIV" }
    };

    IntegerToRoman solution = new IntegerToRoman();
    for (Object[] parameter : parameters) {
      int value = (int) parameter[0];
      String expected = (String) parameter[1];

      String actualByRecursion = solution.intToRomanRecursion(value);
      if (!actualByRecursion.equals(expected)) {
        throw new IllegalStateException("<actualByRecursion> Expected: " + expected +
            ", but was: " + actualByRecursion);
      }

      String actualByIterative = solution.intToRomanIterative(value);
      if (!actualByIterative.equals(expected)) {
        throw new IllegalStateException("<actualByIterative> Expected: " + expected +
            ", but was: " + actualByIterative);
      }

      String actualByBest = solution.intToRomanBest(value);
      if (!actualByBest.equals(expected)) {
        throw new IllegalStateException("<actualByBest> Expected: " + expected +
            ", but was: " + actualByBest);
      }
    }
  }
}
