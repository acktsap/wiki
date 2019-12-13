package api;

public class JavaLambda {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello world");
        // scala
        // val runnable: Runnable = () => { System.out.println("Hello") }
        // kotlin
        // val runnable: Runnable = () -> { System.out.println("Hello") }
    }
}
