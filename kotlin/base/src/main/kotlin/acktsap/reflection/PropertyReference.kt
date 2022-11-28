package acktsap.reflection

import acktsap.Block
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

private const val firstClassObjectX = 1
private var firstClassObjectY = 2

private val String.lastChar: Char
    get() = this[length - 1]

fun main() {
    Block("First Class Object Property References") {
        val kProperty0: KProperty0<Int> = ::firstClassObjectX
        println("firstClassObjectX name: ${kProperty0.name}")
        println("firstClassObjectX value: ${kProperty0.get()}")

        val kMutableProperty0: KMutableProperty0<Int> = ::firstClassObjectY
        println("firstClassObjectX name: ${kMutableProperty0.name}")
        println("firstClassObjectX value: ${kMutableProperty0.get()}")

        kMutableProperty0.set(20)
        println("firstClassObjectX value after set: ${kMutableProperty0.get()}")
    }

    Block("Passing Property Reference") {
        val strs = listOf("a", "bc", "def")
        println("str lengths: ${strs.map(String::length)}")
    }

    Block("Using Property to Access") {
        class A(val p: Int)

        val a = A(1)

        val prop: KProperty1<A, Int> = A::p
        println("p: ${prop.get(a)}")
    }

    Block("Using Property to Access Extension Property") {
        val kProperty1: KProperty1<String, Char> = String::lastChar
        println("lastChar: ${kProperty1.get("abc")}")
    }

    Block("Bound function and property references") {
        val numberRegex = "\\d+".toRegex()
        println("matches: ${numberRegex.matches("29")}")

        val isNumber: (CharSequence) -> Boolean = numberRegex::matches
        println("isNumber: ${isNumber("29")}")

        val strings = listOf("abc", "124", "a70")
        println("numbers: ${strings.filter(numberRegex::matches)}")

        val prop: KProperty0<Int> = "abc"::length
        println("abc length: ${prop.get()}")
    }
}
