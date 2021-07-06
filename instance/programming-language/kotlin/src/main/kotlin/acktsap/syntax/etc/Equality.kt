package acktsap.syntax

import acktsap.Block

@Suppress("RedundantNullableReturnType")
fun main() {
    Block("Structural equality (equals)") {
        val a: Int? = null
        val b: Int? = 3

        val equality = (a == b)
        println("equality: $equality")

        val actual = a?.equals(b) ?: (b === null)
        println("actual: $actual")
    }

    Block("Referential equality (two references point to the same object)") {
        data class A(val p: Int)

        val a = A(1)
        val b = a

        val equalityOnSameObject = (a === b)
        println("equality (same object): $equalityOnSameObject")

        val equalityOnSameValue = (a === A(1))
        println("equality (same value): $equalityOnSameValue")
    }
}
