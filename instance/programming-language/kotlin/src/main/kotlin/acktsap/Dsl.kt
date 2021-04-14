package acktsap

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

fun printWithThread(message: String) = println("[${Thread.currentThread().name}]$message")
