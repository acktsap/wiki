package acktsap.syntax.packageandimports.external

fun printMessage() {
    println("external message")
}

fun pleaseUseAlias() {
    println("another external message")
}

data class Message(val message: String)
