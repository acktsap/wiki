/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.snippet.io.stream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharStream {

    /*
     * Reader, Writer are for character (2 bytes unit).
     */
    public static void main(String[] args) throws IOException {
        FileReader in = null;
        FileWriter out = null;
        try {
            final String resource =
                Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
            in = new FileReader(resource);
            out = new FileWriter("outagain.txt");

            System.out.println("Reading...");
            // read by 2 byte (16 bits)
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
