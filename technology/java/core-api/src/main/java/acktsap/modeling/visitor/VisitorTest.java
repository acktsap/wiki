/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.modeling.visitor;

public class VisitorTest {

  public static void main(String[] args) {
    Character longCharacter = new LongCharacter();
    longCharacter.attack(new LongAttack());
    longCharacter.attack(new ShortAttack());

    Character shortCharacter = new ShortCharacter();
    shortCharacter.attack(new LongAttack());
    shortCharacter.attack(new ShortAttack());
  }

}
