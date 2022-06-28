package acktsap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
open class SomeApplication

fun main(args: Array<String>) {
    runApplication<SomeApplication>(*args)
    exitProcess(0)
}
