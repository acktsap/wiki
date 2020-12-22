package acktsap.core.basic

import acktsap.core.Spec


// In kotlin, everything is an object in the sense that
// we can call member functions and properties on any variable
fun main() {
    Spec("Number Types") {
        // Byte (-128 ~ 127), 1 bytes
        val byteOne: Byte = 1

        // Byte (-128 ~ 127), 2 bytes
        val shortOne: Short = 1
        println("Short: $shortOne")

        // Int (-2^31 ~ 2^31 - 1), 4 bytes
        val intOne = 1

        // Long (-2^64 ~ 2^64 - 1), 8 bytes
        val longOne: Long = 1
        val explicitLong = 1L
        val inferredLong = 3000000000 // inferred to Long since it excess Int.MAX_VALUE

        // Float, (decimal digits : 6-7), 4 bytes
        val floatE = 2.7182818284f // Float, actual value is 2.7182817

        // Double, (decimal digits : 15-16), 8 bytes
        val doubleE = 2.7182818284
    }

    // TODO: https://kotlinlang.org/docs/reference/basic-types.html#explicit-conversions
}