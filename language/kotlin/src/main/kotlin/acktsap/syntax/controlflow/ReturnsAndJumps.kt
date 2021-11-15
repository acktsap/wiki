package acktsap.syntax.controlflow

import acktsap.Block

fun main() {
    Block("Break") {
        for (i in 1..5) {
            if (i == 3) {
                break; // break loop on i == 3
            }
            // print 1, 2
            println("i: $i")
        }
    }

    Block("Break on Label") {
        label@ for (i in 1..3) {
            for (j in 1..5) {
                if (j == 3) {
                    break@label // break '@label' loop
                }
                // print (i: 1, j: 1), (i: 1, j: 2)
                println("i: $i, j: $j")
            }
        }
    }

    Block("Continue") {
        for (i in 1..5) {
            if (i == 3) {
                continue; // skip on i == 3
            }
            // print 1, 2, 4, 5
            println("i: $i")
        }
    }

    Block("Continue on Label") {
        label@ for (i in 1..3) {
            for (j in 1..100) {
                if (j == 3) {
                    continue@label // skip '@label' on j == 3
                }
                // print (i: 1, j: 1), (i: 1, j: 2)
                // print (i: 2, j: 1), (i: 2, j: 2)
                // print (i: 3, j: 1), (i: 3, j: 2)
                println("i: $i, j: $j")
            }
        }
    }

    Block("Return") {
        fun foo() {
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) {
                    return // return nestest enclosing function - foo()
                }
                // print 1, 2
                println(it)
            }
            println("this point is unreachable")
        }
        foo()
    }

    Block("Return at Labels") {
        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) {
                return@lit // return the lambda - @lit
            }
            // print 1, 2, 4, 5
            println(it)
        }

        // using implicit label (name of the function to which the lambda is passed)
        listOf('A', 'B', 'C', 'D', 'E').forEach {
            if (it == 'c') {
                return@forEach // return the lambda - @forEach
            }
            // print A, B, D, E
            println(it)
        }
    }

    Block("Return at anonymous function") {
        listOf(1, 2, 3, 4, 5).forEach(
            fun(value: Int) {
                if (value == 3) {
                    return // return the anonymous function
                }
                // print 1, 2
                println(value)
            }
        )
    }

    Block("Use return to break loop") {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) {
                    return@loop
                }
                // print 1, 2
                println(it)
            }
        }
    }

    Block("Return function lambda") {
        fun foo() {
            (1..5).forEach {
                if (it == 3) {
                    return@foo // return @foo, not lambda itself
                }
                // print 1, 2
                println(it)
            }
        }
        foo()
    }
}
