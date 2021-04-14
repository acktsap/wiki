// @file:JvmName(name: "Func")

package legacy

import java.io.IOException

fun add(a: Int, b: Int): Int {
    return a + b
}

// return type inference
fun add2(a: Int, b: Int) = a + b

// default argument
fun add3(a: Int = 1, b: Int = 0) = a + b

// generate 3 methods
@JvmOverloads
fun add4(a: Int = 1, b: Int = 0) = a + b

fun addException(a: Int = 1, b: Int = 0): Int {
    println("A: $a, B: $b")
    throw IOException()
}

@Throws(IOException::class)
fun useRun() {
    try {
//    JavaFunc().run2()
    } catch (e: IOException) {
    }
}

// internal, private, public keyword

class ClassMethod {
    // internal, private, public, protected keyword
    fun add(a: Int, b: Int): Int {
        // invisible
        // innerAdd(1, 2),
        // local function
        fun innerAdd(a: Int, b: Int): Int {
            return 0
        }
        innerAdd(1, 2)
        return a + b
    }
}

// additional method to String type
fun String.lastChar(): Char {
    return this[this.length - 1]
}

data class Student(val name: String, val score0: Int, val score1: Int) {
    private var secret: String = ""
    // not like this
//    fun rank(): Int {
//        return 3
//    }
}

// like this, function extensions
fun Student.total1(): Int {
    return score0 + score1
}

fun Student.total2(): Int {
    return (score0 + score1) * 10
}

// no overloading, can be used (limitation 1)
fun String.substring(a: Int, b: Int): String {
    return ""
}

fun Student.total3(): Int {
    // can't access this since Student.total1 is a static function (limitation 2)
    // this.secret

    // can use this
    this.total1()
    this.total2()

    return score0 + score1
}

open class Human

fun Human.getJob(): String {
    return "I don't know"
}

class Police : Human()

fun Police.getJob(): String {
    return "Police"
}

class Criminal : Human()

fun Criminal.getJob(): String {
    return "No job"
}

// inline function, no use (no java compatibility)
inline fun test(a: Int, b: Int): Int {
    return a + b
}

// compile it into throw UnSupportedOperationException()
// @InlineOnly
inline fun test2(a: Int, b: Int): Int {
    return a + b
}

class My {
    infix fun fix(my: My): My {
        return My()
    }

    fun run() {
        My() fix My()
    }
}

fun main(args: Array<String>) {
    println("${add(1, 2)}") // 3
    println("${add2(3, 4)}") // 7
    println("${add3(2)}") // 2
    println("${add3(b = 3)}") // 4

    println(Police().getJob()) // Police
    println(Criminal().getJob()) // No job

    // limitation 3 (don't print 'No job')
    // no inheritance (no polymorphism, inheritance isn't 'is a')
    val human: Human = Police()
    println(human.getJob()) // i don't know

    // kotlin regard all list, set, map, array as an array

    // default: immutable list
    val list = listOf(3, 5, 5)

    val mutable = mutableListOf(3, 5, 5)
    mutable.add(3)

    // a fake
    (list as MutableList).add(3)

    // string
    val a = "I am a student"
    // many methods
    // a.filter {  }

    // inline function
    val b = test(3, 4)
}
