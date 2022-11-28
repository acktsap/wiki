package acktsap.types

import acktsap.Block

inline fun <reified A, reified B> Pair<*, *>.asPairOf(): Pair<A, B>? {
    if (first !is A || second !is B) return null
    return first as A to second as B
}

fun main() {
    Block("Is and !is operators") {
        val obj: Any = "test"
        if (obj is String) {
            println(obj.length)
        }

        val obj2: Any = 3
        if (obj2 !is String) { // same as !(obj is String)
            println("Not a String")
        }
    }

    Block("Smart casts in if") {
        fun demo1(x: Any) {
            if (x is String) {
                println(x.length) // x is automatically cast to String
            }
        }
        demo1("tt") // 2

        // if a negative check leads to a return
        fun demo2(x: Any) {
            if (x !is String) {
                return
            }

            println(x.length) // x is automatically cast to String
        }
        demo1("tt") // 2
    }

    Block("Smart casts in the right-hand side of && and ||") {
        fun demo1(x: Any) {
            // x is automatically cast to string on the right-hand side of `&&`
            if (x is String && x.length > 0) {
                println(x.length) // x is automatically cast to String
            }
        }
        demo1("tt") // 2

        fun demo2(x: Any) {
            // x is automatically cast to string on the right-hand side of `||`
            if (x !is String || x.length == 0) {
                return
            }

            println(x.length) // x is automatically cast to String
        }
        demo2("tt") // 2
    }

    Block("Smart casts for when") {
        val list = listOf(100, "test", arrayOf(1, 2, 3).toIntArray())
        list.forEach { x ->
            when (x) {
                is Int -> println(x + 1)
                is String -> println(x.length + 1)
                is IntArray -> println(x.sum())
            }
        }
    }

    Block("Unsafe cast operator") {
        // throws an exception if the cast isn't possible
        val y1: Any = "tt"
        val x1: String = y1 as String
        println(x1) // tt

        val y2: Any? = null
        val x2: String? = y2 as String?
        println(x2) // null
    }

    Block("Safe (nullable) cast operator") {
        // as? returns null on failure.
        val y: Any = 33
        val x: String? = y as? String
        println(x) // null
    }

    // Kotlin ensures type safety of operations involving generics
    // at compile time, while, at runtime, instances of generic types
    // don't hold information about their actual type arguments
    Block("Type erasure and generic type checks") {

        // checking List<Int> is impossible at runtime due to type erasure
        // however, you can check an instance against a star-projected type
        val something: Any = listOf(1, 2)
        if (something is List<*>) {
            something.forEach { println(it) } // 1 2
        }

        // or you can check non-generic part of the type
        val list: List<Any> = ArrayList<Int>().apply { addAll(listOf(11, 22)) }
        if (list is ArrayList) {
            println(list[0]) // 11
        }

        // Inline functions with reified have actual type arguments inlined at each call site.
        // But if arg is an instance of a generic type itself, its type arguments are still erased.
        val somePair: Pair<Any?, Any?> = "items" to listOf(1, 2, 3)
        val stringToSomething = somePair.asPairOf<String, Any>()
        val stringToInt = somePair.asPairOf<String, Int>()
        val stringToList = somePair.asPairOf<String, List<*>>()
        val stringToStringList = somePair.asPairOf<String, List<String>>() // Breaks type safety!
        println(stringToSomething)
        println(stringToInt)
        println(stringToList)
        println(stringToStringList)
    }

    Block("Unchecked casts") {
        fun demo(): Map<String, *> = mapOf<String, Any>(Pair("key", "test"))

        // type erasure makes checking actual type instance impossible at runtime
        // Warning: Unchecked cast: `Map<String, *>` to `Map<String, Int>`
        val data: Map<String, Int> = demo() as Map<String, Int>
        println(data) // {key=test}
        println(data["key"]) // test

        val value = data["key"]
        println(value)
    }
}
