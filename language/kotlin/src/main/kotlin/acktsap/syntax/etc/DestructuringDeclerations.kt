package acktsap.syntax.etc

import acktsap.Block

data class Result(val code: Int, val status: String)

fun main() {
    Block("Destructuring declarations") {
        val result = Result(0, "success")

        val (code, status) = result
        println("code: $code, status: $status")
    }

    Block("Destructuring declarations (actually)") {
        val result = Result(0, "success")

        val code = result.component1()
        val status = result.component2()
        println("code: $code, status: $status")
    }

    Block("Destructuring declarations in collection") {
        val results = (1..3).map { Result(it, "status$it") }

        for ((code, status) in results) {
            println("code: $code, status: $status")
        }
    }

    Block("Destructuring declarations and maps") {
        val map = mapOf(
            "k1" to 1,
            "k2" to 2,
        )

        for ((key, value) in map) {
            println("key: $key, value: $value")
        }
    }

    Block("Underscore for unused variables") {
        val result = Result(-999, "wow")

        val (_, status) = result
        println("status: $status")
    }

    Block("Destructuring in lambdas") {
        val map = mapOf(
            "k1" to 1,
            "k2" to 2,
        )

        val values = map.map { (_, value) -> value }
        println("values: $values")
    }
}
