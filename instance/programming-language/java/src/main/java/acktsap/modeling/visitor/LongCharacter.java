/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.modeling.visitor;

public class LongCharacter implements Character {

    @Override
    public void attack(Attack attack) {
        attack.action(this);
    }

}
