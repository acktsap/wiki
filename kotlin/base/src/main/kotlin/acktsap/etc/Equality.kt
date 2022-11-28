package acktsap.etc

import acktsap.Block

@Suppress("RedundantNullableReturnType", "UnnecessaryVariable")
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

        val origin = A(1)
        val referrer = origin
        val another = A(1)

        println("Referential equality with referrer: ${origin === referrer}")
        println("Structural equality with referrer: ${origin == referrer}")
        println("Referential equality with another: ${origin === another}")
        println("Structural equality with another: ${origin == another}")
    }
}
