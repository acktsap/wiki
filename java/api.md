# Java Api

- [Java Api](#java-api)
  - [Collections](#collections)
    - [ArrayList vs LinkedList](#arraylist-vs-linkedlist)
    - [HashMap, LinkedHashMap, HashTable, ConcurrentHashMap](#hashmap-linkedhashmap-hashtable-concurrenthashmap)
  - [Integer.valueOf vs Integer.parseInt](#integervalueof-vs-integerparseint)
  - [References](#references)

## Collections

### ArrayList vs LinkedList

### HashMap, LinkedHashMap, HashTable, ConcurrentHashMap

## Integer.valueOf vs Integer.parseInt

valueOf는 Boxed object를 반환, parseInt는 primitive type을 반환함. valueOf가 내부적으로 parseInt사용. valueOf에서 -128 ~ 127의 범위는 객체 cache를 사용해서 return. 다른 값들은 객체를 새로 생성. cache는 Integer가 immutable type이라서 가능함

```java
public static Integer valueOf(String s, int radix) throws NumberFormatException {
    return Integer.valueOf(parseInt(s,radix));
}
public static Integer valueOf(int i) {
  final int offset = 128;
  if (i >= -128 && i <= 127) { // must cache
    return IntegerCache.cache[i + offset];
  }
  return new Integer(i);
}
```

## References

valueOf, parseInt

https://m.blog.naver.com/sthwin/221000179980
