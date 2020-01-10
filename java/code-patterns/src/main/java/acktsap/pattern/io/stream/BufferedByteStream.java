/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedByteStream {

  /*
   * To reduce overhead, the Java platform implements buffered I/O streams. Buffered input streams
   * read data from a memory area known as a buffer; the native input API is called only when the
   * buffer is empty. Similarly, buffered output streams write data to a buffer, and the native
   * output API is called only when the buffer is full
   */
  public static void main(String[] args) throws IOException {
    BufferedInputStream in = null;
    BufferedOutputStream out = null;
    try {
      final String resource =
          Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
      in = new BufferedInputStream(new FileInputStream(resource));
      out = new BufferedOutputStream(new FileOutputStream("outagain.txt"));

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
