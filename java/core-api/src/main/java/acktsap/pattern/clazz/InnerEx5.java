/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.clazz;

public class InnerEx5 {

  static class Outer {

    int value = 10;  // Outer.this.value

    class Inner {

      int value = 20;  // this.value

      void method1() {
        int value = 30;
        System.out.println("           value :" + value);
        System.out.println("      this.value :" + this.value);
        System.out.println("Outer.this.value :" + Outer.this.value);
      }
    }
  }

  public static void main(String args[]) {
    Outer outer = new Outer();
    Outer.Inner inner = outer.new Inner();
    inner.method1();
  }
}
