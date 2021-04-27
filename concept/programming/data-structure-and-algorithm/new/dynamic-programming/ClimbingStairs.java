import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/climbing-stairs/
 *
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Note: Given n will be a positive integer.
 *
 * Example 1:
 * 
 * Input: 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 *   1. 1 step + 1 step
 *   2. 2 steps
 * 
 * Example 2:
 * 
 * Input: 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 *   1. 1 step + 1 step + 1 step
 *   2. 1 step + 2 steps
 *   3. 2 steps + 1 step
 * 
 *
 * Approach & Proof 
 *
 * f(1) = 1
 * f(2) = 2
 * f(n) = f(n - 1) + f(n - 2)
 *      = (first is 1 plus left n - 1) + (first is 2 plus left n - 2)
 *
 *
 * Complexity
 *
 *  - Time  : O(n)
 *  - Space : O(n)
 *
 *
 * Review
 * 
 * Arrays.fill(cache, -1); 이렇게 쓰자
 *
 * 처음에 step 1, 2밖에 없는거 모르고 다르게 풀음
 * step 전부 다 가능한줄...
 * 문제를 역시 잘 읽어봐야 해
 * 
 * 이거 그냥 피보나치라서 직전 2개만 저장해도 되는데 걍 이렇게 함
 *
 */
class ClimbingStairs {
  public int climbStairs(int n) {
    int[] cache = new int[n + 1];
    Arrays.fill(cache, -1);
    return calculate(n, cache);
  }

  protected int calculate(int n, int[] cache) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }

    if (cache[n] != -1) {
      return cache[n];
    }

    cache[n] = calculate(n - 1, cache) + calculate(n - 2, cache);
    return cache[n];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      { 2, 2 },
      { 3, 3 },
      { 5, 8 },
      { 40, 165580141 },
    };
    ClimbingStairs solution = new ClimbingStairs();
    for (Object[] parameter : parameters) {
      int input = (int) parameter[0];
      int expected = (int) parameter[1];
      int actual = solution.climbStairs(input);
      if (expected != actual) {
        throw new IllegalStateException("Expected: " + expected +
            ", but was: " + actual);
      }
    }
  }
}
