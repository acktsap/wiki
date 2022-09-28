# 1. Kotlin: What and why

- [Introduction](#introduction)
- [1.1. A taste of Kotlin](#11-a-taste-of-kotlin)
- [1.2. Kotlin Primary traits](#12-kotlin-primary-traits)
  - [Target platforms: server-side, Android, anywhere Java runs](#target-platforms-server-side-android-anywhere-java-runs)
  - [Statically typed](#statically-typed)
  - [Functional and object-oriented](#functional-and-object-oriented)
  - [Free and open source](#free-and-open-source)
- [1.3. Kotlin applications](#13-kotlin-applications)
- [1.4. The philosophy of Kotlin](#14-the-philosophy-of-kotlin)
- [1.5. Using the Kotlin tools](#15-using-the-kotlin-tools)

## Introduction

- Kotlin은 Java platform을 대상으로 함.
- Java 호환성을 중요시.
- Java가 쓰일 수 있는 곳에 거의 사용 가능함.

## 1.1. A taste of Kotlin

- 여러 Person 중에서 나이 제일 많은거 찾을 수 있는 Kotlin 코드 맛좀 봐.
  ```kotlin
  // data class
  data class Person(val name: String,
                    val age: Int? = null) // nullable type, default value is null

  // main function
  fun main(args: Array<String>) {
      val persons = listOf(
          Person("Alice"),
          Person("Bob", age = 29) // named argument
      )

      // lambda expression, elvis operator
      val oldest = persons.maxBy { it.age ?: 0 }

      // string template
      println("The oldest is: $oldest")
  }
  ```
- 예제 코드 실행 해보고 싶으면 https://play.kotlinlang.org/ 에 방문해.

## 1.2. Kotlin Primary traits

### Target platforms: server-side, Android, anywhere Java runs

- Kotlin은 Java가 쓰이는 모든 곳에 더 정확하고 생산성 높고 안전한 대체제가 되려고 함.
- 대표적인 사용처는
  - server-side code (Backend).
  - Android.
- But Kotlin -> JavaScript로 변환해서 kotlin을 browser에서도 사용 가능하는 등 다른 곳에서도 사용 가능.

> jvm 생태계나 좀 잘 잡아보지. Java가 계속 발전하는데 괜찮을까?

### Statically typed

- Java처럼 Statically typed라서 type of expression이 compile time에 결정.
- Dynamically typed는 유연하지만 오타 등을 compile time에 확인할 수 없다는 문제점이 있음.
- Java와는 다르게 변수의 type을 추론해 줘서 타입을 굳이 명시 안해도 됨.
  - eg. `val x = 1` -> Int라고 compiler가 추론.
- 정적 언어를 사용함으로써 다음의 이점이 있음.
  - Performance : 어떤 method call을 해야 하는지 runtime에 결정하지 않아서 호출이 빠름.
  - Reliability : Compiler가 검증을 하기 때문에 runtime에 뻗을 위험이 적음.
  - Maintainability : 모르는 코드를 봤을 때 명확하게 보이기 때문에 유지보수가 쉬움.
  - Tool support : static typing은 refactoring, code complete 같은 IDE feature를 사용할 수 있게 해줌.
- Class, interface, generic 같은 개념을 Java에서 가져와서 친숙함.
- nullable type을 도입해서 사용하는 field가 null이 아닌지 compile time에 확인 가능.
- functional programming을 위해 function type을 도입함.

> Java 10에 `var` keyword가 도입되면서 type 추론이 가능해지긴 함.

### Functional and object-oriented

- Java 해봤으면 OOP는 알거고. 그런데 Kotlin은 FP도 됨.
- FP의 개념들.
  - First-class function : function을 값으로 처리해서 변수에 넣거나 인자로 넘기거나 리턴할 수 있음.
  - Immutability : 생성되면 안바뀌는거 확신 가능.
  - No side effects : pure function만 사용하면 같은 input에 같은 결과가 나오는걸 보장 가능.
- FP는 imperative code에 비해 간결하고 높은 수준의 추상화를 제공함.
  - eg. 
  ```kotlin
  // findPerson() contains the general logic of finding a person.
  fun findAlice() = findPerson { it.name == "Alice" }
  fun findBob() = findPerson { it.name == "Bob" }
  ```
- FP는 객체가 immutable하기 때문에 multi threading 할 때 같은 데이터를 다른 thread에서 변경할 일 자체가 없음.
- FP는 side effect가 없어서 테스트가 쉬움.
- Functional Style은 모든 언어에서 사용 가능하긴 하지만 이를 언어 차원에서 제공이 안되는 경우가 있음. Java에서는 8 이전에는 없었음.
- 반면에 Kotlin에는 FP 지원 짱짱.
  - Function type : function을 인자로 넘기거나 리턴 가능.
  - Lambda expression : 함수 작성할 때 적은 코드 사용.
  - Data class : immutable value를 쉽게 작성.
  - object, collection에 사용할 수 있는 functional style의 rich API를 제공.
- 물론 Kotlin에서는 FP를 강제하지는 않으니까 상황따라 OOP, FP 적당히 섞어서 쓰셈.

### Free and open source

- compiler, libraries, tooling 전부 Apache 2.0 라이선스로 open & free to use임. 
- 오픈소스 ide는 Intellij community, Android studio, Eclipse가 있음.

## 1.3. Kotlin applications

todo

## 1.4. The philosophy of Kotlin

## 1.5. Using the Kotlin tools
