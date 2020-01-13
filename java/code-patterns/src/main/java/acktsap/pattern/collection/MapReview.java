/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapReview {

  public static void main(String[] args) {
    // dummy data
    List<Integer> keys = Arrays.asList(new Integer[] {12, 2, 33, 4, 75, 62});
    String value = "v";

    Map<Integer, String> hashMap = new HashMap<>();
    keys.forEach(k -> hashMap.put(k, value));
    // arbitary order
    System.out.println(hashMap);

    // A Red-Black tree based {@link NavigableMap} implementation.
    Map<Integer, String> treeMap = new TreeMap<>();
    keys.forEach(k -> treeMap.put(k, value));
    // sorted order
    System.out.println(treeMap);

    Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
    keys.forEach(k -> linkedHashMap.put(k, value));
    // insertion order
    System.out.println(linkedHashMap);
  }

}
