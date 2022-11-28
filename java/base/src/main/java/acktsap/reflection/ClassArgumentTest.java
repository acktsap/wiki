package acktsap.reflection;

public class ClassArgumentTest {
    interface TestInterface {
    }

    private static <T> void method(Class<T> clazz) {
        System.out.println(clazz);
    }

    public static void main(String[] args) {
        method(TestInterface.class);
        method(String.class);
    }
}
