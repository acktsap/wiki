package acktsap.spring.application.module;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JavaModelTest {

    @Test
    void test() {
        JavaModel javaModel = new JavaModel(3);
        assertThat(javaModel).isEqualTo(new JavaModel(3));
        System.out.printf("hashCode: %d, toString: %s%n", javaModel.hashCode(), javaModel.toString());
    }
}
