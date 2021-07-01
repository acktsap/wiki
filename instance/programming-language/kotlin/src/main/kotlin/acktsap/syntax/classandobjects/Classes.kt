package acktsap.syntax.classandobjects

import acktsap.Block

private annotation class TestAnnotation

fun main() {
    Block("Class Declaration") {
        class Invoice { /* ... */ }

        // header and the body are optional
        class Empty
    }

    Block("Primary Constructor") {
        class Person constructor(firstName: String) { /*...*/ }

        // constructor keyword can be omitted:
        class Person2(firstName: String) { /*...*/ }

        // init block. executed in the same order as code order
        class InitOrderDemo(name: String) {
            // parameters of the primary constructor can be used in the initializer blocks
            val firstProperty = "First property: $name".also(::println) // 1

            init {
                println("First initializer block that prints $name") // 2
            }

            val secondProperty = "Second property: ${name.length}".also(::println) // 3

            init {
                println("Second initializer block that prints ${name.length}") // 4
            }
        }
        InitOrderDemo("tester")

        // multiple property
        class Person3(
            val firstName: String,
            val lastName: String,
            var age: Int, // trailing comma
        ) { /*...*/ }

        // constructor with annotations or visibility modifiers,
        class Customer @TestAnnotation public constructor(name: String) { /*...*/ }
    }

    Block("Secondary Constructor") {
        // secondary constructor needs to delegate to the primary constructor
        class Person(val name: String) {
            var children: MutableList<Person> = mutableListOf()

            // this(name) : delegation to primary constructor
            constructor(name: String, parent: Person) : this(name) {
                parent.children.add(this)
            }
        }

        // implicit delegation to primary constructor
        class Constructors {
            init {
                println("Init block") // 1
            }

            constructor(i: Int) {
                println("Constructor $i") // 2
            }
        }
        Constructors(3)
    }

    Block("Declaring Private Constructor") {
        class DontCreateMe private constructor() { /*...*/ }
    }

    Block("Constructor with Default Value") {
        // the compiler will generate an additional parameterless constructor with default value.
        class Customer(val name: String = "") {
            override fun toString(): String {
                return "Customer(name = $name)"
            }
        }

        println(Customer())
        println(Customer("tester"))
    }

    Block("Creating Class Instance") {
        class Invoice(val amount: Long = 0L) {
            override fun toString(): String {
                return "Invoice(amount = $amount)"
            }
        }

        val invoice1 = Invoice()
        val invoice2 = Invoice(100)
    }

    Block("Abstract Class") {
        open class Polygon {
            open fun draw() {}
        }

        // no need to use keyword 'open'
        abstract class Rectangle : Polygon() {
            abstract override fun draw()
        }
    }
}
