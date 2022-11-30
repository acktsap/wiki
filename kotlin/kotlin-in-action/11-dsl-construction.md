# 11. DSL construction

- [11.1. From APIs to DSLs](#111-from-apis-to-dsls)
  - [The concept of domain-specific languages](#the-concept-of-domain-specific-languages)
  - [Internal DSLs](#internal-dsls)
  - [Structure of DSLs](#structure-of-dsls)
  - [Building HTML with an internal DSL](#building-html-with-an-internal-dsl)
- [11.2. Building structrued Apis: lambdas with receivers in DSLs](#112-building-structrued-apis-lambdas-with-receivers-in-dsls)
  - [Lambdas with receivers and extension function types](#lambdas-with-receivers-and-extension-function-types)
  - [Using lambdas with receivers in HTML builders](#using-lambdas-with-receivers-in-html-builders)
  - [Kotlin builders: enabling abstraction and reuse](#kotlin-builders-enabling-abstraction-and-reuse)
- [11.3. More flexible block nesting with the 'invoke' convention](#113-more-flexible-block-nesting-with-the-invoke-convention)
  - [The "invoke" convention: objects callable as functions](#the-invoke-convention-objects-callable-as-functions)
  - [The "invoke" convention and functional types](#the-invoke-convention-and-functional-types)
  - [The "invoke" convention in DSLs: declaring dependencies in Gradle](#the-invoke-convention-in-dsls-declaring-dependencies-in-gradle)
- [11.4. Kotlin DSLs in practice](#114-kotlin-dsls-in-practice)
  - [Chaining infix calls: “should” in test frameworks](#chaining-infix-calls-should-in-test-frameworks)
  - [Defining extensions on primitive types: handling dates](#defining-extensions-on-primitive-types-handling-dates)
  - [Member extension functions: internal DSL for SQL](#member-extension-functions-internal-dsl-for-sql)
  - [Anko: creating Android UIs dynamically](#anko-creating-android-uis-dynamically)

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

- Kotlin DSL은 type-safe builder의 개념을 사용. 이 개념은 groovy에서 나왔는데 object 생성 계층을 선언형으로 할 수 있게 함.
- Kotlin DSL은 groovy와는 다르게 type-safe함.

Html Builder

```kotlin
fun createSimpleTable() = createHTML().
    table {
        tr {
            td { +"cell" }
        }
    }
```

- 이거 그냥 함수 호출임. tr, td 전부 그냥 함수 호출
- lambda with receiver를 인자로 받는 higher order function 함수 호출
- 이 lambda들은 name-resolution rule을 바꿈. 예를 들면 td는 tr block 안에서만 사용할 수 있음.
- name-resolution context는 receiver type에 의해 정해짐.

실제 명시를 다 하면 이렇게 되는거임.

```kotlin
fun createSimpleTable() = createHTML().
    table {
        (this@table).tr {
            (this@tr).td {
                +"cell"
            }
        }
    }
```

- lambda with receiver 안쓰고 그냥 쓰면 `it`, `this` 같은 부가적인 코드가 생겨서 읽기 힘들어짐.
- lambda with receiver 짱임.

그런데 위의 코드의 경우 tr 안에서 table의 함수도 사실 호출 가능함. 이걸 `@DslMarker`이라는 annotation을 붙여주면 제약을 걸 수 있음.

실제 전체 코드임.

```kotlin
open class Tag(val name: String) {
    private val children = mutableListOf<Tag>() // store all nested tags

    protected fun <T : Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child) // store a reference to the child tag
    }

    override fun toString() =
        "<$name>${children.joinToString("")}</$name>" // return resulting HTML as string 
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class TABLE : Tag("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}

class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}

class TD : Tag("td")

// usage
fun createTable() =
    table {
        tr {
            td {
            }
        }
    }
```

Kotlin DSL을 쓰면 이렇게 동적으로도 생성 가능.

```kotlin
fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td {
            }
        }
    }
}
```

- 결론적으로 lambda with receiver는 code block의 name-resolution context를 제한하고 API의 structure를 만들 수 있는 기능을 제공함.

### Kotlin builders: enabling abstraction and reuse

- 우리는 코드를 짤 때 원래 중복 제거하려고 함수 빼고 그럼.
- Kotlin internal DSL을 쓰면 반복되는 코드를 보다 명확하게 뺄 수 있게 함.

순수 html

```html
<div class="dropdown">
    <button class="btn dropdown-toggle">
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
        <li><a href="#">Action</a></li>
        <li><a href="#">Another action</a></li>
        <li role="separator" class="divider"></li>
        <li class="dropdown-header">Header</li>
        <li><a href="#">Separated link</a></li>
    </ul>
</div>
```

Kotlin DSL

```kotlin
fun buildDropdown() = createHTML().div(classes = "dropdown") {
    button(classes = "btn dropdown-toggle") {
        +"Dropdown"
        span(classes = "caret")
    }
    ul(classes = "dropdown-menu") {
        li { a("#") { +"Action" } }
        li { a("#") { +"Another action" } }
        li { role = "separator"; classes = setOf("divider") }
        li { classes = setOf("dropdown-header"); +"Header" }
        li { a("#") { +"Separated link" } }
    }
}
```

한번 더 추상화된 Kotlin DSL. 이렇게 internal DSL도 extension을 활용하여 method로 뺄 수 있음.

```kotlin
// definition
fun UL.item(href: String, name: String) = li { a(href) { +name } }

fun UL.divider() = li { role = "separator"; classes = setOf("divider") }

fun UL.dropdownHeader(text: String) =
li { classes = setOf("dropdown-header"); +text }

fun DIV.dropdownMenu(block: UL.() -> Unit) = ul("dropdown-menu", block)

...

// usage
fun dropdownExample() = createHTML().dropdown {
    dropdownButton { +"Dropdown" }
    dropdownMenu {
        item("#", "Action")
        item("#", "Another action")
        divider()
        dropdownHeader("Header")
        item("#", "Separated link")
    }
}
```

## 11.3. More flexible block nesting with the 'invoke' convention

### The "invoke" convention: objects callable as functions

- `get`이 `foo.get(bar)` 말고 `foo[bar]`처럼 쓸 수 있는 것처럼 `invoke`도 마찬가지임.
- `invoke` operator의 형식에는 제약이 없음. 인자, 리턴타입을 마음대로 지정할 수 있음.

```kotlin
class Greeter(val greeting: String) {
    // definition
    operator fun invoke(name: String) {
        println("$greeting, $name!")
    }
}

// usage
val bavarianGreeter = Greeter("Servus")
bavarianGreeter("Dmitry") // prints "Servus, Dmitry"
```

### The "invoke" convention and functional types

invoke 함수를 쓰면 긴 lambda를 깔끔하게 refactoring 할 수 있음.

```
// definition
data class Issue(
    val id: String, val project: String, val type: String,
    val priority: String, val description: String
)

class ImportantIssuesPredicate(val project: String) : (Issue) -> Boolean {
    override fun invoke(issue: Issue): Boolean {
        return issue.project == project && issue.isImportant()
    }

    private fun Issue.isImportant(): Boolean {
        return type == "Bug" && (priority == "Major" || priority == "Critical")
    }
}

// usage
val i1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save settings failed")
val i2 = Issue("KT-12183", "Kotlin", "Feature", "Normal", "Intention: convert several calls on the same receiver to with/apply")
val predicate = ImportantIssuesPredicate("IDEA")
for (issue in listOf(i1, i2).filter(predicate)) {
    println(issue.id)
}
IDEA-154446
```

### The "invoke" convention in DSLs: declaring dependencies in Gradle

다음의 두 가지 케이스를 모두 지원하고 싶을 때 invoke를 활용.

```kotlin
dependencies.compile("junit:junit:4.11") // plain method call

dependencies { // using invoke
    compile("junit:junit:4.11")
}
```

정의는 다음과 같음.

```kotlin
class DependencyHandler {
    fun compile(coordinate: String) {
        println("Added dependency on $coordinate")
    }

    operator fun invoke(body: DependencyHandler.() -> Unit) {
        body()
    }
}
```

이 두개 code block은 동일함.

```kotlin
dependencies { // using invoke
    compile("junit:junit:4.11")
}

dependencies.invoke({
    this.compile("org.jetbrains.kotlin:kotlin-reflect:1.0.0")
})
```

## 11.4. Kotlin DSLs in practice

### Chaining infix calls: “should” in test frameworks

infix 예시1.

```kotlin
// definition
interface Matcher<T> {
    fun test(value: T)
}

infix fun <T> T.should(matcher: Matcher<T>) = matcher.test(this)

class startWith(val prefix: String) : Matcher<String> { // DSL 작성할때는 class 이름이 대문자로 시작 안해도 괜찮음
    override fun test(value: String) {
        if (!value.startsWith(prefix)) {
            throw AssertionError("String $value does not start with $prefix")
        }
    }
}

// usage
s should startWith("kot")
```

infix 예시2.

```kotlin
object start

infix fun String.should(x: start): StartWrapper = StartWrapper(this)

class StartWrapper(val value: String) {
    infix fun with(prefix: String) =
        if (!value.startsWith(prefix))
            throw AssertionError("String does not start with $prefix: $value")
}

// usage
"kotlin" should start with "kot"

// actual
"kotlin".should(start).with("kot")
```

### Defining extensions on primitive types: handling dates

std api에 extension 달아서 DSL 만든 예시.

```kotlin
// definition
val Int.days: Period
    get() = Period.ofDays(this)

val Period.ago: LocalDate
    get() = LocalDate.now() - this

val Period.fromNow: LocalDate
    get() = LocalDate.now() + this

// usage
val yesterday = 1.days.ago
val tomorrow = 1.days.fromNow
```

### Member extension functions: internal DSL for SQL

member extension(class에 extension 만드는거)를 사용한 예시1.

```kotlin
// definition
class Table {
    fun integer(name: String): Column<Int>
    fun varchar(name: String, length: Int): Column<String>

    fun <T> Column<T>.primaryKey(): Column<T>
    fun Column<Int>.autoIncrement(): Column<Int>
    ...
}

object Country : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
}

// usage
SchemaUtils.create(Country)

// result
/*
CREATE TABLE IF NOT EXISTS Country (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_Country PRIMARY KEY (id)
)
*/
```

- member extension은 그 class내에만 한정되기 때문에 추가적인 member extension을 정의할 수 없다는 단점이 있음.
- 예를 들면 위 케이스의 경우 새로운 column type이 추가되어도 `integer`, `varchar` 이런 dsl을 정의할 수 없음. 왜냐하면 extension은 Table 객체 내부에 접근을 할 수 없기 때문임. 새로운 column type을 처리하려면 접근이 필요함.

member extension를 사용한 예시2.

```kotlin
// definition
fun Table.select(where: SqlExpressionBuilder.() -> Op<Boolean>) : Query

object SqlExpressionBuilder {
    infix fun<T> Column<T>.eq(t: T) : Op<Boolean>
    // ...
}

// usage
val result = (Country join Customer)
    .select { Country.name eq "USA" }
result.forEach { println(it[Customer.name]) }
```

### Anko: creating Android UIs dynamically

android layout DSL 예시

```kotlin
// definition
fun Context.alert(
    message: String,
    title: String,
    init: AlertDialogBuilder.() -> Unit
)

class AlertDialogBuilder {
    fun positiveButton(text: String, callback: DialogInterface.() -> Unit)
    fun negativeButton(text: String, callback: DialogInterface.() -> Unit)
    // ...
}

// usage
fun Activity.showAreYouSureAlert(process: () -> Unit) {
    alert(title = "Are you sure?", message = "Are you really sure?") {
        positiveButton("Yes") { process() }
        negativeButton("No") { cancel() }
    }
}
```