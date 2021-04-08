package acktsap

@Suppress("FunctionName")
inline fun Block(description: String, block: () -> Unit) {
    println("== $description ==")
    block()
    println()
}

fun printWithThread(message: Any?) = println("[${Thread.currentThread().name}] $message")
