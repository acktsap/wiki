package acktsap.syntax.classandobjects

import acktsap.Block

private annotation class TestAnnotation

@Suppress("ConvertSecondaryConstructorToPrimary")
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

        val demo = InitOrderDemo("tester")
        println("demo.firstProperty: ${demo.firstProperty}")
        println("demo.secondProperty: ${demo.secondProperty}")

        // multiple property
        class Person3(
            val firstName: String,
            val lastName: String,
            var age: Int, // trailing comma can be placed
        ) { /*...*/ }
    }

    Block("Primary constructor with annotation") {
        class Customer @TestAnnotation public constructor(name: String) { /*...*/ }
    }

    Block("Declaring Private Primary Constructor") {
        class DontCreateMe private constructor() { /*...*/ }
    }

    Block("Secondary Constructor") {
        // secondary constructor needs to delegate to the primary constructor
        class Person(val name: String) {
            var children = mutableListOf<Person>()

            // this(name) : delegation to primary constructor
            constructor(name: String, parent: Person) : this(name) {
                parent.children.add(this)
            }

            override fun toString(): String {
                return "Person(name=$name, children=$children)"
            }
        }

        val parent = Person("god")
        val child = Person("slave", parent)
        println("parent: $parent")
        println("child: $child")

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

    Block("Constructor with Default Value") {
        /**
         * If all of the primary constructor have default values, the compiler will benerate an additional parameterless constructor.
         *
         * That is, it creates
         *
         * ```
         * class Customer {
         *     private String name;
         *
         *     public Customer() {
         *       this.name = "tt";
         *     }
         *
         *     public Customer(String name) {
         *       this.name = name;
         *     }
         * }
         * ```
         */
        class Customer(val name: String = "tt") {
            override fun toString(): String {
                return "Customer(name=$name)"
            }
        }

        val customer1 = Customer()
        val customer2 = Customer("tester")
        println("customer with default: $customer1")
        println("customer with args: $customer2")
    }

    Block("Abstract Class") {
        open class Polygon {
            open fun draw() {}
        }

        // no need to use keyword 'open'
        abstract class Rectangle : Polygon() {
            abstract override fun draw()
        }

        class Test : Rectangle() {
            override fun draw() {
                println("Test.draw")
            }
        }

        Test().draw()
    }
}
