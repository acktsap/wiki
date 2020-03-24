# Java Core

- [Java Core](#java-core)
  - [Type](#type)
    - [Primitive types](#primitive-types)
    - [Wrapper Class, AutoBoxing, Unboxing](#wrapper-class-autoboxing-unboxing)
    - [Integer.valueOf vs Integer.parseInt](#integervalueof-vs-integerparseint)
  - [Class](#class)
    - [Overridding vs Overloading](#overridding-vs-overloading)
    - [Interface vs Abstract class](#interface-vs-abstract-class)
    - [Access Modifier](#access-modifier)
    - [static, default method in interface](#static-default-method-in-interface)
    - [Java Class정의 필수요소](#java-class%ec%a0%95%ec%9d%98-%ed%95%84%ec%88%98%ec%9a%94%ec%86%8c)
    - [Annotation](#annotation)
    - [Generics](#generics)
    - [Exception](#exception)
    - [ClassLoader](#classloader)
  - [Collection vs Stream](#collection-vs-stream)
    - [ArrayList, LinkedList](#arraylist-linkedlist)
    - [Vector vs ArrayList](#vector-vs-arraylist)
    - [HashMap vs LinkedHashMap vs TreeMap](#hashmap-vs-linkedhashmap-vs-treemap)
    - [HashTable vs ConcurrentHashMap](#hashtable-vs-concurrenthashmap)
    - [Lambda, @FunctionalInterface](#lambda-functionalinterface)
  - [Concurrency](#concurrency)
    - [ForkJoinPool](#forkjoinpool)
    - [Atomic Operation](#atomic-operation)
  - [I/O](#io)
    - [InputStream, OutputStream, Reader, Writer](#inputstream-outputstream-reader-writer)
    - [NIO](#nio)
    - [Stream vs Channel](#stream-vs-channel)
  - [Reflection](#reflection)
    - [Proxy vs DynamicProxy](#proxy-vs-dynamicproxy)
  - [Jar](#jar)
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

### Wrapper Class, AutoBoxing, Unboxing

Java에는 primitive type에 각각 해당하는 클래스가 있음. 이를 Wrapper class라고 함. Java 5부터 Wrapper class랑 primitive class간 자동으로 변환해 주는데 이를 AutoBoxing(primitive tyep -> wrapper class)과 Unboxing(wrapper class -> primitive type)이라고 함

### Integer.valueOf vs Integer.parseInt

valueOf는 Wrapper class object를 반환, parseInt는 primitive type을 반환. valueOf에서 -128 ~ 127의 범위는 객체 cache를 사용해서 return. 다른 값들은 객체를 새로 생성.

```java
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

### Overridding vs Overloading

Overridding은 부모의 method를 자손이 재정의하는 것이고 Overloading은 같은 이름의 method를 여러개 정의하는 것. Overloading의 경우에는 return은 영향을 주지 않음. method의 parameter쪽 signature이 달라야 가능.

### Interface vs Abstract class

Interface는 상태를 가질 수 없음. Abstract class는 상태를 가질 수 있음. 원래 1.8 이전까지는 interface에 구현도 가질 수 없었으나 java 8에 default method가 추가된 이후 Interface와 abstract class의 차이점은 상태 여부임. 추가로 Interface는 다중구현을 할 수 있으나 abstract class는 다중상속이 불가능함. 또 interface에는 모든 method가 public이지만 abstract class에는 access modifier가 다 적용됨.

### Access Modifier

- public : 전부가능
- protected : 같은 패키지, 자손들
- default (안적은거) : 같은 패키지만
- private : 클래스 내부에서만

### static, default method in interface

jdk 8 부터 등장. static의 경우 jdk7까지는 일관성을 위해 안만들었음. Collections가 그래서 있는 것. static은 기본적으로 public임. default method는 interface에 method를 추가할 경우 그것을 상속하는 모든 클래스가 이를 구현해야 되서 추가됨. 해당 method에 대한 기본 동작을 제공. 이것으로 인해 추상 클래스와 인터페이스의 차이점은 상태를 가지느냐 안가지느냐 밖에 없게 됨.

### Java Class정의 필수요소

equals, hashcode, toString을 항상 재정의해야한다. equals, hashcode는 HashSet, HashMap에 필요. toString은 사람이 읽기 편한 형태로 표현해야 한다는 권장사항임. 비슷한 수준에서 Comparable도 구현할지 고민해봐야 함. Comparable을 구현해서 객체 사이의 순서를 구현해 주면 `Arrays.sort()`, `Collections.sort()`같은거를 별도의 Comparitor 없이 사용할 수 있음.

### Annotation

java 5부터 추가된 것으로 Annotation처리를 통해 MetaPrograming을 해서 bolierplate code를 줄이는게 그 목적이 있음. 이것을 잘 쓴 예시로는 Lombok, Spring Framework가 있음.

### Generics

java 5부터 추가된 것으로 다양한 타입에 대해 compile type checking을 해주는 기능. Compile하면 Generics정보는 사라지고 특정 타입으로 변경됨.

### Exception

Throwable이 최고 조상이고 크게 Error, Exception, RuntimeException으로 나눌 수 있음. 부모 자손 관계는 다음과 같음.

- Throwable
  - Error
    - OutOfMemoryError
    - ...
  - Exception
    - IOException
    - ...
    - RuntimeException
      - NullPointerException
      - UnSupportedOperationException
      - ...

Error는 심각한 에러를 의미하고 (eg. StackOverflow) Exception은 에러 처리가 강제됨. RuntimeException은 에러 처리가 강제되진 않음.

### ClassLoader

Class를 Loading하는 녀석으로 대표적으로 다음의 3가지 ClassLoader가 있다.

- Bootstrap ClassLoader : 'jre/lib/rt.jar' 안의 클래스를 Loading함. Native C로 구현되어 있음
- ExtClassLoader (PlatformClassLoader in java9) : 'jre/lib/ext' 안의 jar들을 Loading 함
- AppClassLoader (SystemClassLoader in java 9) : classpath에 있거나 manifest의 classpath값으로 지정된 경로에서 class를 loading.

ClassLoader간에는 hierarchy가 있어서 class를 찾을 때 부모에서 먼저 찾고 자손에서 찾는 식임. 그래서 상위 클래스로더는 상위 클래스로더가 로드한 클래스를 볼 수 있지만 부모에서는 자손이 로드한 것을 볼 수 없음.

## Collection vs Stream

Collection은 등 자료를 저장하는 것에 대한 추상화로 List, Set, Map 등이 있음. Stream은 자료를 처리하는 방식에 대한 추상화로 map, filter, flatMap등이 있음. Iterating을 할 때 Collection은 외부에서 사용자가 직접 해야하는 반면에 Stream은 내부적으로 자체적으로 함. Stream의 경우 map, filter, map 이런거 막 묶어놔도 실질적으로 for문은 terminal operation이 나와야만 실행함. Stream의 경우 Parallel stream을 사용할 수 있으나 이 경우 공통 forkJoinPool을 사용하기 때문에 여러개가 이걸 사용하는 경우 오히려 성능 저하가 발생할 수 있음.

### ArrayList, LinkedList

둘다 List의 구현체인데 ArrayList는 내부적으로 값을 배열로 저장. LinkedList는 내부적으로 노드들의 연결로 저장함. ArrayList는 array로 내부 구현을 하기 때문에 random access를 constant time에 할 수 있으나 ArrayList는 값을 추가할 때 capacity가 가득 찬 경우 이를 늘려주는 연산을 해야 하고 중간에 있는 원소를 삭제를 할 경우 뒤에 있는 값들을 모두 다시 복사해줘야함. LinkedList는 값의 추가나 삭제를 할 때 그냥 node의 연결을 해주거나 끊어주면 되지만 index로 access을 할 때 그 index까지 iterating 해야함. Iterating할 때 값들이 붙어있는 ArrayList가 Node들의 주소로 연결되어 있는 LinkedList보다 Locality의 관점에서 더 좋을 수 있음. Locality는 현재 참조하는 값에 인접한 값을 참조할 경우 이를 cache에 저장해 두면 더 빠른 것을 말함.

### Vector vs ArrayList

Vector는 Java 1.0부터 있었고 ArrayList는 Java 1.2부터 있었음. 둘다 내부적으로 Array로 값을 저쟝하고 동적으로 크기를 증가시키지만, Vector는 method에 모두 synchronized가 걸려 있는 반면에 ArrayList는 걸려있지 않음. Vector는 단일 thread에서도 monitor lock을 걸기 때문에 ArrayList에 비해 성능이 느릴 수 있음.

### HashMap vs LinkedHashMap vs TreeMap

HashMap은 일반적인 HashMap이고 LinkedHashMap은 HashMap에 내부적으로 LinkedList로 저장해서 Iterating시 insertion order가 보장된다. TreeMap은 Comparator를 기반으로 Red-Black Tree로 저장. HashMap, LinkedHashMap은 put, get에 O(1)이 보장되지만 TreeMap은 O(log(n))의 시간이 걸림.

HashMap은 내부적으로 array로 저장하는데 `hashCode() & (n - 1)`의 index에 값을 저장함. collision이 나는 경우 separate chaining방식으로 해당 index의 값에 LinkedList (1.8부터는 TreeNode, TreeNode의 경우 bucket안에서 search가 O(log(m)을 보장), LinkedList는 O(m))로 저장함.

### HashTable vs ConcurrentHashMap

HashTable 1.0부터, ConcurrentHashMap은 1.5부터 등장. HashTable은 모든 method에 synchronized가 걸려있는 반면에 ConcurrentHashMap은 synchronized을 최소한으로 걸어서 더 빠른 성능을 보장함. 구체적으로 하면 HashMap이 array의 `hash & (n - 1)` index에 node의 separate chaining의 방식으로 저장하는데 그 bucket만 synchronized를 걸어버림. 그래서 다른 bucket에 대해서는 동시에 처리를 할 수 있음.

### Lambda, @FunctionalInterface

Lambda는 jdk8부터 등장한것으로 그냥 anonymous class 에 syntax suger를 붙인것 뿐임. `@FunctionalInterface`를 통해 interface에 함수가 한개인거를 강제해서 컴파일 시 체크를 해주는 annotation임. FunctionalInterface와 일반 Interface의 차이점은 method갯수임 `@FunctionalInterface`는 한개만 강제되고 일반 interface는 여러개가 올 수 있음. `@FunctionalInterface`가 없더라도 method가 한개인 interface는 lambda로 쓸 수 있음.

자바에서 기본적으로 제공해주는 함수형 인터페이스는 다음과 같음.

- `Runnable` : void run()
- `Supplier` : T get()
- `Consumer` : void accept()
- `Function` : R apply(T t)
- `Predicate` : boolean test(T t)

## Concurrency

### ForkJoinPool

Work-stealing pool로 fork를 통해 분리하고 join을 통해 합침. 일반 ExecutorService 와는 각 Thread들이 개별 queue를 가지고 자기의 task queue가 비어있으면 다른 thread의 task를 가져와서 처리함으로써 최적의 성능을 낼 수 있음. forkjoinpool을 사용할 때는 작업을 독립적인 작업으로 균등하게 분할해야 함.

### Atomic Operation

Compare and Swap으로 CPU Cache와 memory에 있는 값이 다른 가시성 문제를 해결한 operation으로 cache와 memory의 값을 비교해서 값이 다르면 실패하고 재시도를 함.

## I/O

### InputStream, OutputStream, Reader, Writer

InputStream / OutputStream은 byte단위로 I/O을 하는 녀석. Reader / Writer는 char단위 (2 byte)로 I/O를 하는 녀석임.

### NIO

![java-io](./img/java-io.jpg)

Non-blocking IO x, New IO임. 1.3부터 추가되었는데 기존의 Java I/O는 kernal buffer를 직접 handling할 수 없어서 느렸음. 즉 I/O를 위해서는

1. JVM이 Kernal에 I/O를 요청
2. Kernal이 system call을 함
3. Disk Controller가 디스크로부터 파일을 읽어옴
4. DMA (Direct Memory Access)를 이용해서 kernal buffer로 복사
5. JVM 내부 buffer로 복사

여기서 JVM 내부 buffer로 복사를 해야 한다는 문제점이 있었다. 이는 Thread에 blocking이 발생하고, 내부 buffer가 GC의 대상이 된다는 문제점이 있었다.\
이것을 해결하기 위해 Kernal의 buffer에 directly하게 접근하게 할 수 있는 `ByteBuffer`라는 클래스를 제공 (다른 `Buffer`들은 기존의 방식과 똑같음).

### Stream vs Channel

Stream은 one-way라서 I/O 둘다를 위해서는 InputStream, OutputStream 두개가 필요함. But Channel은 two-way라서 한개만 있어도 됨. 내부적으로 처리는 stream의 경우 구현체에 따라 byte부터 Object까지 가능하지만 Channel은 Buffer만 사용함. Stream은 Blocking방식만 가능하지만 Channel에는 Non-blocking방식도 가능함.

## Reflection

클래스의 구체적인 타입을 알지 못해도 클래스의 method, type, field를 접근하게 할 수 있게 해주는 api. Java의 Class에 대한 정보가 static영역에 올라가 있기 때문에 이것이 가능.

### Proxy vs DynamicProxy

그냥 Proxy는 메소드를 다 정의해야함. DynamicProxy는 Reflection을 통해 실행되는 method를 가져와서 동적으로 처리할 수 있음. 구체적으로는 InvocationHandler로 함.

## Jar

TBD

## References

valueOf, parseInt

https://m.blog.naver.com/sthwin/221000179980

AutoBoxing, Unboxing

https://hyeonstorage.tistory.com/168

ClassLoader

https://homoefficio.github.io/2018/10/13/Java-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%A1%9C%EB%8D%94-%ED%9B%91%EC%96%B4%EB%B3%B4%EA%B8%B0/

Reflection

https://brunch.co.kr/@kd4/8

Jar spec

https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jar.html

Collection vs Stream

https://javaconceptoftheday.com/collections-and-streams-in-java/

ForkJoinPool, Parallel Stream

https://m.blog.naver.com/PostView.nhn?blogId=tmondev&logNo=220945933678&proxyReferer=https%3A%2F%2Fwww.google.com%2F

CAP

https://beomseok95.tistory.com/225

NIO

http://eincs.com/2009/08/java-nio-bytebuffer-channel-file/

https://homoefficio.github.io/2016/08/06/Java-NIO%EB%8A%94-%EC%83%9D%EA%B0%81%EB%A7%8C%ED%81%BC-non-blocking-%ED%95%98%EC%A7%80-%EC%95%8A%EB%8B%A4/