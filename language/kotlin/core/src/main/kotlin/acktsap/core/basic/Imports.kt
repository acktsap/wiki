package acktsap.core.basic

/* Imports */
import acktsap.core.Spec
import acktsap.core.basic.external.Message
import acktsap.core.basic.external.printMessage
import java.util.* // use '*'
import acktsap.core.basic.external.pleaseUseAlias as another // use alias

fun main() {
    Spec("Default Imports") {
        println("""
            A number of packages are imported into every Kotlin file by default:
                kotlin.*
                kotlin.annotation.*
                kotlin.collections.*
                kotlin.comparisons.* (since 1.1)
                kotlin.io.*
                kotlin.ranges.*
                kotlin.sequences.*
                kotlin.text.*

            Additional packages are imported depending on the target platform:
                JVM:
                    java.lang.*
                    kotlin.jvm.*
                JS:
                    kotlin.js.*
        """.trimIndent())
    }

    Spec("Imports") {
        printMessage() // external message
        val message = Message("hey")
        println(message) // hey
        another() // another message

        // java.util.Objects
        println(Objects.isNull(null)) // true
        // java.util.Vector
        println(Vector<Int>(1).apply {
            add(1)
            add(2)
        }) // [1, 2]
    }

    Spec("Full Import") {
        val sb = java.lang.StringBuilder().apply {
            append("full")
            append(" ")
            append("import")
        }
        println(sb.toString()) // full import
    }

}

