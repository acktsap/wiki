package acktsap.spec.generics;

import java.util.ArrayList;
import java.util.List;

public class WildwardRelation {

    interface Eatable {

    }

    static class Fruit implements Eatable {

    }

    static class Apple extends Fruit {

    }

    static class Grape extends Fruit {

    }

    static class Water implements Eatable {

    }


    static class Toy {

    }

    static class Box<T> {

        List<T> list = new ArrayList<T>();

        void add(T item) {
            list.add(item);
        }

        T get(int i) {
            return list.get(i);
        }

        int size() {
            return list.size();
        }

        public String toString() {
            return list.toString();
        }
    }

    // wildcard restriction
    static class FruitBox<T extends Fruit & Eatable> extends Box<T> {

    }

    public static void main(String[] args) {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        fruitBox.add(new Fruit());
        fruitBox.add(new Apple());
        fruitBox.add(new Grape());
//        fruitBox.add(new Water());  // 에러. Water는 Fruit의 자손이 아님
        System.out.println("fruitBox-" + fruitBox);

        FruitBox<Apple> appleBox = new FruitBox<>();
        appleBox.add(new Apple());
//        appleBox.add(new Grape());  // 에러. Grape는 Apple의 자손이 아님
        System.out.println("appleBox-" + appleBox);

        FruitBox<Grape> grapeBox = new FruitBox<>();
        grapeBox.add(new Grape());
        System.out.println("grapeBox-" + grapeBox);

//        FruitBox<Grape> grapeBox = new FruitBox<Apple>(); // 에러. 타입 불일치
//        FruitBox<Toy> toyBox = new FruitBox<Toy>();   // 에러.
    }
}

