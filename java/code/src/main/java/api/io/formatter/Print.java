/*
 * @copyright defined in LICENSE.txt
 */

package api.io.formatter;

public class Print {

  /*
   * Like all byte and character stream objects, instances of PrintStream and PrintWriter implement
   * a standard set of write methods for simple byte and character output. In addition, both
   * PrintStream and PrintWriter implement the same set of methods for converting internal data into
   * formatted output.
   * 
   * The only PrintStream objects you are likely to need are System.out and System.err. When you
   * need to create a formatted output stream, instantiate PrintWriter, not PrintStream.
   */
  public static void main(String[] args) {
    final int i1 = 2;
    final double r1 = Math.sqrt(i1);

    System.out.print("The square root of ");
    System.out.print(i1);
    System.out.print(" is ");
    System.out.print(r1);
    System.out.println(".");

    final int i2 = 5;
    final double r2 = Math.sqrt(i1);
    System.out.println("The square root of " + i2 + " is " + r2 + ".");
  }

}
