import java.util.Arrays;

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
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 104
 *
 *
 * Approach & Proof 
 *
 * Let m be # of coins and f(n) be best solution for amount n. Then,
 *
 * f(n) = 1 + min(f(n - coins[0]), f(n - coins[1]), ..., f(n - coins[m - 1]))  for there exists (n - coins[i]) != -1
 *      = -1     for there not exists (n - coins[i]) != -1
 *      = 0      for n == 0
 *      = -1     for n < 0
 *
 * todo
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
    int[] cache = new int[amount + 1];
    Arrays.fill(cache, -1);
    cache[0] = 0;
    return compute(cache, coins, amount);
  }
  
  protected int compute(int[] cache, int[] coins, int amount) {
    if (amount < 0) {
      return -1;
    }
    
    // cached (dp)
    if (cache[amount] != -1) {
      return cache[amount];
    }

    int min = Integer.MAX_VALUE;
    for (int coin : coins) {
      int candidate = compute(cache, coins, amount - coin);
      if (candidate != -1) {
        min = Math.min(min, candidate);
      }
    }
    
    // not found
    if (min == Integer.MAX_VALUE) {
      return -1;
    }
    
    cache[amount] = 1 + min;
    
    return cache[amount];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 5 },
        11,
        3,
      },
      {
        new int[] { 2, 5 },
        11,
        4,
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
