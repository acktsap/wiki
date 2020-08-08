/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.modeling.visitor;

public class LongAttack implements Attack {

  @Override
  public void action(LongCharacter character) {
    System.out.println("Long");
  }

  @Override
  public void action(ShortCharacter character) {
    System.out.println("ShortCharacter can't attack long attack");
  }

}
