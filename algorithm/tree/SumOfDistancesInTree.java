import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/sum-of-distances-in-tree/
 *
 * An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
 * The ith edge connects nodes edges[i][0] and edges[i][1] together.
 * Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes
 *
 * Approach
 *
 * f(i, j) = 1  where i, j are has direct edge
 * f(i, j) = f(i, k) + f(k, j)  where i, k and k, j are connected respectively
 *
 * Adjacent matrix에 edges, distance저장.
 * 
 * f(i, j):
 *   if i == j -> return 0
 *   if cache[i][j] != 0 -> return cache[i][j]
 *   else -> distance = dfs(i, j); cache[i][j] = distance
 *   return cache[i][j]
 *
 * Review
 *
 * TODO: not yet passed
 */
class SumOfDistancesInTree {
  public int[] sumOfDistancesInTree(int N, int[][] edges) {
    int[] ret = new int[N];
    int[][] cache = new int[N][N];
    int[][] adjacents = buildAdjacentMatrix(N, edges);
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (i != j) {
          ret[i] += distance(adjacents, cache, i, j);
        }
      }
      for (int x = 0; x < N; ++x) {
        for (int y = 0; y < N; ++y) {
          System.out.print(cache[x][y] + " ");
        }
        System.out.println();
      }
      System.out.println();
    }
    return ret;
  }

  protected int[][] buildAdjacentMatrix(int size, int[][] edges) {
    int[][] adjacents = new int[size][size];
    for (int[] edge : edges) {
      int i = edge[0];
      int j = edge[1];
      adjacents[i][j] = 1;
      adjacents[j][i] = 1;  // undirected
    }
    return adjacents;
  }

  // dfs
  protected int distance(int[][] adjacents, int[][] cache, int i, int j) {
    if (0 != cache[i][j]) {
      return cache[i][j];
    }

    if (1 == adjacents[i][j]) {
      cache[i][j] = 1;
      cache[j][i] = 1;  // undirected
      return 1;
    }

    System.out.printf("i: %d, j: %d%n", i, j);
    int count = 1;
    for (int next = i + 1; next < adjacents.length; ++next) {
      if (1 == adjacents[i][next]) {
        count += distance(adjacents, cache, next, j);
      }
    }
    cache[i][j] = count;
    cache[j][i] = count;  // undirected
    return cache[i][j];
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        6,
        new int[][] { { 0, 1 }, { 0, 2 }, { 2, 3 }, { 2, 4 }, { 2, 5 } },
        new int[] { 8, 12, 6, 10, 10, 10 },
      },
    };
    SumOfDistancesInTree solution = new SumOfDistancesInTree();
    for (Object[] parameter : parameters) {
      int n = (int) parameter[0];
      int[][] edges = (int[][]) parameter[1];
      int[] expected = (int[]) parameter[2];
      int[] actual = solution.sumOfDistancesInTree(n, edges);
      if (!Arrays.equals(expected, actual)) {
        throw new IllegalStateException("Expected: " + Arrays.toString(expected) +
            ", but actual: " + Arrays.toString(actual));
      }
    }
  }
}
