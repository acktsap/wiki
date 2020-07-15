/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStream {

  /*
   * A stream is a sequence of data.
   *
   * xxxStream are for a byte (1 byte unit).
   * 
   * The best approach is to use character streams, as discussed in the next section. There are also
   * streams for more complicated data types. Byte streams should only be used for the most
   * primitive I/O.
   */
  public static void main(String[] args) throws IOException {
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      final String resource =
          Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
      in = new FileInputStream(resource);
      out = new FileOutputStream("outagain.txt");

      System.out.println("Reading...");
      // read by byte (8 bits)
      int c;
      while ((c = in.read()) != -1) {
        System.out.format("Next: %c%n", c);
        out.write(c);
      }
      System.out.println();
    } finally {
      // always close stream
      System.out.println("Finished");
      if (null != in) {
        in.close();
      }
      if (null != out) {
        out.close();
      }
    }
  }

}
