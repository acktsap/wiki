/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.io.scanner;

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
        Scanner scanner = null;
        try {
            String file =
                Thread.currentThread().getClass().getResource("/usnumbers.txt").getPath();
            scanner = new Scanner(new BufferedReader(new FileReader(file)));

            // can read 32,767 as representing integer
            scanner.useLocale(Locale.US);

            double sum = 0;
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    sum += scanner.nextDouble();
                } else {
                    // just pass it
                    scanner.next();
                }
            }
            System.out.println("Sum: " + sum);
        } finally {
            if (null != scanner) {
                scanner.close();
            }
        }

    }

}
