package legacy

data class SingleValue(val a: Int, val b: Int = 3)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Repeatable // can repeat on the same target
@MustBeDocumented // must document it
annotation class Fancy(val name: String)

@Target(
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.EXPRESSION,
  AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.SOURCE)
@Repeatable // can repeat on the same target
@MustBeDocumented // must document it
annotation class Fancy2(val name: String)

// param에 붙이겠다!
data class Ex(@param:Fancy2("aaa") val arg: String)

// kapt : annotation가지고 하는 kotlin library

// 생성자가 같으면 enum
enum class DateName {
  Sun, Mon, Twe
}

// 생성자가 다르면 sealed class
// 이것 저것 다 됨
sealed class List2 {
  object Empty : List2() {

  }

  object Conc : List2() {

  }
}

class A2 {
  private val a: Int = 0
}

class A3(private val a: Int) {
}

fun main() {
  println(SingleValue(3))
  println(SingleValue(3) == SingleValue(3))
  println(SingleValue(3).copy(b = 5))

  // can be used in lambda
  val f = @Fancy("Hello") { Thread.sleep(10) }

//  val date = Mon
//  when (date) {
//    Sun -> println("Welcome")
//  }
}