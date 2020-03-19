/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io.formatter;

public class Format {

  public static void main(String[] args) {
    /*
     * d: formats an integer value as a decimal value.
     * 
     * f: formats a floating point value as a decimal value.
     * 
     * n: outputs a platform-specific line terminator.
     * 
     * x: formats an integer as a hexadecimal value.
     * 
     * s: formats any value as a string.
     * 
     * tB: formats an integer as a locale-specific month name
     * 
     * 
     * In the Java programming language, the \n escape always generates the linefeed character
     * (\u000A). Don't use \n unless you specifically want a linefeed character. To get the correct
     * line separator for the local platform, use %n.
     *
     */
    final int i = 2;
    final double r = Math.sqrt(i);
    System.out.format("The square root of %d is %.6f %n", i, r);

    /*
     * 1$: 1st argument
     *
     * +0: the + flag specifies that the number should always be formatted with a sign, and the 0
     * flag specifies that 0 is the padding character.
     * 
     * 20: The minimum width of the formatted value; the value is padded if necessary. By default
     * the value is left-padded with blanks.
     * 
     * 10: For floating point values, this is the mathematical precision of the formatted value
     */
    System.out.format("%f, %1$+020.10f %n", Math.PI);
  }

}
