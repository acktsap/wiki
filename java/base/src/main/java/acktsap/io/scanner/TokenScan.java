/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.io.scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TokenScan {

    /*
     * The scanner API breaks input into individual tokens associated with bits of data.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = null;
        try {
            String resource =
                Thread.currentThread().getClass().getResource("/xanadu.txt").getPath();
            scanner = new Scanner(new BufferedReader(new FileReader(resource)));

            // default: WHITESPACE_PATTERN
            scanner.useDelimiter(",\\s*");

            while (scanner.hasNext()) {
                System.out.println("Next token: " + scanner.next());
            }
        } finally {
            if (null != scanner) {
                scanner.close();
            }
        }
    }

}
