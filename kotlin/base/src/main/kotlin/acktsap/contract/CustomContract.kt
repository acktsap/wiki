package acktsap.snippet.contract

import acktsap.Block
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@ExperimentalContracts
fun returnsContract(condition: Boolean) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw IllegalArgumentException()
}

@ExperimentalContracts
fun returnsTrueContract(condition: Boolean): Boolean {
    contract {
        returns(true) implies (condition)
    }
    return condition
}

@ExperimentalContracts
fun returnsNotNullContract(condition: Boolean): Boolean? {
    contract {
        returnsNotNull() implies (condition)
    }
    return if (condition) true else null
}

@ExperimentalContracts
inline fun callsInPlaceAtMostOnceContract(block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
}

@ExperimentalContracts
inline fun callsInPlaceAtLeastOnceContract(block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
    }
    block()
    block()
}

@ExperimentalContracts
inline fun <T> callsInPlaceExactlyOnceContract(block: () -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block()
}

@ExperimentalContracts
inline fun callsInPlaceUnknownContract(block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.UNKNOWN)
    }
    block()
    block()
    block()
}

@ExperimentalContracts
fun main() {
    Block("returns()") {
        fun run(x: Any?) {
            returnsContract(x != null)
            println("x is $x") // no need to ?.toString()
        }

        run(10)
        run("Hello")
        run(null) // throws exception
    }

    Block("returns(value: Any?)") {
        fun run(x: Number) {
            if (returnsTrueContract(x is Int)) {
                val value = x + 1 // x is considered as 'Int'
                println("x + 1 is $value")
            } else {
                println("x is not Int")
            }
        }

        run(10)
        run(-.9)
    }

    Block("returnsNotNull()") {
        fun run(x: Int?) {
            if (returnsNotNullContract(x == 3) != null) {
                println("x is $x")
            } else {
                println("x is not 3")
            }
        }

        run(3)
        run(2)
    }

    Block("callsInPlace(block, InvocationKind.AT_MOST_ONCE)") {
        val x: Int

        callsInPlaceAtMostOnceContract {
            x = 10
            println("x is $x")
        }

        // println("x is $x") // x must be initialized
    }

    Block("callsInPlace(block, InvocationKind.AT_LEAST_ONCE)") {
        var x: Int

        callsInPlaceAtLeastOnceContract {
            x = 10
        }

        println("x is $x")
    }

    Block("callsInPlace(block, InvocationKind.EXACTLY_ONCE)") {
        val x: Int

        callsInPlaceExactlyOnceContract {
            x = 10
        }

        println("x is $x")
    }

    Block("callsInPlace(block, InvocationKind.UNKNOWN)") {
        var x: Int

        callsInPlaceUnknownContract {
            x = 10
            println("x is $x")
        }

        // println("x is $x") // x must be initialized
    }
}
