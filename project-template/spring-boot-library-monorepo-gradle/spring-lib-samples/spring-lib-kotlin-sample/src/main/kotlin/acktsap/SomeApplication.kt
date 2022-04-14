package acktsap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SomeApplication

fun main(args: Array<String>) {
    runApplication<SomeApplication>(*args)
}
