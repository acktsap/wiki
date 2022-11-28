package acktsap.classandobjects

import acktsap.Block

fun main() {
    Block("Generics basic") {
        class Box<T>(t: T) {
            var value = t

            override fun toString(): String {
                return "Box(value=$value)"
            }
        }

        Box(3).also { println(it) }
        Box<Int>(5).also { println(it) } // type is inferred
    }

    // todo: https://kotlinlang.org/docs/generics.html#variance
}
