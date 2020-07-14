package api

object KSingleton {
  fun foo() {

  }
}

class Comp {
  fun foo() {

  }

  companion object {
    fun bar() {

    }
  }
}

open class singleton2 {
  open fun foo() {

  }
}

fun main() {
  // KSingleton.INSTANCE.foo() in java. like single pattern in java
  KSingleton.foo()

//  Comp().bar()

  val a = object : singleton2() {
    override fun foo() {

    }
  }
}