import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/gas-station/
 *
 * There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
 * 
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station.
 * You begin the journey with an empty tank at one of the gas stations.
 * 
 * Given two integer arrays gas and cost,
 * return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.
 *
 * If there exists a solution, it is guaranteed to be unique
 * 
 * 
 * Example 1:
 * 
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * Output: 3
 * Explanation:
 * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 4. Your tank = 4 - 1 + 5 = 8
 * Travel to station 0. Your tank = 8 - 2 + 1 = 7
 * Travel to station 1. Your tank = 7 - 3 + 2 = 6
 * Travel to station 2. Your tank = 6 - 4 + 3 = 5
 * Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
 * Therefore, return 3 as the starting index.
 *
 * Example 2:
 * 
 * Input: gas = [2,3,4], cost = [3,4,3]
 * Output: -1
 * Explanation:
 * You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
 * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 0. Your tank = 4 - 3 + 2 = 3
 * Travel to station 1. Your tank = 3 - 3 + 3 = 3
 * You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
 * Therefore, you can't travel around the circuit once no matter where you start.
 *  
 * 
 * Constraints:
 * 
 * - gas.length == n
 * - cost.length == n
 * - 1 <= n <= 105
 * - 0 <= gas[i], cost[i] <= 104
 *
 *
 *
 * Approach & Proof 
 *
 * 1. Backtracking & Brute force
 *
 * for all i
 *   if gas[i] < cost[i]:
 *     skip
 *   else:
 *     <calcualte for cycle>
 *     loop invariant
 *      - remain : holds remaining gas until to before filling gas at i
 *
 * completixy
 *
 * - Time  : O(n^2)
 * - Space : O(1)
 *
 *
 *
 * Review
 *
 *
 */
class GasStation {
  public int canCompleteCircuitBruteForce(int[] gas, int[] cost) {
    for (int i = 0; i < gas.length; ++i) {
      if (cost[i] <= gas[i]) {
        int remain = gas[i] - cost[i];
        int j = i + 1;
        while (j < gas.length && cost[j] <= remain + gas[j]) {
          remain = remain + gas[j] - cost[j];
          ++j;
        }
        
        if (j != gas.length) {
          continue;
        }
        
        j = 0;
        while (j < i && cost[j] <= remain + gas[j]) {
          remain = remain + gas[j] - cost[j];
          ++j;
        }
        
        if (j == i) {
          return i;
        }
      }
    }
    
    return -1;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[] { 1, 2, 3, 4, 5 },
        new int[] { 3, 4, 5, 1, 2 },
        3,
      },
      {
        new int[] { 2, 3, 4 },
        new int[] { 3, 4, 3 },
        -1,
      },
    };

    var solution = new GasStation();
    for (Object[] parameter : parameters) {
      int[] gas = (int[]) parameter[0];
      int[] cost = (int[]) parameter[1];
      int expected = (int) parameter[2];

      {
        int actual = solution.canCompleteCircuitBruteForce(gas, cost);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
