package acktsap.core

@Suppress("FunctionName")
inline fun Spec(spec: String, example: () -> Unit) {
    println("== [Spec: $spec] ==")
    example()
    println()
}

@Suppress("FunctionName")
inline fun Pattern(name: String, pattern: () -> Unit) {
    println("== [Pattern: $name] ==")
    pattern()
    println()
}

