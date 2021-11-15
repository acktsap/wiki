package acktsap.syntax.classandobjects

import acktsap.Block

interface Polygon {
    // interface members are 'open' by default
    fun draw() {
        println("Polygon.draw")
    }
}

fun main() {
    Block("Inheritance Basic") {
        // Implicitly inherits from Any
        // So do equals(), hashCode() and toString() of Any
        class Example

        // make class inheritable using 'open'
        open class Base(p: Int = 3)

        // inheritance
        class Derived1() : Base()
        class Derived2(p: Int) : Base(p)

        open class View(ctx: String, attrs: Map<String, Any> = mapOf())

        // If the derived class has no primary constructor,
        // then each secondary constructor has to initialize base.
        class MyView : View {
            constructor(ctx: String) : super(ctx)

            constructor(ctx: String, attrs: Map<String, Any>) : super(ctx, attrs)
        }
    }

    Block("Overriding methods") {
        // make it open
        open class Shape {
            open fun draw() {
                println("Circle.draw")
            }

            fun fill() {}
        }
        Shape().draw()

        // override method
        class Circle() : Shape() {
            override fun draw() {
                println("Circle.draw")
            }
        }
        Circle().draw()

        // prevent override using 'final'
        open class Rectangle() : Shape() {
            final override fun draw() {
                println("Rectangle.draw")
            }
        }
        Rectangle().draw()
    }

    Block("Overriding val properties") {
        // make it open
        open class Shape {
            open val vertexCount: Int = 0
        }

        val shape = Shape()
        println("Shape.vertexCount: ${shape.vertexCount}")

        // override property
        class Rectangle1 : Shape() {
            override val vertexCount = 4
        }

        val rectangle1 = Rectangle1()
        println("Rectangle1.vertexCount: ${rectangle1.vertexCount}")

        // override property in primary constructor
        class Rectangle2(override val vertexCount: Int = 8) : Shape()

        val rectangle2 = Rectangle2()
        println("Rectangle2.vertexCount: ${rectangle2.vertexCount}")
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

        val polygon = Polygon()
        println("Polygon.vertexCount: ${polygon.vertexCount}")
    }

    Block("Derived class initialization order") {
        // The base class initialization is first order
        // So, avoid open members in the constructors, property initializers, and init blocks.
        open class Base(val name: String) { // 1

            init {
                println("Initializing a base class") // 2
            }

            open val size: Int = name.length.also {
                println("Initializing size in the base class: $it") // 3
            }
        }

        class Derived(
            name: String,
            val lastName: String,
        ) : Base(
            name.also {
                println("Argument for the base class: $it")
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

            val borderColor: String
                get() = "black"
        }

        // access using 'super@Outer`
        class FilledRectangle : Rectangle() {
            override fun draw() {
                val filler = Filler()
                filler.drawAndFill()
            }

            inner class Filler {
                fun fill() {
                    println("Filling")
                }

                fun drawAndFill() {
                    super@FilledRectangle.draw()
                    fill()
                    println("Drawn a with color ${super@FilledRectangle.borderColor}")
                }
            }
        }

        FilledRectangle().draw()
    }

    Block("Overriding rules") {
        // if a class inherits multiple implementations of the same member,
        // it must override this member and provide its own implementation

        open class Rectangle {
            open fun draw() {
                println("Rectangle.draw")
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
