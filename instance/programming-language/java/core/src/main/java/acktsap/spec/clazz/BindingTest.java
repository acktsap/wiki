package acktsap.spec.clazz;

public class BindingTest {

    static class Parent1 {

        int x = 100;

        void method() {
            System.out.println("Parent1 Method");
        }
    }

    static class Child1 extends Parent1 {

        int x = 200;

        void method() {
            System.out.println("Child1 Method");
            System.out.println("  x=" + x);  // this.x
            System.out.println("  super.x=" + super.x); // 100
            System.out.println("  this.x=" + this.x); // 200
        }
    }

    static class Parent2 {

        int x = 100;

        void method() {
            System.out.println("Parent2 Method");
        }
    }

    static class Child2 extends Parent2 {

    }

    public static void main(String[] args) {
        // field는 해당 타입에 맞게 사용
        // method는 항상 실제 객체를 사용
        Parent1 parent1 = new Child1();
        Child1 child1 = new Child1();
        System.out.println("parent1.x = " + parent1.x); // 100
        parent1.method(); // call Child1.method
        System.out.println("child1.x = " + child1.x); // 200
        child1.method();

        System.out.println();

        // always use parent one
        Parent2 parent2 = new Child2();
        Child2 child2 = new Child2();
        System.out.println("parent2.x = " + parent2.x); // 100
        parent2.method();
        System.out.println("child2.x = " + child2.x); // 100
        child2.method();
    }
}
