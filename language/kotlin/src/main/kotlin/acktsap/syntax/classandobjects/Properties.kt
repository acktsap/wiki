package acktsap.syntax.classandobjects

import acktsap.Block

// compile time constant
const val CONSTANT = "abc"

@Suppress("DeprecatedCallableAddReplaceWith")
fun main() {
    Block("Declaring properties") {
        class Address {
            val name: String = "Holmes, Sherlock" // read-only
            var street: String = "Baker"
        }

        // usage
        val address = Address()
        println("address.name: ${address.name}")
        println("address.street: ${address.street}")

        address.street = "Road"
        println("address.street (after modification): ${address.street}")
    }

    Block("Getters and setters") {
        class A {
            // has type Int, default getter and setter. type is inferred from initializer
            var initialized = 1

            // ERROR: explicit initializer required, default getter and setter implied
            // var allByDefault 

            // has type Int, default getter, must be initialized in constructor
            val simple: Int?

            // has type Int and a default getter
            val inferredType = 1

            // private setter
            var privateSetter: String = "abc"
                private set

            init {
                simple = 1
            }
        }

        val a = A()

        println("initialized: ${a.initialized}")
        a.initialized = 3
        println("initialized (after set): ${a.initialized}")

        println("simple: ${a.simple}")
        println("inferredType: ${a.inferredType}")
        println("privateSetter: ${a.privateSetter}")

        // failed since its private
        // a.privateSetter = 3 
    }

    Block("Computed getter and setter") {
        class A(val size: Int) {
            // custom getter, called everytime. Implement computed property
            val isEmpty: Boolean
                get() = this.size == 0

            // custom getter and setter
            var stringRepresentation: String
                get() = this.valueHolder
                set(value) { // convention name is 'value'
                    setDataFromString(value) // parses the string and assigns values to other properties
                }

            private var valueHolder: String = "abc"

            private fun setDataFromString(value: String) {
                this.valueHolder = "$value-postfix"
            }
        }

        val a = A(10)
        println("isEmpty: ${a.isEmpty}")
        println("stringRepresentation: ${a.stringRepresentation}")
        a.stringRepresentation = "tt"
        println("stringRepresentation (after set): ${a.stringRepresentation}")
    }

    Block("Backing field") {
        // a field is used to hold its value in memory
        // we can access it using 'field'
        class A(val size: Int) {
            var counter = 0 // the initializer assigns the backing field directly
                set(value) {
                    if (value >= 0) {
                        field = value
                        // ERROR StackOverflow: Using actual name 'counter' would make setter recursive
                        // counter = value 
                    }
                }

            // A backing field will be generated for a property if it uses the default implementation
            // or if a custom accessor references it through the field identifier.
            // So in this case, backing field will not be generated
            val isEmpty: Boolean
                get() = this.size == 0
        }

        val a = A(10)
        println("counter: ${a.counter}")
        a.counter = 3
        println("counter (after set): ${a.counter}")
        println("isEmpty: ${a.isEmpty}")
    }

    Block("Backing properties") {
        class A {
            // backing property
            private var _table: Map<String, Int>? = null

            val table: Map<String, Int>
                get() {
                    if (_table == null) {
                        _table = HashMap()
                    }
                    return _table ?: throw AssertionError("Set to null by another thread")
                }
        }

        val a = A()
        println("table: ${a.table}")
    }

    Block("Compile-time constants") {
        println("Compile time constant: $CONSTANT")

        // compile time constant can be used in annotation
        @Deprecated(CONSTANT)
        fun foo() = println("foo")
        foo()
    }

    Block("Late-initialized properties and variables") {
        class A {
            // not throws error
            lateinit var value: String

            fun print() {
                if (::value.isInitialized) {
                    println("value: $value")
                } else {
                    println("value is not initialized")
                }
            }
        }

        val a = A()
        a.print()
        a.value = "abc"
        a.print()
    }
}
