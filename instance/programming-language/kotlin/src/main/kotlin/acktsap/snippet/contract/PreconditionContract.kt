package acktsap.snippet.contract

import acktsap.Block

@Suppress("RedundantNullableReturnType")
fun main() {
    Block("require()") {
        val x = 3
        require(x == 3)
        println("x is $x")

        require(x == 10) { "x != 10" } // throw exception
    }

    Block("requireNotNull()") {
        val x: Int? = 3
        val notNullX = requireNotNull(x)
        println("x + 1 is ${notNullX + 1}") // no need to use ?.

        val y: Int? = null
        requireNotNull(y) { "y is null" } // throw exception
    }

    Block("check()") {
        val x = 3
        check(x == 3)
        println("x is $x")

        check(x == 10) { "x != 10" } // throw exception
    }

    Block("checkNotNull()") {
        val x: Int? = 3
        val notNullX = checkNotNull(x)
        println("x + 1 is ${notNullX + 1}") // no need to use ?.

        val y: Int? = null
        checkNotNull(y) { "y is null" } // throw exception
    }

    Block("error()") {
        println("success")
        error("This is error")
    }
}
