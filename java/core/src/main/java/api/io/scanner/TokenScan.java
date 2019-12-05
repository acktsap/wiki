/*
 * @copyright defined in LICENSE.txt
 */

package api.io.scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TokenScan {

  /*
   * The scanner API breaks input into individual tokens associated with bits of data.
   */
  public static void main(String[] args) throws IOException {
    Scanner s = null;
    try {
      final String resource =
          Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
      s = new Scanner(new BufferedReader(new FileReader(resource)));

      // default: WHITESPACE_PATTERN
      s.useDelimiter(",\\s*");
      while (s.hasNext()) {
        System.out.println(s.next());
      }
    } finally {
      if (null != s) {
        s.close();
      }
    }
  }

}
