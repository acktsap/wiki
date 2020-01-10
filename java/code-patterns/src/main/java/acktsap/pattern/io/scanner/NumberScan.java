/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io.scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class NumberScan {

  /*
   * The scanner API breaks input into individual tokens associated with bits of data.
   */
  public static void main(String[] args) throws IOException {
    Scanner s = null;
    try {
      double sum = 0;
      final String resource =
          Thread.currentThread().getClass().getResource("/usnumbers.txt").getPath();
      s = new Scanner(new BufferedReader(new FileReader(resource)));

      s.useLocale(Locale.US); // can read 32,767 as representing integer
      while (s.hasNext()) {
        if (s.hasNextDouble()) {
          sum += s.nextDouble();
        } else {
          s.next();
        }
      }
      System.out.println(sum);
    } finally {
      if (null != s) {
        s.close();
      }
    }

  }

}
