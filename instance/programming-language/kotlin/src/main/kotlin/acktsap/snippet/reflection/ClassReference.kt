package acktsap.snippet.reflection

import acktsap.Block
import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

const val firstClassObjectX = 1
var firstClassObjectY = 2

val String.lastChar: Char
    get() = this[length - 1]

fun main() {
    Block("Class References") {
        val clazz = String::class
        println("class: $clazz")
    }

    Block("Bound Class References") {
        val value = "test"
        println("class: ${value::class.qualifiedName}")
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
