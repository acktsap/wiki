package acktsap.snippet.reflection

import acktsap.Block

fun main() {
    Block("Function Reference") {
        fun isOdd(x: Int) = x % 2 != 0
        val numbers = listOf(1, 2, 3)

        println(numbers.filter(::isOdd))
    }

    Block("Function Reference With Receiver") {
        val isEmptyStringList: List<String>.() -> Boolean = List<String>::isEmpty

        println(isEmptyStringList(listOf()))
    }

    Block("Function Composition using Function Reference") {
        fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
            return { x -> f(g(x)) }
        }

        fun isOdd(x: Int) = x % 2 != 0
        fun length(s: String) = s.length

        val oddLength = compose(::isOdd, ::length)
        val strings = listOf("a", "ab", "abc")

        println(strings.filter(oddLength))
    }

    Block("Property References") {
        // TODO : https://kotlinlang.org/docs/reflection.html#property-references
    }
}
