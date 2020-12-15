package acktsap.core

@Suppress("FunctionName")
inline fun Spec(spec: String, example: () -> Unit) {
    println("== $spec ==")
    example()
    println()
}

@Suppress("FunctionName")
inline fun Pattern(name: String, pattern: () -> Unit) {
    println("== $name ==")
    pattern()
    println()
}

