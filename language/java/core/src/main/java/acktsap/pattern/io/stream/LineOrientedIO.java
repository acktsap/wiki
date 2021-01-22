/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io.stream;

import java.io.*;

public class LineOrientedIO {

    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            final String resource =
                Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
            in = new BufferedReader(new FileReader(resource));
            out = new PrintWriter(new FileWriter("outagain.txt"));

            System.out.println("Reading...");
            // read by line
            // line break: a carriage-return/line-feed sequence ("\r\n"), a single carriage-return ("\r"),
            // or a single line-feed ("\n")
            String line;
            while ((line = in.readLine()) != null) {
                System.out.format("Next: %s%n", line);
                out.write(line);
            }
            System.out.println();
        } finally {
            // always close stream
            System.out.println("Finished");
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
