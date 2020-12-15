package acktsap.core.clazz

import acktsap.core.Spec
import acktsap.core.external.externalExtension

/*
    the ability to extend a class with new functionality
    without having to inherit from the class or use design patterns such as Decorator
 */


/* Extension properties (local extension of properties is not allowed) */

val <T> List<T>.lastIndex: Int
    get() = size - 1

/* Extension properties (setter) */

var value: String? = null
var String.cabinet: String
    get() = value ?: "<<empty>>"
    set(v) {
        value = v
    }

/* Companion object extensions (companion inside local class is now allowed) */

class MyClass {
    companion object {
    }
}

fun MyClass.Companion.print() = println("Companion")

fun main() {
    Spec("function extension") {
        fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
            // can use this keyword
            val tmp = this[index1]
            this[index1] = this[index2]
            this[index2] = tmp
        }

        val list = mutableListOf(1, 2)
        println(list) // 1, 2
        list.swap(0, 1)
        println(list) // 2, 1
    }

    Spec("extensions are resolved statically") {
        open class Shape

        class Rectangle : Shape()

        fun Shape.getName() = "Shape"

        fun Rectangle.getName() = "Rectangle"

        // calls 'Shape.getName()'
        fun printClassName(s: Shape) = println(s.getName())
        printClassName(Rectangle()) // Shape
    }

    Spec("function extension on member") {
        class Example {
            // the member always wins on extention
            fun method() = println("Class method")
        }

        // never called since the member always wins
        fun Example.method() = print("Extension method")

        // with different signature is allowed
        fun Example.method(message: String) = println(message)

        val example = Example()
        example.method() // Class method
        example.method("Hey home") // Hey home
    }

    Spec("Nullable receiver") {
        fun Any?.beautify(): String {
            if (this == null) return "Hey null"
            return toString()
        }

        val nullable: String? = null
        println(nullable.beautify()) // Hey null
    }

    Spec("Extension properties") {
        println(listOf(1, 2, 3).lastIndex) // 2
    }

    Spec("Extension properties (with setter)") {
        val container = "Container"
        println(container.cabinet) // <<empty>>
        container.cabinet = "keep"
        println(container.cabinet) // keep
    }

    Spec("Companion object extensions") {
        MyClass.print() // Companion
    }

    Spec("Scope of extensions") {
        // need import
        listOf<Int>().externalExtension() // Hey external one
    }

    Spec("Declaring extensions as members") {
        // todo: https://kotlinlang.org/docs/reference/extensions.html#declaring-extensions-as-members
    }
}
