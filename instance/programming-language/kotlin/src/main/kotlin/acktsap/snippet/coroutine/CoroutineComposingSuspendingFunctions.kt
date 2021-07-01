package acktsap.snippet.coroutine

import acktsap.Block
import acktsap.log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    suspend fun doSomethingUsefulOne(): Int {
        delay(300L)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(300L)
        return 29
    }

    Block("Sequential by default") {
        runBlocking {
            val time = measureTimeMillis {
                val one = doSomethingUsefulOne()
                val two = doSomethingUsefulTwo()
                log("The answer is ${one + two}")
            }
            log("Completed in $time ms") // 600ms
        }
    }

    Block("Concurrent using async") {
        // launch : returns Job (does not carry result)
        // async : returns Deferred (a light-weight non-blocking future, carry result)
        runBlocking {
            val time = measureTimeMillis {
                val one = async { doSomethingUsefulOne() }
                val two = async { doSomethingUsefulTwo() }
                log("The answer is ${one.await() + two.await()}") // by await
            }
            log("Completed in $time ms") // 300ms
        }
    }

    Block("Lazily started async") {
        // CoroutineStart.LAZY : starts the coroutine when 'await' or 'start' is invoked
        runBlocking {
            val time = measureTimeMillis {
                val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
                val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }

                one.start() // start the first one. but two is not started
                delay(600)
                log("The answer is ${one.await() + two.await()}") // two starts here
            }
            log("Completed in $time ms") // 900ms
        }
    }

    Block("Async style functions (NOT RECOMMANDED)") {
        // Async style functions. Not the suspending functions.
        // Not recommanded. What if it throws error? The program may continue other operation..
        fun somethingUsefulOneAsync(): Deferred<Int> = GlobalScope.async {
            doSomethingUsefulOne()
        }

        fun somethingUsefulTwoAsync(): Deferred<Int> = GlobalScope.async {
            doSomethingUsefulTwo()
        }

        val time = measureTimeMillis {
            // we can initiate async actions outside of a coroutine
            val one = somethingUsefulOneAsync()
            val two = somethingUsefulTwoAsync()

            // but waiting for a result must involve either suspending or blocking.
            // here we use `runBlocking { ... }` to block the main thread while waiting for the result
            runBlocking {
                log("The answer is ${one.await() + two.await()}")
            }
        }
        log("Completed in $time ms") // 300ms
    }

    Block("Structured concurrency with async") {
        // Structured concurrency
        suspend fun concurrentSum(): Int = coroutineScope {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            one.await() + two.await()
        }

        runBlocking {
            val time = measureTimeMillis {
                log("The answer is ${concurrentSum()}")
            }
            log("Completed in $time ms") // 300ms
        }

        // cancellation is always propagated through coroutines hierarchy
        suspend fun failedConcurrentSum(): Int = coroutineScope {
            val one = async<Int> {
                try {
                    delay(Long.MAX_VALUE) // Emulates very long computation
                    log("Never called")
                    42
                } finally {
                    log("First child was cancelled")
                }
            }
            val two = async<Int> {
                log("Second child throws an exception")
                throw ArithmeticException()
            }
            one.await() + two.await()
        }

        runBlocking {
            try {
                failedConcurrentSum()
            } catch (e: ArithmeticException) {
                log("Computation failed with ArithmeticException")
            }
        }
    }
}
