package acktsap.coroutine

import acktsap.Block
import acktsap.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

@Suppress("BlockingMethodInNonBlockingContext", "DuplicatedCode")
fun main() {
    Block("Delay with Dispatchers.IO") {
        runBlocking {
            val dispatcher = Dispatchers.IO

            val time = measureTimeMillis {
                val a = async(dispatcher) {
                    delay(100)
                    log("return 12")
                    12
                }
                val b = async(dispatcher) {
                    delay(100)
                    log("return 23")
                    23
                }
                log("Requested")

                log("Result: ${a.await() + b.await()}")
            }
            log("Completed in $time ms") // 100ms
        }
    }

    Block("Sleep with Dispatchers.IO") {
        runBlocking {
            val dispatcher = Dispatchers.IO

            val time = measureTimeMillis {
                val a = async(dispatcher) {
                    TimeUnit.MILLISECONDS.sleep(100L)
                    log("return 12")
                    12
                }
                val b = async(dispatcher) {
                    TimeUnit.MILLISECONDS.sleep(100L)
                    log("return 23")
                    23
                }
                log("Requested")

                log("Result: ${a.await() + b.await()}")
            }
            log("Completed in $time ms") // 100ms
        }
    }

    Block("Delay with Direct dispatcher") {
        runBlocking {
            val executor = Executor { it.run() }
            val dispatcher = executor.asCoroutineDispatcher()

            log("Start")
            val time = measureTimeMillis {
                // use CoroutineScope
                val a = async(dispatcher) {
                    delay(100)
                    log("return 12")
                    12
                }
                val b = async(dispatcher) {
                    delay(100)
                    log("return 23")
                    23
                }
                log("Requested")

                log("Result: ${a.await() + b.await()}")
            }
            log("Completed in $time ms") // 100ms
        }
    }

    Block("Sleep with Direct dispatcher") {
        runBlocking {
            val executor = Executor { it.run() }
            val dispatcher = executor.asCoroutineDispatcher()

            log("Start")
            val time = measureTimeMillis {
                // use CoroutineScope
                val a = async(dispatcher) {
                    TimeUnit.MILLISECONDS.sleep(100L)
                    log("return 12")
                    12
                }
                val b = async(dispatcher) {
                    TimeUnit.MILLISECONDS.sleep(100L)
                    log("return 23")
                    23
                }
                log("Requested")

                log("Result: ${a.await() + b.await()}")
            }
            log("Completed in $time ms") // 200ms
        }
    }
}
