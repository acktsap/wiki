package acktsap.language.packageandimports

/* Imports */
import acktsap.Block
import acktsap.language.packageandimports.external.Message
import acktsap.language.packageandimports.external.printMessage
import java.util.*
import acktsap.language.packageandimports.external.pleaseUseAlias as alias

fun main() {
    Block("Default Imports") {
        println(
            """
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
        """.trimIndent()
        )
    }

    Block("Imports") {
        // external message
        printMessage()
        println(Message("hey")) // hey

        // external message with alias
        alias()
    }

    Block("Imports with *") {
        // java.util.Objects
        println(Objects.isNull(null)) // true
        // java.util.Vector
        println(Vector<Int>(1).apply {
            add(1)
            add(2)
        }) // [1, 2]
    }
}

