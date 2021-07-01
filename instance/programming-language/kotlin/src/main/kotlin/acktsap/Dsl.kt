package acktsap

import java.time.LocalTime

@Suppress("FunctionName")
inline fun Block(description: String, block: () -> Unit) {
    println("== $description ==")
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace(System.out)
    }
    println()
}

fun log(message: String) = println("${LocalTime.now()} [${Thread.currentThread().name}] $message")
