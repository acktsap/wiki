package acktsap.language.controlflow

import acktsap.Block
import java.lang.Integer.parseInt
import java.util.concurrent.ThreadLocalRandom

fun main() {
    Block("If Expression") {
        val a = 1
        val b = 2

        // if-else as statement
        if (a > b) {
            println("a > b")
        } else {
            println("a <= b")
        }

        // if-else as expression
        val choose = if (a > b) {
            println("Choose a")
            "a"
        } else {
            println("Choose b")
            "b"
        }
        println(choose) // b

        // no ternary operator (condition ? then : else), because if works fine in this role
        val max = if (a > b) a else b
        println(max) // 2
    }

    Block("When Expression") {
        val x = ThreadLocalRandom.current().nextInt(100)

        // when as statement
        when (x) {
            1 -> println("x == 1")
            2 -> println("x == 2")
            // use comma
            3, 4 -> println("x == 3 or x == 4")
            else -> println("x is some else ($x)")
        }

        // when as expression
        val ret = when (x) {
            in 1..99 -> "x is in 1..99"
            else -> "x is else ($x)"
        }
        println(ret)

        // use comma
        when (x) {
            3, 4 -> println("x == 3 or x == 4")
            else -> println("x is neither 3 nor 4")
        }

        // use arbitary expression
        val s = "99"
        when (x) {
            parseInt(s) -> println("x is $s")
            else -> println("x is not $s")
        }

        // use 'in'
        val validNumbers = 10..99
        when (x) {
            in 1..2 -> println("x is in 1..2")
            in validNumbers -> println("x is in valid number ($validNumbers)")
            else -> println("what? $x?")
        }

        // not 'in'
        when (x) {
            !in 33..44 -> println("not in 33..44")
            else -> println("in 33..44")
        }

        // use 'is'
        val any = listOf(3, "'3'").random()
        when (any) {
            is String -> println("$any is String")
            else -> println("$any is not String")
        }

        // replacement for if-else
        when {
            x / 2 == 0 -> println("x is even")
            else -> println("x is odd")
        }
    }

    Block("When with variable (since 1.3)") {
        fun execute() = listOf(
            IllegalStateException(),
            UnsupportedOperationException(),
            RuntimeException()
        ).random()

        val ret = when (val response = execute()) {
            is IllegalStateException -> "IllegalStateException"
            is UnsupportedOperationException -> "UnsupportedOperationException"
            else -> "Unexpected ($response)"
        }
        println("response: $ret")
    }

    Block("For Loops") {
        val list = listOf(1, 2, 3)

        // for each
        for (item in list) {
            println(item)
        }

        // use an index
        for (i in list.indices) {
            println("list[$i] : ${list[i]}")
        }

        // use withIndex
        for ((index, value) in list.withIndex()) {
            println("list[$index] : $value")
        }
    }

    Block("While Loops") {
        var x = 3
        while (x > 0) {
            println("x is $x")
            --x
        }
    }
}
