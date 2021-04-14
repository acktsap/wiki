package acktsap.language

/* Top-level */
val PI = 3.14
var x = 0

fun incrementX() {
    x += 1
}

fun main() {
    /* constant */

    val a: Int = 1 // immediate assignment
    val b = 2 // `Int` type is inferred
    val c: Int // Type required when no initializer is provided
    c = 3 // deferred assignment

    /* variable */

    var x = 5 // `Int` type is inferred
    x += 1

    println("Local constant: $a")
    println("Local variable: $x")
    println("Global constant: $PI")
    println("Global variable: $x")
}
