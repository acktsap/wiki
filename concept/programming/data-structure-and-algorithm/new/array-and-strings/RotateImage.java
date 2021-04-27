import java.util.Arrays;

/*
 * Descryption
 *
 * https://leetcode.com/problems/rotate-image/
 * 
 * You are given an n x n 2D matrix representing an image.
 * 
 * Rotate the image by 90 degrees (clockwise).
 * 
 * Note:
 * 
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.
 * DO NOT allocate another 2D matrix and do the rotation.
 * 
 * Example 1:
 * 
 * Given input matrix = 
 * [
 *   [1,2,3],
 *   [4,5,6],
 *   [7,8,9]
 * ],
 * 
 * rotate the input matrix in-place such that it becomes:
 * [
 *   [7,4,1],
 *   [8,5,2],
 *   [9,6,3]
 * ]
 *
 *
 * Approach & Proof 
 *
 * Solution = reverseRow(transpose(matrix))
 *
 * First
 *
 * a b
 * c d
 *
 * After transpose
 *
 * a c
 * b d
 *
 * After reverseRow
 *
 * c a
 * d b
 *
 *
 * Complexity
 *
 *  - Time  : O(1)
 *  - Space : O(1)
 *
 *
 * Review
 *
 * 처음에 너무 복잡하게 생각하고 알고리즘을 이상하게 설계했었다..
 * matirx 관련된거는 가능하면 단순하게 하는게 좋을 듯 실수하기 쉬움
 * 그리고 이런 선형대수같은 문제는 transpose, reverseRow, reverseColumn만 활용해서 풀려고 해보자
 *
 *
 */
class RotateImage {
  public void rotate(int[][] matrix) {
    int n = matrix.length;

    // transpose (left-bottom <-> right-top)
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < i; ++j) {
        swap(matrix, i, j, j, i);
      }
    }

    // left <-> right
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n / 2; ++j) {
        swap(matrix, i, j, i, (n - 1) - j);
      }
    }

  }

  protected void swap(int[][] matrix, int i1, int j1,
      int i2, int j2) {
    int tmp = matrix[i1][j1];
    matrix[i1][j1] = matrix[i2][j2];
    matrix[i2][j2] = tmp;
  }

  public static void main(String[] args) {
    Object[][] parameters = new Object[][] {
      {
        new int[][] {
                       { 1, 2, 3 },
                       { 4, 5, 6 },
                       { 7, 8, 9 },
                    },
        new int[][] {
                       { 7, 4, 1 },
                       { 8, 5, 2 },
                       { 9, 6, 3 },
                    },
      },
      {
        new int[][] {
                       {  1,  2,  3,  4 },
                       {  5,  6,  7,  8 },
                       {  9, 10, 11, 12 },
                       { 13, 14, 15, 16 },
                    },
        new int[][] {
                       { 13,  9,  5,  1 },
                       { 14, 10,  6,  2 },
                       { 15, 11,  7,  3 },
                       { 16, 12,  8,  4 },
                    },
      },
    };

    RotateImage solution = new RotateImage();
    for (Object[] parameter : parameters) {
      int[][] matrix = (int[][]) parameter[0];
      int[][] expected = (int[][]) parameter[1];
      solution.rotate(matrix);
      for (int i = 0; i < matrix.length; ++i) {
        if (!Arrays.equals(expected[i], matrix[i])) {
          throw new IllegalStateException("Expected: " + Arrays.toString(expected[i]) +
              ", but was: " + Arrays.toString(matrix[i])  + " at row " + i);
        }
      }
    }
  }
}
