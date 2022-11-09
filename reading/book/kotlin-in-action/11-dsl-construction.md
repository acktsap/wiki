# 11. DSL construction

- [11.1. From APIs to DSLs](#111-from-apis-to-dsls)
  - [The concept of domain-specific languages](#the-concept-of-domain-specific-languages)
  - [Internal DSLs](#internal-dsls)
  - [Structure of DSLs](#structure-of-dsls)
  - [Building HTML with an internal DSL](#building-html-with-an-internal-dsl)
- [11.2. Building structrued Apis: lambdas with receivers in DSLs](#112-building-structrued-apis-lambdas-with-receivers-in-dsls)
  - [Lambdas with receivers and extension function types](#lambdas-with-receivers-and-extension-function-types)
  - [Using lambdas with receivers in HTML builders](#using-lambdas-with-receivers-in-html-builders)
- [11.3. More flexible block nesting with the 'invoke' convention](#113-more-flexible-block-nesting-with-the-invoke-convention)
- [11.4. Kotlin DSLs in practice](#114-kotlin-dsls-in-practice)

This chapter covers

- Building domain-specific languages
- Using lambdas with receivers
- Applying the invoke convention
- Examples of existing Kotlin DSLs

## 11.1. From APIs to DSLs

도전 과제

- 우리는 모두 readability와 maintainability가 좋은 코드를 작성하려고 함.
- 이걸 달성하려면 class 한개에만 집중하면 안됨. class끼리 상호작용해서 상호작용하는 interface들을 봐야 함.

AP가 Clean하다는 것은

- 이름과 개념을 잘 선택해서 잘 읽혀야 함. 이거는 어떤 코드를 작성하든 중요.
- code 자체가 군더러기 없이 필요한 syntax만 있어야 함.

예시

```kotlin
/* Extension function */
// from
StringUtil.capitalize(s)
// to
s.capitalize()

/* Infix call */
// from
1.to("one")
// to
1 to "one"

/* Operator overloading */
// from
set.add(2)
// to
set += 2 

/* Convention for the get method */
// from
map.get("key")
// to
map["key"] 

/* Lambda outside of parentheses */
// from
file.use({ f -> f.read() } )
// to
file.use { it.read() } 

/* Lambda with a receiver */
// from
sb.append("yes")
sb.append("no")
// to
with (sb) {
  append("yes")
  append("no")
}
```

- Kotlin에서는 multiple method call에 대한 strucuture를 만드는 식으로 해서 DSL을 작성할 수 있음.

### The concept of domain-specific languages

- dsl은 컴퓨터로 일반적인 문제를 푸는 general-purpose programming language랑 다름.
- dsl은 특정한 domain에 대한 언어임. 그 도메인 제외하고는 노상관.

예시

- SQL : db 데이터에 어떤 연산을 할건지 표현
- regular expression : 특정한 패턴의 문자열을 표현
- 이 언어들은 제공하는 연산을 도메인에 맞게 잘 제약해서 DSL이 됨.

DSL의 장, 단점

- DSL의 장점
  - 선언형으로 작성. 어떻게 수행되는지는 engine이 알아서 함. 그래서 최적화 같은걸 보다 효율적으로 수행.
  - general-purpose language처럼 명령형으로 작성하면 그런 작업을 직접 해줘야 함.
- DSL의 단점
  - 자체적인 언어를 가지고 있기 때문에 general-purpose language랑 엮을때 귀찮음. syntax가 맞는지 등을 compile time에 알 수 없음.

### Internal DSLs

- Internal DSL이란 general-purpose language로 작성된 DSL.
- general-purpose language로 작성되어 있기 때문에 syntax check 등의 이점을 가져갈 수 있음.
  
예시: SQL

- plain SQL : String으로 처리하는 등 general-purpose language랑 엮는 작업이 필요함.
  ```sql
  SELECT Country.name, COUNT(Customer.id)
    FROM Country
    JOIN Customer
      ON Country.id = Customer.country_id
  GROUP BY Country.name
  ORDER BY COUNT(Customer.id) DESC
    LIMIT 1
  ```
- Internal DSL (Exposed 사용) : Kotlin으로 작성되어 있어서 general-purpose language랑 겪는 작업이 필요 없음.
  ```kotlin
  (Country join Customer)
    .slice(Country.name, Count(Customer.id))
    .selectAll()
    .groupBy(Country.name)
    .orderBy(Count(Customer.id), isAsc = false)
    .limit(1)
  ```

### Structure of DSLs

- 사실 DSL이랑 일반적인 API의 명확한 경계선은 없음.
- 하지만 API와는 다르게 DSL은 **자체적인 structure (or grammer)**가 있음.

보통의 API

- method 여러개로 구성. client는 이 method 여러개를 한개 한개 호출.
- 자체적인 구조가 없음.
- method 호출간 context 공유가 안됨.
- command-query API라고 부름.

DSL

- 자체적인 구조가 있음. 즉, Grammar가 있음.
  - 자연어의 문법과 동일하게 특정 문장에서 호출할 수 있는 method 등을 제약할 수 있음.
- method 호출간 context 공유가 됨.
  - 예시
  ```groovy
  // dsl : share context
  dependencies {
    compile("junit:junit:4.11")
    compile("com.google.inject:guice:4.1.0")
  }

  // command-qeury API : don't share context
  project.dependencies.add("compile", "junit:junit:4.11")
  project.dependencies.add("compile", "com.google.inject:guice:4.1.0")
  ```

Kotlin DSL 은 다음의 방법으로 structure를 구성

- Kotlin DSL은 nested lambda call.
- chained method call.
  - 예시
  ```kotlin
  // DSL
  str should startWith("kot")

  // plain API
  assertTrue(str.startsWith("kot"))
  ```

### Building HTML with an internal DSL

DSL

```kotlin
fun createSimpleTable() = createHTML().
  table {
    tr {
    td { +"cell" }
  }
}
```

실제 생성하는 HTML

```html
<table>
  <tr>
    <td>cell</td>
  </tr>
</table>
```

- DSL을 이용하면 기존 HTML에는 없는 kotlin 공유의 기능을 누릴 수 있음.
  - for, while loop 등을 사용해서 td 생성.
  - type check 를 활용해서 td 안에 호출할 수 있는 method를 제약.
  - ...

## 11.2. Building structrued Apis: lambdas with receivers in DSLs

- Lambdas with receivers 기능으로 DSL에서 structure를 구성함.

### Lambdas with receivers and extension function types

- lambda with receiver는 lambda 호출 할 때 호출 대상을 `it`, `this` 같은거로 지정 안하고 직접 호출할 수 있게 함.
  - eg.
    ```kotlin
    fun buildString(
      builderAction: StringBuilder.() -> Unit
    ) : String {
      val sb = StringBuilder()
      sb.builderAction()
      return sb.toString()
    }

    val s = buildString {
      this.append("Hello, ") // using 'it'
      append("World!") // no 'it'
    }
    ```
- 여기서 인자를 extension function type이라고 부름.

extension function type

- 구조 : `String.(Int, Int) -> Unit`
  - receiver type : `String.(Int, Int) -> Unit`
  - receiver object : `String`
  - parameter type: `(Int, Int)`
  - return value : `Unit`
- 이걸 extension type이라고 부르는 이유는 extention function이 `a.method()`를 `method()`처럼 호출할 수 있게 해줘서임.
- 위의 예시의 `sb.builderAction()`를 보면 extension function처럼 쓰는걸 볼 수 있음.
- 이 타입을 그냥 변수로 정의도 가능.
  ```kotlin
  val appendExcl : StringBuilder.() -> Unit = { this.append("!") } // declaration
  val stringBuilder = StringBuilder("Hi")
  stringBuilder.appendExcl() // usage
  ```

보다 좋은 호출 방식 (직접 builderAction 호출하지 않고 apply std lib 사용)

```kotlin
fun buildString(builderAction: StringBuilder.() -> Unit): String =
  StringBuilder().apply(builderAction).toString()
```

apply, with std library 구조

- 구조
  ```kotlin
  inline fun <T> T.apply(block: T.() -> Unit): T {
    block() // this.block() 이랑 같음
    return this
  }

  inline fun <T, R> with(receiver: T, block: T.() -> R): R =
    receiver.block() // lambda 결과를 리턴.
  ```
- 예시
  ```kotlin
  val map = mutableMapOf(1 to "one")
  map.apply { this[2] = "two"}
  with (map) { this[3] = "three" }
  ```

### Using lambdas with receivers in HTML builders

todo

## 11.3. More flexible block nesting with the 'invoke' convention

## 11.4. Kotlin DSLs in practice