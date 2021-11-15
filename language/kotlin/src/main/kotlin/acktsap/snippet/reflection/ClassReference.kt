package acktsap.snippet.reflection

import acktsap.Block
import kotlin.reflect.KParameter
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

private interface SomeInterface

@Suppress("JoinDeclarationAndAssignment")
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

    Block("Constructors") {
        class A(val money: Int = 0, name: String = "")

        val clazz = A::class
        val primaryConstructor = clazz.primaryConstructor
        println("primary constructor")
        println("  $primaryConstructor")

        val constructors = clazz.constructors
        println("constructors")
        constructors.forEach { println("  $it") }
    }

    Block("Make instance") {
        data class A(val money: Int = 0, val name: String = "noname") {
            lateinit var tt: String

            constructor(tt: String) : this(-999, "risky") {
                this.tt = tt
            }
        }

        val clazz = A::class
        val primaryConstructor = requireNotNull(clazz.primaryConstructor)

        val instance = clazz.createInstance()
        println("clazz.createInstance: $instance")

        val args = arrayOf(30, "god")
        val instanceWithCall = primaryConstructor.call(*args)
        println("primaryConstructor.call: $instanceWithCall")

        val argByParameter = mutableMapOf<KParameter, Any>().apply {
            for (parameter in primaryConstructor.parameters) {
                if (parameter.name == "money") {
                    put(parameter, 100)
                } else {
                    put(parameter, "dog")
                }
            }
        }
        val instanceWithCallBy = primaryConstructor.callBy(argByParameter)
        println("primaryConstructor.callBy: $instanceWithCallBy")

        println("instance.tt: ${instanceWithCallBy.tt}") // throw exception
    }

    Block("Constructors Params") {
        data class D(val a: Int)

        class A(val money: Int, d: D = D(0), name: String?)

        val clazz = A::class

        val constructor = requireNotNull(clazz.primaryConstructor)
        val parameters = constructor.parameters

        println("parameters")
        parameters.forEach {
            println("  $it")
            with(it) {
                println("    index: $index")
                println("    type: $type")
                println("    kind: $kind")
                println("    name: $name")
                println("    isOptional: $isOptional")
            }
        }
    }

    Block("Check isXX") {
        val clazz = SomeInterface::class
        println("interface - clazz.isAbstract: ${clazz.isAbstract}")
        println("interface - clazz.isOpen: ${clazz.isOpen}")
        println("interface - clazz.isSealed: ${clazz.isSealed}")
        println("interface - clazz.isFinal: ${clazz.isFinal}")
    }
}
