package acktsap.snippet.reflection

import acktsap.Block

fun main() {
    Block("Class References") {
        val clazz = String::class
        println("class: $clazz")
    }

    Block("Bound Class References") {
        val value = "test"
        println("class: ${value::class.qualifiedName}")
    }
}
