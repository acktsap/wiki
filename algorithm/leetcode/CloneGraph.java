import java.util.Objects;

/*
 * Descryption
 *
 * https://leetcode.com/problems/clone-graph/
 *
 * Given a reference of a node in a connected undirected graph.
 * 
 * Return a deep copy (clone) of the graph.
 * 
 * Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.
 * 
 * class Node {
 *     public int val;
 *     public List<Node> neighbors;
 * }
 *  
 * 
 * Test case format:
 * 
 * For simplicity, each node's value is the same as the node's index (1-indexed).
 * For example, the first node with val == 1, the second node with val == 2, and so on. The graph is represented in the test case using an adjacency list.
 * 
 * An adjacency list is a collection of unordered lists used to represent a finite graph.
 * Each list describes the set of neighbors of a node in the graph.
 * 
 * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
 *
 *
 * Example 1
 *
 * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
 * Output: [[2,4],[1,3],[2,4],[1,3]]
 *
 * Example 2 (only one node)
 *
 * Input: adjList = [[]]
 * Output: [[]]
 *
 * Example 3
 *
 * Input: adjList = []
 * Output: []
 *
 *
 *
 * Approach & Proof 
 *
 * 1. xxx approach
 *
 * Let a = 1
 * ...
 * 
 * proof
 *
 * completixy
 *
 * - Time  :
 * - Space :
 *
 *
 *
 * Review
 *
 *
 */
class CloneGraph {
  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new char[][] {
                       { '1', '1', '1', '1', '0' },
                       { '1', '1', '0', '1', '0' },
                       { '1', '1', '0', '0', '0' },
                       { '0', '0', '0', '0', '0' },
        },
        1
      },
    };

    var solution = new Template();
    for (Object[] parameter : parameters) {
      char[][] grid = (char[][]) parameter[0];
      int expected = (int) parameter[1];

      {
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
    }
  }
}
