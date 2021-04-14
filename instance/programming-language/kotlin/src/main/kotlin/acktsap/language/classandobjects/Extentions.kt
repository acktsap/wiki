package acktsap.language.classandobjects

import acktsap.Block
import acktsap.language.classandobjects.external.externalExtension

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
    Block("Function extension") {
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

    Block("Extensions are resolved statically") {
        open class Shape

        class Rectangle : Shape()

        fun Shape.getName() = "Shape"

        fun Rectangle.getName() = "Rectangle"

        // calls 'Shape.getName()'
        fun printClassName(shape: Shape) = println(shape.getName())

        printClassName(Rectangle()) // Shape
    }

    Block("Function extension on member") {
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

    Block("Nullable receiver") {
        fun Any?.beautify(): String {
            if (this == null) {
                return "Hey null"
            }
            return toString()
        }

        val nullable: String? = null
        println(nullable.beautify()) // Hey null
    }

    Block("Extension properties") {
        println(listOf(1, 2, 3).lastIndex) // 2
    }

    Block("Extension properties (with setter)") {
        val container = "Container"
        println(container.cabinet) // <<empty>>
        container.cabinet = "keep"
        println(container.cabinet) // keep
    }

    Block("Companion object extensions") {
        MyClass.print() // Companion
    }

    Block("Scope of extensions") {
        // need import
        listOf<Int>().externalExtension() // Hey external one
    }

    Block("Declaring extensions as members") {
        class Host(val hostName: String)

        class Connection(val host: Host, val port: Int) {
            // extension as member
            fun Host.connectionInfo() = "$hostName:$port"

            fun connect() {
                // use extension
                println("Connecting to ${host.connectionInfo()}..")
            }

            // on name collision, extension receiver takes precedence
            fun Host.nameCollision() {
                println(toString()) // calls Host.toString
                println(this@Connection.toString()) // calls Connection.toString
            }

            fun nameCollision() {
                host.nameCollision()
            }
        }

        val host = Host("localhost")
        val connection = Connection(host, 8080)

        connection.connect() // Connecting to localhost:8080..

        // error, extension is inside Connection class
        // host.printConnection()

        connection.nameCollision()
    }

    Block("Declaring extensions as members as open") {
        open class Base

        class Derived : Base()

        open class BaseCaller {
            // base extension
            open fun Base.print() = println("Base extension in BaseCaller")

            open fun Derived.print() = println("Derived extension in BaseCaller")

            fun call(b: Base) = b.print()
        }

        class DerivedCaller : BaseCaller() {
            // overridden extension
            override fun Base.print() = println("Base extension in DerivedCaller")

            override fun Derived.print() = println("Derived extension in DerivedCaller")
        }

        // extension receiver is resolved statically
        BaseCaller().call(Base()) // Base extension  in BaseCaller
        // extension receiver is resolved statically
        BaseCaller().call(Derived()) // Base extension in BaseCaller
        // dispatch receiver is resolved virtually
        DerivedCaller().call(Base()) // Base extension in DerivedCaller
        // extension receiver is resolved statically
        DerivedCaller().call(Derived()) // Base extension in DerivedCaller
    }
}
