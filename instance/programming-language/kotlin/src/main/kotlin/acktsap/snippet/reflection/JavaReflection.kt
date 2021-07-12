package acktsap.snippet.reflection

import acktsap.Block
import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

fun main() {
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
