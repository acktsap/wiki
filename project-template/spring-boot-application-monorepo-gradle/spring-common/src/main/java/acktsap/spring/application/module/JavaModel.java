package acktsap.spring.application.module;

import java.util.Objects;

public class JavaModel {
    private final int value;

    public JavaModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        JavaModel model = (JavaModel)other;
        return value == model.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Model{" + "value=" + value
            + '}';
    }
}
