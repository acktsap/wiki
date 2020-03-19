# Java Core

- [Java Core](#java-core)
  - [Type](#type)
    - [Primitive types](#primitive-types)
    - [Wrapper class, primitive type, AutoBoxing, Unboxing](#wrapper-class-primitive-type-autoboxing-unboxing)
    - [Integer.valueOf vs Integer.parseInt](#integervalueof-vs-integerparseint)
  - [Class](#class)
    - [Overloading vs Overridding](#overloading-vs-overridding)
    - [Interface vs Abstract class](#interface-vs-abstract-class)
    - [Access Modifier](#access-modifier)
    - [static, default method in interface](#static-default-method-in-interface)
    - [Java Class정의 필수요소](#java-class%ec%a0%95%ec%9d%98-%ed%95%84%ec%88%98%ec%9a%94%ec%86%8c)
  - [Collection vs Stream](#collection-vs-stream)
    - [ArrayList, LinkedList, Vector](#arraylist-linkedlist-vector)
    - [HashMap, LinkedHashMap, ConcurrentHashMap, HashTable](#hashmap-linkedhashmap-concurrenthashmap-hashtable)
    - [Lambda, FunctionalInterface](#lambda-functionalinterface)
  - [InputStream, OutputStream, Reader, Writer](#inputstream-outputstream-reader-writer)
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

### Wrapper class, primitive type, AutoBoxing, Unboxing

Java에서는 primitive type에 각각 해당하는 객체가 있음. 이를 Wrapper class라고 함.

Autoboxing은 primitive type을 Wrapper class에 담을 때 자동으로 Wrapper class로 변환해주는 기능, Unboxing은 반대로 Wrapper class를 primitive type에 담을 때 자동으로 primitive type으로 변환해 주는 기능임. java 1.5부터 지원함.

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

## Class

객체를 정의해 놓은 것. 실제 객체는 instance라고 부름.

### Overloading vs Overridding

Overloading은 부모의 method를 자손이 재정의하는 것이고 Overridding은 같은 이름의 method를 여러개 정의하는 것임. Overridding의 경우에는 method의 parameter쪽 signature이 달라야 함. return은 영향을 주지 않음.`

### Interface vs Abstract class

Interface는 상태를 가질 수 없음. Abstract class는 상태를 가질 수 있음. 원래 1.8 이전까지는 interface에 구현도 가질 수 없었으나 default method가 추가된 이후 Interface와 abstract class의 차이점은 상태 여부임. 추가로 Interface는 다중구현을 할 수 있으나 abstract class는 다중상속이 불가능함. 또 interface에는 모든 method가 public이지만 abstract class에는 access modifier가 다 적용됨.

### Access Modifier

- public : 전부가능
- default : 안적은거, 같은 패키지만 가능, 모듈화 할 때 사용
- protected : 같은 패키지, 자손들에게서
- private : 클래스 내부에서만

### static, default method in interface

jdk 8 부터 등장. static의 경우 jdk7까지는 일관성을 위해 안만들었음. Collections가 그래서 있는 것. static은 기본적으로 public임. default method는 interface에 method를 추가할 경우 그것을 상속하는 모든 클래스가 이를 구현해야 되서 추가됨. 해당 method에 대한 기본 동작을 제공. 이것으로 인해 추상 클래스와 인터페이스의 차이점은 상태를 가지느냐 안가지느냐 밖에 없게 됨.

### Java Class정의 필수요소

toString, hashCode, equals

TBD

## Collection vs Stream

Collection은 등 자료를 저장하는 것에 대한 추상화로 List, Set, Map 등이 있음. Stream은 자료를 처리하는 방식에 대한 추상화로 map, filter, flatMap등이 있음. Iterating을 할 때 Collection은 외부에서 사용자가 직접 해야하는 반면에 Stream은 내부적으로 자체적으로 함. Stream의 경우 map, filter, map 이런거 막 묶어놔도 실질적으로 for문은 terminal operation이 나와야만 실행함.

### ArrayList, LinkedList, Vector

둘다 List의 구현체인데 ArrayList는 내부적으로 값을 배열로 저장. LinkedList는 내부적으로 노드들의 연결로 저장함. ArrayList는 값을 추가할 때 capacity가 가득 찬 경우 이를 늘려주는 연산을 해야 하지만 random access가 빠름. LinkedList는 값을 추가할 때 뒤에 그냥 연결만 해주면 되지만 random access를 할 경우 해당 index만큼 iterating을 해야함. ArrayList는 값을 중간에 빼면 뒤에 있는 값들을 shift해줘야 하는데 LinkedList는 그냥 중간에 있는 녀석의 연결만 끊으면 됨. Iterating할 때 ArrayList가 주소들로 연결되어 있는 LinkedList보다 Locality의 관점에서 더 좋을 수 있음. Locality는 현재 참조하는 값에 인접한 값을 참조할 경우 이를 cache에 저장해 두면 더 빠른 것을 말함.

### HashMap, LinkedHashMap, ConcurrentHashMap, HashTable

### Lambda, FunctionalInterface

jdk8부터 등장 Lambda를 쓰면 그냥 실질적으로 anonymous class가 박혀있음 `() ->` 이거는 syntax sugar일 뿐. `@FunctionalInterface`를 통해 interface에 함수가 한개인거를 강제할 수 있음. method가 하나인 인터페이스를 자바가 몇개 제공해줌. 주로 stream과 함께 사용

- `Supplier` : T get()
- `Consumer` : void accept()
- `Function` : R apply(T t)
- `Predicate` : boolean test(T t)

## InputStream, OutputStream, Reader, Writer

InputStream/OutputStream은 byte단위로 I/O을 하는 녀석. Reader/Writer는 char단위 (2 byte)로 I/O를 하는 녀석임.

## Reflection

클래스의 구체적인 타입을 알지 못해도 클래스의 method, type, field를 접근하게 할 수 있게 해주는 api. Java의 Class에 대한 정보가 static영역에 올라가 있기 때문에 이것이 가능.

### Proxy vs DynamicProxy

그냥 Proxy는 메소드를 다 정의해야함. DynamicProxy는 Reflection을 통해 실행되는 method를 가져와서 동적으로 처리할 수 있음. 구체적으로는 InvocationHandler로 함.

## References

valueOf, parseInt

https://m.blog.naver.com/sthwin/221000179980

Collection vs Stream

https://javaconceptoftheday.com/collections-and-streams-in-java/

AutoBoxing, Unboxing

https://hyeonstorage.tistory.com/168

Reflection

https://brunch.co.kr/@kd4/8
