package acktsap.reflection

import acktsap.Block

fun main() {
    Block("Class References") {
        val clazz = String::class
        println(clazz)
    }

    Block("bound Class References") {
        val value = "test"
        println("Test: ${value::class.qualifiedName}")
    }
}
