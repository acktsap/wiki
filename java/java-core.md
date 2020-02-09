# Java Core

- [Java Core](#java-core)
  - [Type](#type)
    - [Primitive types](#primitive-types)
    - [Boxed type, primitive type, AutoBoxing, Unboxing](#boxed-type-primitive-type-autoboxing-unboxing)
    - [Integer.valueOf vs Integer.parseInt](#integervalueof-vs-integerparseint)
  - [Collections](#collections)
    - [ArrayList vs LinkedList](#arraylist-vs-linkedlist)
    - [HashMap, LinkedHashMap, HashTable, ConcurrentHashMap](#hashmap-linkedhashmap-hashtable-concurrenthashmap)
  - [Stream](#stream)
    - [Functional Interfaces](#functional-interfaces)
  - [Reflection](#reflection)
    - [Proxy vs DynamicProxy](#proxy-vs-dynamicproxy)
  - [References](#references)

## Type

값들의 집합과 그것들 사이의 연산들. 숫자를 바라보는 관점이기도 함.

### Primitive types

- boolean: 1 byte, default: false, bit가 대부분의 architecture에서 directly 접근 안되서 byte임
- byte: 1 byte, default: 0
- char: 2 byte, for storing character default: '\u0000'
- short: 2 byte, default: 0
- int: 4 byte, default: 0
- long: 8 byte, default: 0L
- float: 4 byte, default: 0.0f
- double: 8 byte, default: 0.0d

### Boxed type, primitive type, AutoBoxing, Unboxing

### Integer.valueOf vs Integer.parseInt

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

## Collections

### ArrayList vs LinkedList

### HashMap, LinkedHashMap, HashTable, ConcurrentHashMap

## Stream

### Functional Interfaces

## Reflection

클래스의 구체적인 타입을 알지 못해도 클래스의 method, type, field를 접근하게 할 수 있게 해주는 api. Java의 Class에 대한 정보가 static영역에 올라가 있기 때문에 이것이 가능.

### Proxy vs DynamicProxy

그냥 Proxy는 메소드를 다 정의해야함. DynamicProxy는 Reflection을 통해 실행되는 method를 가져와서 동적으로 처리할 수 있음. 구체적으로는 InvocationHandler로 함.

## References

valueOf, parseInt

https://m.blog.naver.com/sthwin/221000179980

Reflection

https://brunch.co.kr/@kd4/8
