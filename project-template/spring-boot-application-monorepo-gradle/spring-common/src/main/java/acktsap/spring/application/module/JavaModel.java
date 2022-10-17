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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JavaModel model = (JavaModel)o;
        return value == model.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Model{" +
            "value=" + value +
            '}';
    }
}
