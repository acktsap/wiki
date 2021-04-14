/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.modeling.visitor;

public class ShortAttack implements Attack {

    @Override
    public void action(LongCharacter character) {
        System.out.println("LongCharacter can't attack short attack");
    }

    @Override
    public void action(ShortCharacter character) {
        System.out.println("Short");
    }

}
