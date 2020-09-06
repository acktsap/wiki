package legacy

interface Flyable {
  fun fly()
}

interface Walkable {
  fun walk()}

interface Runnable : Walkable {
  fun runnable()
}

// open : overridable
// abstract : must override
// override : override it
// sealed : can't overridable outside of current file

class Fly : Flyable {
  override fun fly() {
  }
}

// 'open' must present
// default is final
open class Vehicle {

}

// no 다중상속, convention: class, interface1, interface2, ...
class Car : Vehicle(), Flyable, Walkable {
  override fun fly() {
  }

  override fun walk() {
  }
}

// really this
// open class Man constructor(name: String, age: Int) {
open class Man(name: String, age: Int) {
  // must call this(name)
  constructor(name: String): this(name, 0)

  // used in this
  val nickname = "$name!!"

  // style guide (based on story)
  val nicknameForever: String
  init {
    nicknameForever = "forever $name"
  }
  fun printNickname() {
    println("Nickname: $nickname")
  }
}

open class NoMan internal constructor(name: String, age: Int) {
}

// val: unaccessable property
open class RealMan(val name: String, val age: Int) {
  fun print() {
    println(name)
  }
}

class Rectangle(val height: Int, val width: Int) {
  constructor(side: Int): this(side, side)
}

fun Square(size: Int): Rectangle {
  return Rectangle(size)
}

// in java, kotlin is the same, but different in property, field
// allocation order : child -> parent
// init order : parent -> child

interface Like {
  val name: String
  fun newMethod(): String
}

// delegate Like impl to like object
class Delegate(like: Like): Like by like {
  override fun newMethod(): String {
    return "Hijacker"
  }
}

fun main() {
  // 1. allocate memory for Man
  // 2. init values (nickname)
  Man("name", 33)
}