package legacy

import java.io.FileInputStream
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

typealias MyLambda = (a: String, b: Int, c: Int) -> Int


fun main() {
  // form
  val l1: (a: Int, b: Int) -> Int = { a: Int, b: Int -> a + b }
  val l2: (a: Int, b: Int) -> Unit = { a: Int, b: Int -> a + b }
  val l3: (Int, Int) -> Unit = { a, b -> a + b }
  val l4 = { a: Int, b: Int -> a + b }

  // alias for long form
  // _ -> not use
  val l5: MyLambda = { _, b, c -> b + c }
  val l6: MyLambda = { _, _, c -> c }

  // high order function: takes lambda as argument
  fun iWillUse(lambda: MyLambda) {

  }

  // currying last element
  fun isRunnable(a: String, b: Int, lambda: MyLambda): Boolean {
    return true
  }
  if (isRunnable("", 3) { a, b, c -> 0 }) {

  }

  // currying all element -> used in dsl
  fun isRunnable2(lambda: MyLambda): Boolean {
    return true
  }
  if (isRunnable2 { a, b, c -> 0 }) {

  }

  class Person() {
    val name: String = ""
    var age: Int = 0
    fun calculateScore(a: Int): Int {
      return a * 3
    }
  }

  val p1 = Person()

  // with impl
  fun <T, R> with2(n: T, block: T.() -> R): R {
    return n.block()
  }
  // with
  with(p1) {
    this.name
  }

  val a = 3

  class Context {
    val contextValue = 3
    fun main() {
      /*
          receiver   it   this
                    let   also      block return
                    run   apply     receiver
                                    return
       */

      // this, it are the same (just a name, 0 slot of stack frame)

      // let (used in grasp creator pattern, creating object)
      val letValue: Int = p1.let {
        // can see (outside variable, this, object itself)
        a + this.contextValue + it.age
      }
      // used anywhere
      Person().let { "" }

      // run
      val runValue: Int = p1.run {
        // this = it
        a + contextValue + this.age
      }
      // used when set value of itself & run with it
      Person().run { this.age = 33; "" }
      Person().takeIf { it.age < 10 }?.name ?: run { "" }

      // apply
      val applyValue: Person = p1.apply {
        a + contextValue + this.age
      }
      // used when set value of itself (change value inside object)
      Person().apply { this.age = 33 }

      // also
      val alsoValue: Person = p1.also {
        a + contextValue + it.age
      }
      // used when do something not modifying itself
      Person().also { it.age }


      // etcs (remove boilerplate code)

      FileInputStream("/usr/abc").use { inputStream ->
        inputStream.read()
      }

      ReentrantLock().withLock {
        // do something
      }

      repeat(5) { i ->
        println(i)
      }
    }
  }

}
