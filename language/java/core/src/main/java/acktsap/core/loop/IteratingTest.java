package acktsap.core.loop;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IteratingTest {

  public static void main(String[] args) {
    /* array */
    int[] array = {1, 2, 3};

    // array index
    for (int i = 0; i < array.length; ++i) {
      System.out.println("Array index: " + array[i]);
    }

    // array for each
    for (int value : array) {
      System.out.println("Array for each: " + value);
    }

    /* collection */
    Collection<Integer> collection = List.of(1, 2, 3);

    // collection iterator
    Iterator<Integer> it = collection.iterator();
    while (it.hasNext()) {
      Integer value = it.next(); // throws NoSuchElementException if no next element
      System.out.println("Collection iterator: " + value);
    }

    // collection for each
    for (int value : collection) {
      System.out.println("Collection for each: " + value);
    }

    // collection for each
    for (int value : collection) {
      System.out.println("Collection for each: " + value);
    }

    // collection forEach (java 8 or higher)
    collection.forEach(i -> System.out.println("Collection forEach: " + i));

    /* list */
    List<Integer> list = List.of(1, 2, 3);

    // list index (list only)
    for (int i = 0; i < list.size(); ++i) {
      System.out.println("List index: " + list.get(i));
    }

    /* map */
    Map<Integer, String> map = Map.of(1, "1", 2, "2", 3, "3");

    // map for each
    for (Entry<Integer, String> entry : map.entrySet()) {
      int key = entry.getKey();
      String value = entry.getValue();
      System.out.printf("Map forEach (%d, %s)%n", key, value);
    }

    // map forEach
    map.forEach((key, value) -> System.out.printf("Map forEach (%d, %s)%n", key, value));
  }

}
