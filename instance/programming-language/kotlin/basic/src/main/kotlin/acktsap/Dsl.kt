package acktsap

@Suppress("FunctionName")
inline fun Block(description: String, block: () -> Unit) {
    println("== $description ==")
    block()
    println()
}
