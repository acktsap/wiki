# 2. Kotlin Basics

- [2.1. Basic elements: functions and variables](#21-basic-elements-functions-and-variables)
  - [Hello world!](#hello-world)
  - [Functions](#functions)
  - [Variable](#variable)
  - [Easier string formatting: string template](#easier-string-formatting-string-template)
- [2.2. Class and properties](#22-class-and-properties)
  - [Propertes](#propertes)
  - [Custom accessors](#custom-accessors)
  - [Kotlin source code layout: directories and packages](#kotlin-source-code-layout-directories-and-packages)
- [2.3. Representing and handling choices: enums and "when"](#23-representing-and-handling-choices-enums-and-when)
- [2.4. Iterating over things: "while" and "for" loops](#24-iterating-over-things-while-and-for-loops)
- [2.5. Exceptions in Kotlin](#25-exceptions-in-kotlin)

This chapter covers

- Declaring functions, variables, classes, enums, and properties
- Control structures in Kotlin
- Smart casts
- Throwing and handling exceptions

## 2.1. Basic elements: functions and variables

### Hello world!

```kotlin
fun main(args: Array<String>) {
    println("Hello, world!")
}
```

- 함수 선언에 `fun`이 사용.
- 함수 인자 타입이 인자 이름 다음에 나옴.
- 함수가 top level of file에 선언 가능. class에 안넣어도 됨.
- Java와는 다르게 Array가 class라서 별도의 syntax가 없어도 됨.
- 긴 `System.out.println` 말고 wrapper인 `println`씀. Kotlin은 Java std library 함수에 대한 wrapper를 많이 제공함.
- 현대적인 언어답게 `;` 없어도 됨.

### Functions

```kotlin
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}
```

- 함수 리턴을 하려면 인자 리스트 끝나고 타입 적으면 됨.
- Kotlin에서는 if도 expression임. loops (for, do, do/while) 빼고 다 expression임. 그런데 assignment는 statement임 (java에서는 expression). Java에서 비교랑 할당을 실수해서 사용하는 경우를 방지하기 위해 assignment를 statement로 함.

Expression Body

```kotlin
fun max(a: Int, b: Int): Int = if (a > b) a else b
```

- 이렇게도 사용 가능. 이렇게 사용하는걸 Expression Body라고 부름. 이전의 `{}`로 감싸진 친구는 Block Body라고 부름.


```kotlin
fun max(a: Int, b: Int) = if (a > b) a else b
```

- 심지어 리턴 타입 명시 안해도 됨. 컴파일러가 type inference로 알아서 찾아줌.
- 리턴 타입 명시 안해도 되는거는 Expression Body에서만임. Block Body의 경우 다수의 return이 있는 경우가 있어서 명시해주는게 보기 편해서 명시해주게 언어를 설계함.

### Variable

```kotlin
// without type
val question = "The Ultimate Question of Life, the Universe, and Everything"
// with type
val answer: Int = 42
```

- kotlin에서는 Java랑은 다르게 타입을 명시 안해줘도 됨. type inference가 알아서 해줌. 명시 해야 한다면 변수명 다음에 해주면 됨.

```kotlin
val answer: Int
answer = 42
```

- 변수 초기값이 없으면 타입을 명시해줘야 함.

변수를 선언하는 방법.

- `val` (value) : Immutable reference.
- `var` (variable) : Mutable reference.
- 가능하면 `val`를 사용해서 불변으로 짜는게 함수형처럼 짜는거라 좋음.

```kotlin
val message: String
if (canPerformOperation()) {
    message = "Success"
    // ... perform the operation
} else {
    message = "Failed"
}
```

- `val` 변수를 조건에 따라 다르게 할당할 수 있음. 이런 경우 compiler가 한번만 실행하는걸 보장해줌.

```kotlin
val languages = arrayListOf("Java")
languages.add("Kotlin")
```

- `val`이 immutable reference 이지만 가리키는 대상 자기 자신은 변화가 가능.

```kotlin
var answer = 42
answer = "no answer"
```

- `var`은 변수 값을 바꿀 수 있지만 다른 타입으로는 안됨.

### Easier string formatting: string template

```kotlin
fun main(args: Array<String>) {
    val name = if (args.size > 0) args[0] else "Kotlin"
    // string template
    // Java의 ("Hello, " + name + "!" 와 동일
    println("Hello, $name!")
}
```

- 변수값을 string literal 안에 넣을 수 있음.
- `$` 사용 $ 자체를 넣으려면 `\$` 사용.
- 실제 compiled된 코드는 `StringBuilder` 사용하긴 함.

```kotlin
// ${} usage
fun main(args: Array<String>) {
    if (args.size > 0) {
        println("Hello, ${args[0]}!")
    }
}

// ""(double quote) 도 안에 사용 가능.
fun main(args: Array<String>) {
    println("Hello, ${if (args.size > 0) args[0] else "someone"}!")
}
```

- `$`는 단순한 변수명 뿐만 아니라 복잡한거도 사용 가능. `${}` 사용하면 됨.

## 2.2. Class and properties

- Kotlin에서도 class 제공. 그런데 보다 적은 코드로 작성 가능.

```java
public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

```kotlin
class Person(val name: String)
```

- Boilerplate code 다 없앰.
- Java와는 다르게 기본 option이 public이라 public keyword 굳이 안써도 됨.

### Propertes

```kotlin
class Person(
    val name: String,
    var isMarried: Boolean
)
```

- Java에서 data class를 정의할 때 field를 private로 하고 getter, setter accesor를 제공함. Java에서는 field + accessor를 property라고 부름.
- Kotlin에서는 이런 property를 언어 차원에서 제공함.
- `val`는 read-only property.
- `var`는 mutable property.

```sh
/* Java */
>>> Person person = new Person("Bob", true);
>>> System.out.println(person.getName());
Bob
>>> System.out.println(person.isMarried());
true
```

- 앞서 정의한 kotlin class를 Java에서는 이렇게 사용 가능.
- 생성된 getter, setter에는 예외가 있음. 'is'가 붙은 경우 getter에는 추가적인 prefix가 붙지 않음. setter에는 'is'가 'set'으로 대체됨.
  - eg. getter : isMarried, setter : setMarried

```sh
>>> val person = Person("Bob", true)
>>> println(person.name)
Bob
>>> println(person.isMarried)
true
```

- Kotlin으로는 이렇게 깔끔하게 사용 가능.
- 역으로 Java로 정의한 class를 Kotlin에서도 이렇게 사용 가능. getName, setName이 있는 경우 `var name`으로 취급, isMarried, setMarried의 경우 `var isMarried`로 취급.

### Custom accessors

```kotlin
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width
        }

        // 이렇게도 가능
        get() = height == width
}
```

- custom property accessor.
- 값을 저장하지 않고 매번 계산함.

```sh
>>> val rectangle = Rectangle(41, 43)
>>> println(rectangle.isSquare)
false
```

- 이렇게 사용.
- 사실 이렇게 하는게 함수 정의하는거랑 차이는 없는데 가독성이 좋음.

### Kotlin source code layout: directories and packages

todo

## 2.3. Representing and handling choices: enums and "when"

## 2.4. Iterating over things: "while" and "for" loops

## 2.5. Exceptions in Kotlin