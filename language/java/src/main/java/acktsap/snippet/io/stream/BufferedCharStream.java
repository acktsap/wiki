/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.snippet.io.stream;

import java.io.*;

public class BufferedCharStream {

    /*
     * To reduce overhead, the Java platform implements buffered I/O streams. Buffered input streams
     * read data from a memory area known as a buffer; the native input API is called only when the
     * buffer is empty. Similarly, buffered output streams write data to a buffer, and the native
     * output API is called only when the buffer is full
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            final String resource =
                Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
            in = new BufferedReader(new FileReader(resource));
            out = new BufferedWriter(new FileWriter("outagain.txt"));

            System.out.println("Reading...");
            // read by 2 byte (16 bits)
            int c;
            while ((c = in.read()) != -1) {
                System.out.format("Next: %c%n", c);
                out.write(c);

                // flush manually on space
                if (' ' == c) {
                    System.out.println("-- Flush buffer --");
                    out.flush();
                }
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
