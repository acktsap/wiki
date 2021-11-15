package acktsap.snippet.collection

import acktsap.Block
import kotlin.streams.asSequence

fun main() {
    Block("Java Stream to Sequence") {
        val origin = listOf(1, 2, 3)
        val converted = origin.stream()
            .asSequence()
            .map { it * 10 }
            .toList()

        println("origin: $origin")
        println("converted: $converted")
    }

    // TODO: https://kotlinlang.org/docs/sequences.html
}
