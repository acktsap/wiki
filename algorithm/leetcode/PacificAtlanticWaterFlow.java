import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Descryption
 *
 * https://leetcode.com/problems/pacific-atlantic-water-flow/
 *
 * There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean.
 * The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.
 * 
 * The island is partitioned into a grid of square cells.
 * You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c).
 * 
 * The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west
 * if the neighboring cell's height is less than or equal to the current cell's height.
 * Water can flow from any cell adjacent to an ocean into the ocean.
 * 
 * Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes
 * that rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.
 * 
 * 
 * Example 1:
 * 
 * Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
 * Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
 *
 * Example 2:
 * 
 * Input: heights = [[2,1],[1,2]]
 * Output: [[0,0],[0,1],[1,0],[1,1]]
 *  
 * 
 * Constraints:
 * 
 * - m == heights.length
 * - n == heights[r].length
 * - 1 <= m, n <= 200
 * - 0 <= heights[r][c] <= 10^5
 *
 *
 *
 * Review
 *
 * dfs 감을 좀 잡아야 할 듯
 *
 *
 */
class PacificAtlanticWaterFlow {
  int[] dx = { 1, -1, 0, 0 };
  int[] dy = { 0, 0, 1, -1 };

  /*
    table에 score를 넣어두고 갈 수 있는지만 미리 다채워두면 되지 않을까?
    한 entry의 score는 인접한거에서 알 수 있으니까
    1111 4 bit 써서 앞에 두개는 pacific 가능,불가능 / 뒤에 두개는 atlantic 가능,불가능
    pacific: 8(1000), 4(100), atlantic: 2(10), 1(1)
    
    0x0c : 1100
    0x08 : 1000
    0x04 : 0100
    
    0x03 : 0011
    0x02 : 0010
    0x01 : 0001
    
    umm.. 두개를 동시에하려고 하니까 잘 안되네 그냥 각각 돌리자.
    아니야 visited가 문제였어..
    
    .. 개뻘짓거리 말고 단순하게 가자.
    에초에 접근을 양쪽 끝에서부터 하면 괜찮지 않을까?
    
    dfs 기준 : 어디까지 전파될 수 있을까?
    
    - time: O(2*n*m)
    - space: O(2*n*m)
  */
  public List<List<Integer>> pacificAtlanticDfs(int[][] heights) {
    List<List<Integer>> ret = new ArrayList<>();
    
    boolean[][] canPacific = new boolean[heights.length][heights[0].length];
    boolean[][] canAtlantic = new boolean[heights.length][heights[0].length];
      
    int[][] scores = new int[heights.length][heights[0].length];
    for (int i = 0; i < heights.length; ++i) {
      dfs(heights, i, 0, canPacific);
      dfs(heights, i, heights[0].length - 1, canAtlantic);
    }
    
    for (int j = 0; j < heights[0].length; ++j) {
      dfs(heights, 0, j, canPacific);
      dfs(heights, heights.length - 1, j, canAtlantic);
    }
    
    for (int i = 0; i < heights.length; ++i) {
      for (int j = 0; j < heights[0].length; ++j) {
        if (canPacific[i][j] && canAtlantic[i][j]) {
          ret.add(List.of(i, j));
        }
      }
    }
    
    return ret;
  }
  
  protected void dfs(int[][] heights, int i, int j, boolean[][] canReach) {
    // check visited
    if (canReach[i][j]) {
      return;
    }
      
    canReach[i][j] = true;
    
    for (int k = 0; k < 4; ++k) {
      int x = i + dx[k];
      int y = j + dy[k];
      
      if (x < 0 || x >= heights.length || y < 0 || y >= heights[0].length) {
        continue;
      }
      
      if (heights[i][j] > heights[x][y]) {
        continue;
      }
      
      dfs(heights, x, y, canReach);
    }
  }
  
  /*
    bfs로 해볼까 했는데
    문제 이해를 잘못했다.. 시작 높이보다 낮은거면 전부 갈 수 있는지 알았는데 아니었어
    인접한거만 갈 수 있구나
  */
  public List<List<Integer>> pacificAtlanticMisunderstanding(int[][] heights) {
    List<List<Integer>> ret = new ArrayList<>();
    
    for (int i = 0; i < heights.length; ++i) {
      for (int j = 0; j < heights[0].length; ++j) {
        if (canReach(heights, i, j)) {
          ret.add(Arrays.asList(i, j));
        }
      }
    }
    
    return ret;
  }

  protected boolean canReach(int[][] heights, int startX, int startY) {
    boolean canReachPacific = false;
    boolean canReachAtlantic = false;
    
    boolean[][] visited = new boolean[heights.length][heights.length];
    
    Queue<List<Integer>> queue = new LinkedList<>();
    queue.add(Arrays.asList(startX, startY));
    while (!queue.isEmpty()) {
      List<Integer> next = queue.poll();
      
      int x = next.get(0);
      int y = next.get(1);
      
      if (x == 0 || y == 0) {
        canReachPacific = true;
      }
      if (x == (heights[0].length - 1) || y == heights.length) {
        canReachAtlantic = true;
      }
      
      for (int i = 0; i < 4; ++i) {
        int nextX = x + dx[i];
        int nextY = y + dy[i];
        
        if (
            0 <= nextX && nextX < heights[0].length
            && 0 <= nextY && nextY < heights.length
            && !visited[nextX][nextY]
            && heights[nextX][nextY] <= heights[x][y]
           ) {
          queue.add(Arrays.asList(nextX, nextY));
        }
      }
      
      visited[x][y] = true;
    }
    
    return canReachPacific && canReachAtlantic;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] {
          { 1, 2, 2, 3, 5 },
          { 3, 2, 3, 4, 4 },
          { 2, 4, 5, 3, 1 },
          { 6, 7, 1, 4, 5 },
          { 5, 1, 1, 2, 4 },
        },
        List.of(
          List.of(0, 4),
          List.of(1, 3),
          List.of(1, 4),
          List.of(2, 2),
          List.of(3, 0),
          List.of(3, 1),
          List.of(4, 0)
        ),
      },
      {
        new int[][] {
          { 3, 3, 3, 3, 3, 3 },
          { 3, 0, 3, 3, 0, 3 },
          { 3, 3, 3, 3, 3, 3 },
        },
        List.of(
          List.of(0, 0), List.of(0, 1), List.of(0, 2), List.of(0, 3),  List.of(0, 4), List.of(0, 5),
          List.of(1, 0), List.of(1, 2), List.of(1, 3),  List.of(1, 5),
          List.of(2, 0), List.of(2, 1), List.of(2, 2), List.of(2, 3),  List.of(2, 4), List.of(2, 5)
        ),
      },
    };

    var solution = new PacificAtlanticWaterFlow();
    for (Object[] parameter : parameters) {
      var heights = (int[][]) parameter[0];
      var expected = (List<List<Integer>>) parameter[1];


      {
        var actual = solution.pacificAtlanticDfs(heights);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
      /*
      {
        var actual = solution.pacificAtlanticMisunderstanding(heights);
        if (!Objects.equals(expected, actual)) {
          throw new IllegalStateException("Expected: " + Objects.toString(expected) +
              ", but was: " + Objects.toString(actual));
        }
      }
      */
    }
  }
}
