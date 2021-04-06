package acktsap

@Suppress("FunctionName")
inline fun Block(description: String, block: () -> Unit) {
    println("== $description ==")
    block()
    println()
}

inline fun printlnWithData(message: Any?) {
    System.out.println("[${Thread.currentThread().name} in ${System.currentTimeMillis()}ms] $message")
}
