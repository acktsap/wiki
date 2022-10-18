# Graalvm Native Image

- [Concept](#concept)
- [How it works](#how-it-works)
- [Static Analysis](#static-analysis)
- [Cross Compile](#cross-compile)
- [Sample](#sample)
  - [Runing static code](#runing-static-code)
  - [binding](#binding)
- [References](#references)

## Concept

- Java code ahead-of-time를 binary로 만든 것.
- 걍 class 파일들이 돌 때 JIT로 필요할 때 compile 하는데 이걸 전부 해두는 것?
- application classes, standard-library classes, language runtime, and statically-linked native code를 포함.
- native-image tool 사용. 사용하려면 glibc-devel, zlib, gcc, and/or libstdc++-static 같은 local toolchain이 필요함.
- Pros
  - 시작이 빠름.
  - 공격을 잘 방어.
- Cons
  - Jar 마는거보다 컴파일이 느림.

## How it works

Build Time vs Run Time

- 그냥 jvm 환경에서 수행될 때는 class가 처음 사용될 때 초기화됨. 그런데 native image를 빌드하면 빌드 할 때 초기화됨.
- 예를 들어서 static code 같은게 jvm 환경에서는 처음 수행될 때 로드되는데 native image에서는 빌드 할 때 초기화됨. 그래서 느림.
- 그런데 이 native image 빌드를 어떤 환경에서 하느냐? jvm 위에서 함.
- 빌드 타임에 처리된 static code 들은 image heap이라는 곳에 저장됨.
- Image Heap이 저장하는 것.
  - Objects created during the image build that are reachable from application code.
  - `java.lang.Class` objects of classes used in the native image.
  - Object constants embedded in method code.
- 두 가지 방법으로 초기화 가능.
  - By passing --initialize-at-build-time=<class> to the native-image builder.
  - By using a class in the static initializer of a build-time initialized class.
- 자주 사용되는 `java.lang.String`, `java.util.**` 같은 친구들은 빌드 graalvm이 빌드타임에 image heap에 넣어줌. 전부 이렇게 넣는거는 아님.

## Static Analysis

- 어떤 class, method들이 사용될지 검사하는 것. 모든 class를 바이너리에 넣을 필요는 없음.
- 이거는 `main` method를 기준으로 접근 사능한지 검사함.
- 이렇게 하니까 동적으로 class를 load하는거 같은 장난은 못침.

## Cross Compile

- [[native-image] Cross compilation support? #407](https://github.com/oracle/graal/issues/407)
  - 아직 미지원인듯
  - 각각 platform 별로 build해야 할거 같음. 관련된 [github action](https://github.com/marketplace/actions/github-action-for-graalvm#building-a-helloworld-with-graalvm-native-image-on-different-platforms)이 있음.

## Sample

### Runing static code

Plain jvm

```sh
javac HelloWorldStatic.java
java HelloWorldStatic 
```

Result

```sh
Greeter is getting ready!
Hello, World!
```

Native

```sh
javac HelloWorldStatic.java
$GRAALVM_HOME/bin/native-image HelloWorldStatic
./helloworldstatic
```

Result

```sh
Greeter is getting ready!
Hello, World!
```

Native

```sh
javac HelloWorldStatic.java
$GRAALVM_HOME/bin/native-image HelloWorldStatic --initialize-at-build-time=HelloWorld\$Greeter
./helloworldstatic
```

Result

```sh
========================================================================================================================
GraalVM Native Image: Generating 'helloworld' (executable)...
========================================================================================================================
Greeter is getting ready!
[1/7] Initializing...          

...

Hello, World!
```

### binding

Jvm

```sh
javac ExampleStatic.java
java -Dmessage=hi ExampleStatic
java -Dmessage=hello ExampleStatic 
```

Result

```sh
Hello, World! My message is: hi
Hello, World! My message is: hello
```

Native without initialize at build time

```sh
javac ExampleStatic.java
$GRAALVM_HOME/bin/native-image ExampleStatic -Dmessage=native
./examplestatic
./examplestatic -Dmessage=aNewMessage
```

Result

```sh
Hello, World! My message is: null
Hello, World! My message is: aNewMessage
```

Native with initialize at build time

```sh
javac ExampleStatic.java
$GRAALVM_HOME/bin/native-image ExampleStatic --initialize-at-build-time=ExampleStatic -Dmessage=native
./examplestatic
./examplestatic -Dmessage=aNewMessage
```

Result

```sh
Hello, World! My message is: native
Hello, World! My message is: native
```

## References

- [graalvm native image official doc](https://www.graalvm.org/22.2/reference-manual/native-image/)
