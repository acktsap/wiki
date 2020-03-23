# Java Per Versions

- [Java Per Versions](#java-per-versions)
  - [Java LTS](#java-lts)
  - [Java 1.5](#java-15)
  - [Java 1.7](#java-17)
  - [Java 1.8](#java-18)
  - [Java 1.9](#java-19)
  - [Java 1.10](#java-110)
  - [Java 1.11](#java-111)
  - [Java 1.12 or more](#java-112-or-more)
  - [References](#references)

## Java LTS

Long-Term Support의 줄임말로 해당 버전은 다른 버전에 비해 오랫동안 유지보수가 됨. Java에서는 8, 11버전이 LTS 임

## Java 1.5

- Generics : Compile-time type safety를 여러 타입에 대해 제공하는 기술.
- Annotations : Annotation처리를 통해 boilerplate code를 줄임. 선언적 프로그래밍이 가능하게 함.

## Java 1.7

- Try-with-resources : AutoClosable을 상속한 객체들에 대해서 자동으로 close
- Garbare-First (G1) garbage collectors의 공식적인 추가
- Fork-Join Pool : 분할 정복과 비슷, 나눠서 일하고 합친다

## Java 1.8

- Lambda Expression : anonymous class에 대한 간략한 표현이 가능
- default methods : interface에 method를 정의할 수 있음. 구현체들을 바꾸지 않고 기능을 추가하기 위해 제공.
- java.util.stream : 데이터 처리에 대해 추상화된 method들을 함수형으로 제공.
- Removal or PermGen in JVM : 각종 메타 정보를 OS가 관리하는 영역으로 옮겨 Perm 영역의 사이즈 제한을 없앤 것이라 할 수 있다.

## Java 1.9

- Garbare-First (G1)이 default GC

## Java 1.10

- Type Inference : `var list = new LinkedList<>`

## Java 1.11

- Java Mission Control의 제거 (Oracle JDK에서 7, 8, 9, 10서 기본으로 제공).
- Z GC : pause time 10ms이하를 목표로 한 새로운 GC

## Java 1.12 or more

TBD

## References

https://en.wikipedia.org/wiki/Java_version_history

https://docs.oracle.com/javase/1.5.0/docs/relnotes/features.html

https://www.oracle.com/technetwork/java/javase/jdk7-relnotes-429209.html

https://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html

https://openjdk.java.net/projects/jdk/10/

https://openjdk.java.net/projects/jdk/11/

https://openjdk.java.net/projects/jdk/12/
