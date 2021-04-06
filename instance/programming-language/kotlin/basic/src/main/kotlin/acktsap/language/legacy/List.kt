package legacy

// sealed: 이 안에 있는거는 대수적으로 셜계했다 안바뀜~!~!
abstract sealed class List<T> {
  abstract fun add(e: String): List<Any?>
  abstract fun get(i: Int): String?
  abstract fun remove(i: Int): List<Any?>
}

object EmptyList : List<Any?>() {
  override fun add(e: String): List<Any?> {
    return Conc(e, EmptyList)
  }

  override fun get(i: Int): String? {
    return null
  }

  override fun remove(i: Int): List<Any?> {
    return this
  }

  override fun toString(): String {
    return "<<tail>>"
  }
}

enum class B {
  a1, b1
}

// same as enum class
sealed class C {
}

// better for computer
// ram is better than sqllite
// 기계친화적인 코드임
class Conc(val v: String, val tail: List<Any?>) : List<Any?>() {
  override fun add(e: String): List<Any?> {
    return Conc(v, tail.add(e))
  }

  override fun get(i: Int): String? {
    return if (0 == i) {
      v
    } else {
      tail.get(i - 1)
    }
  }

  override fun remove(i: Int): List<Any?> {
    return if (0 == i) {
      tail
    } else {
      tail.remove(i - 1)
    }
  }

  override fun toString(): String {
    return "$v, $tail"
  }
}

fun main() {
  val list = EmptyList.add("Hello").add("world")
  println(list)
}