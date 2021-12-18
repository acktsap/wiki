/*
 * Descryption
 *
 * https://leetcode.com/problems/coin-change/
 *
 * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
 * 
 * Return the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * 
 * You may assume that you have an infinite number of each kind of coin.
 * 
 * Example 1:
 * 
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * 
 * Example 2:
 * 
 * Input: coins = [2], amount = 3
 * Output: -1
 * 
 * Example 3:
 * 
 * Input: coins = [1], amount = 0
 * Output: 0
 * 
 * Constraints:
 * 
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 231 - 1
 * 0 <= amount <= 104
 *
 *
 * Approach & Proof 
 *
 *
 * Complexity
 *
 *  - Time  :
 *  - Space :
 *
 *
 * Review
 *
 *
 */
class CoinChange {
  public int coinChange(int[] coins, int amount) {
    return 0;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 5 },
        11,
        3,
      },
      {
        new int[] { 2 },
        3,
        -1,
      },
      {
        new int[] { 1 },
        0,
        0,
      },
      {
        new int[] { 186, 419, 83, 408 },
        6249,
        20,
      },
      {
        new int[] { 4 },
        3,
        -1,
      },
      {
        new int[] { 2, 4 },
        3,
        -1,
      },
    };

    var solution = new CoinChange();
    for (Object[] parameter : parameters) {
      int[] coins = (int[]) parameter[0];
      int amount = (int) parameter[1];
      int expected = (int) parameter[2];
      int actual = solution.coinChange(coins, amount);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
