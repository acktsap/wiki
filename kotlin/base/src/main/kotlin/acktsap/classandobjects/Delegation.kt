package acktsap.classandobjects

import acktsap.Block

interface Base1 {
    fun println()
}

interface Base2 {
    val message: String
    fun println()
    fun printMessage()
}

fun main() {
    Block("Delegation") {
        class BaseImpl(private val x: Int) : Base1 {
            override fun println() = println(x)
        }

        class Derived(b: Base1) : Base1 by b

        val derived = Derived(BaseImpl(10))
        derived.println() // 10
    }

    Block("Delegation with override") {
        class BaseImpl(private val x: Int) : Base2 {
            override val message: String = "BaseImpl message"

            override fun println() = println(x)

            override fun printMessage() = println(message)
        }

        class Derived(b: Base2) : Base2 by b {
            // This property is not accessed from b's implementation of `printMessage`
            override val message: String = "Derived message"

            // overridden
            override fun println() = println("abc")
        }

        val derived = Derived(BaseImpl(10))
        derived.println() // abc
        derived.printMessage() // BaseImpl message
    }

    Block("Delegation to a generic") {
        println(
            """
            Delegation to a generic interface is impossible.
            Since jvm cannot represent a class with type parameter as a superclass.
            see https://discuss.kotlinlang.org/t/class-delegation-to-generic/4681
            """.trimIndent()
        )
        // fails to compile
        // class SomeClass<T>(t: T) : T by t
    }
}
