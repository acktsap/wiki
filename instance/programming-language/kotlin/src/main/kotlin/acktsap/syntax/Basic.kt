package acktsap.syntax

import acktsap.Block

/* Top-level */
val PI = 3.14
var x = 0

fun incrementX() {
    x += 1
}

@Suppress("JoinDeclarationAndAssignment")
fun main() {
    Block("Comment") {
        // This is an end-of-line comment

        /* This is a block comment
           on multiple lines. */
    }

    Block("Read-only variable") {
        val a: Int = 1 // immediate assignment
        val b = 2 // `Int` type is inferred
        val c: Int // Type required when no initializer is provided

        c = 3 // deferred assignment
        println("Local constant a: $a")
        println("Local constant b: $b")
        println("Local constant c: $c")
        println("Global constant: $PI")
    }

    Block("Variable") {
        var a = 5 // `Int` type is inferred
        a += 1

        println("Local variable: $a")
        println("Global variable: $x")
    }

    Block("Functions") {
        // function declaration
        fun sum1(a: Int, b: Int): Int {
            return a + b
        }
        println("sum1: ${sum1(1, 2)}")

        // use expression in function body
        fun sum2(a: Int, b: Int) = a + b
        println("sum2: ${sum2(1, 2)}")

        // without return type
        fun printSum(a: Int, b: Int) {
            println("sum of $a and $b is ${a + b}")
        }
        printSum(1, 2)
    }
}
