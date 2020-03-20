package acktsap.pattern.generics;

import java.util.ArrayList;
import java.util.List;

public class WildwardTest {

  interface Eatable {

  }

  static class Fruit implements Eatable {

    public String toString() {
      return "Fruit";
    }
  }

  static class Apple extends Fruit {

    public String toString() {
      return "Apple";
    }
  }

  static class Grape extends Fruit {

    public String toString() {
      return "Grape";
    }
  }

  static class Water implements Eatable {

    public String toString() {
      return "Water";
    }
  }

  static class Toy {

    public String toString() {
      return "Toy";
    }
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
    FruitBox<Apple> appleBox = new FruitBox<>();
    FruitBox<Grape> grapeBox = new FruitBox<>();
//		FruitBox<Grape> grapeBox = new FruitBox<Apple>(); // 에러. 타입 불일치
//		FruitBox<Toy>   toyBox    = new FruitBox<Toy>();   // 에러.

    fruitBox.add(new Fruit());
    fruitBox.add(new Apple());
    fruitBox.add(new Grape());
//    fruitBox.add(new Water());  // 에러. Water는 Fruit의 자손이 아님
    appleBox.add(new Apple());
//		appleBox.add(new Grape());  // 에러. Grape는 Apple의 자손이 아님
    grapeBox.add(new Grape());

    System.out.println("fruitBox-" + fruitBox);
    System.out.println("appleBox-" + appleBox);
    System.out.println("grapeBox-" + grapeBox);
  }
}

