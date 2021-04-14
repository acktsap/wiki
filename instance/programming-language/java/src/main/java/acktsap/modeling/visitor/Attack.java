package acktsap.modeling.visitor;

public interface Attack {

    void action(LongCharacter character);

    void action(ShortCharacter character);

}
