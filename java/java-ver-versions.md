# Java Per Versions

- [Java Per Versions](#java-per-versions)
  - [Java 1.5](#java-15)
  - [Java 1.7](#java-17)
  - [Java 1.8](#java-18)
  - [Java 1.10](#java-110)
  - [Java 1.11](#java-111)
  - [Java 1.12](#java-112)

## Java 1.5

https://docs.oracle.com/javase/1.5.0/docs/relnotes/features.html

- Generics : Allows a type or method to operate on objects of various types while providing compile-time type safety.
- Annotations
  - Lets you avoid writing boilerplate code by enabling tools to generate it from annotations.
  - Leads to a "declarative" programming style.

## Java 1.7

https://www.oracle.com/technetwork/java/javase/jdk7-relnotes-429209.html

- Try-with-resources : Ensures that each resource is closed at the end of the statement.
- Garbare-First (G1) garbage collectors
- Fork-Join Pool : 분할 정복과 비슷, 나눠서 일하고 합친다

## Java 1.8

https://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html

- Lambda Expression : Enable you to treat functionality as a method argument.
- default methods : Enable new functionality to be added to the interfaces of libraries and ensure binary compatibility with code written for older versions of those interface.
- java.util.stream : support functional-style operations on streams of elements.
- Removal or PermGen in JVM : 각종 메타 정보를 OS가 관리하는 영역으로 옮겨 Perm 영역의 사이즈 제한을 없앤 것이라 할 수 있다.

## Java 1.10

https://openjdk.java.net/projects/jdk/10/

- Type Inference

## Java 1.11

https://openjdk.java.net/projects/jdk/11/

- Java Mission Control, which was shipped in JDK 7, 8, 9, and 10, is no longer included with the Oracle JDK. It is now a separate download
- Epsilon GC : Only handles memory allocation, does not implement any memory reclamation. Useful for performance testing

## Java 1.12

https://openjdk.java.net/projects/jdk/12/

- Shenandoah GC : To reduces GC pause times by doing evacuation work concurrently with the running Java threads.
