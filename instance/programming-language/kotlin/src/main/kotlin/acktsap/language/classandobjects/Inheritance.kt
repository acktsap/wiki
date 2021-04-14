package acktsap.language.classandobjects

import acktsap.Block

interface Polygon {
    fun draw() { /* ... */
    } // interface members are 'open' by default
}

fun main() {
    Block("Inheritance Basic") {
        // Implicitly inherits from Any
        // So do equals(), hashCode() and toString() of Any
        class Example

        // make class inheritable
        open class Base(p: Int = 3)

        // inheritance
        class Derived1() : Base()
        class Derived2(p: Int) : Base(p)

        // If the derived class has no primary constructor,
        // then each secondary constructor has to initialize base.
        open class View(ctx: String, attrs: Map<String, Any> = mapOf())

        class MyView : View {
            constructor(ctx: String) : super(ctx)

            constructor(ctx: String, attrs: Map<String, Any>) : super(ctx, attrs)
        }
    }

    Block("Overriding methods") {
        // make it open
        open class Shape {
            open fun draw() { /*...*/
            }

            fun fill() { /*...*/
            }
        }

        // override method
        class Circle() : Shape() {
            override fun draw() { /*...*/
            }
        }

        // prevent override
        open class Rectangle() : Shape() {
            final override fun draw() { /*...*/
            }
        }
    }

    Block("Overriding val properties") {
        // make it open
        open class Shape {
            open val vertexCount: Int = 0
        }

        // override property
        class Rectangle : Shape() {
            override val vertexCount = 4
        }

        // override property in primary constructor
        class Rectangle2(override val vertexCount: Int = 4) : Shape()
    }

    Block("Overriding var properties") {
        // make it open
        abstract class Shape {
            abstract val vertexCount: Int
        }

        // can override a val with a var, but not vice versa
        // - val : has get method
        // - overriding as var : just add set method
        class Polygon : Shape() {
            override var vertexCount: Int = 0
        }
    }

    Block("Derived class initialization order") {
        // The base class initialization is first order
        // So, avoid open members in the constructors, property initializers, and init blocks.
        open class Base(val name: String) {

            init {
                println("Initializing a base class")
            } // 2

            open val size: Int = name.length.also {
                println("Initializing size in the base class: $it") // 3
            }
        }

        class Derived(
            name: String,
            val lastName: String,
        ) : Base(
            name.capitalize().also {
                println("Argument for the base class: $it") // 1
            }
        ) {

            init {
                println("Initializing a derived class") // 4
            }

            override val size: Int = (super.size + lastName.length).also {
                println("Initializing size in the derived class: $it") // 5
            }
        }

        Derived("tester", "lim")
    }

    Block("Calling the superclass implementation") {
        // access using 'super'
        open class Rectangle {
            open fun draw() {
                println("Drawing a rectangle")
            }

            val borderColor: String get() = "black"
        }

        class FilledRectangle : Rectangle() {
            override fun draw() {
                super.draw()
                println("Filling the rectangle")
            }

            val fillColor: String get() = super.borderColor
        }

        // access using 'super@Outer`
        class FilledRectangle2 : Rectangle() {
            override fun draw() {
                val filler = Filler()
                filler.drawAndFill()
            }

            inner class Filler {
                fun fill() {
                    println("Filling")
                }

                fun drawAndFill() {
                    super@FilledRectangle2.draw()
                    fill()
                    println("Drawn a with color ${super@FilledRectangle2.borderColor}")
                }
            }
        }
    }

    Block("Overriding rules") {
        // if a class inherits multiple implementations of the same member,
        // it must override this member and provide its own implementation

        open class Rectangle {
            open fun draw() { /* ... */
            }
        }

        class Square() : Rectangle(), Polygon {
            // The compiler requires draw() to be overridden:
            override fun draw() {
                super<Rectangle>.draw() // call to Rectangle.draw()
                super<Polygon>.draw() // call to Polygon.draw()
            }
        }
    }
}
