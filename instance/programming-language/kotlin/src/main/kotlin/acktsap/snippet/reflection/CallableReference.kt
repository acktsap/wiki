package acktsap.snippet.reflection

import acktsap.Block
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

const val firstClassObjectX = 1
var firstClassObjectY = 2

val String.lastChar: Char
    get() = this[length - 1]

fun main() {
    Block("Function Reference") {
        fun isOdd(x: Int) = x % 2 != 0
        val numbers = listOf(1, 2, 3)

        println(numbers.filter(::isOdd))
    }

    Block("Function Reference With Receiver") {
        val isEmptyStringList: List<String>.() -> Boolean = List<String>::isEmpty

        println(isEmptyStringList(listOf()))
    }

    Block("Function Composition using Function Reference") {
        fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
            return { x -> f(g(x)) }
        }

        fun isOdd(x: Int) = x % 2 != 0
        fun length(s: String) = s.length

        val oddLength = compose(::isOdd, ::length)
        val strings = listOf("a", "ab", "abc")

        println(strings.filter(oddLength))
    }

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

    Block("Constructor references") {
        data class Foo(private val p: Int = 1)

        fun test0(factory: () -> Foo) {
            val foo: Foo = factory()
            println("Foo: $foo")
        }

        fun test1(factory: (Int) -> Foo) {
            val foo: Foo = factory(2)
            println("Foo: $foo")
        }

        test0(::Foo)
        test1(::Foo)
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

    Block("Bound constructor references") {
        class Outer {
            inner class Inner
        }

        val o = Outer()
        val boundInner = o::Inner
        println("boundInnerClass: $boundInner")
    }

    // use api in a package 'kotlin.reflect.jvm'
    Block("Interoperability with Java reflection") {
        class A(val p: Int)

        println("javaGetter: ${A::p.javaGetter}") // prints "public final int A.getP()"
        println("javaField: ${A::p.javaField}") // prints "private final int A.p"

        val a = A(3)

        val javaClass = a.javaClass
        println("javaClass: $javaClass")

        val kClass: KClass<A> = javaClass.kotlin
        println("kClass: $kClass")
    }
}
