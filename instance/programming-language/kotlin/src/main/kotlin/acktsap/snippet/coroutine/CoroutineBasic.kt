package acktsap.snippet.coroutine

import acktsap.Block
import acktsap.log
import kotlinx.coroutines.*

fun main() {
    Block("Hello world (implicit blocking)") {
        GlobalScope.launch { // launch a new coroutine without blocking current thread
            delay(300L) // non-blocking delay (default time unit is ms)
            log("World!")
        }
        log("Hello,")
        // main thread continues while coroutine is delayed
        Thread.sleep(1000L) // block main thread to keep JVM
    }

    Block("Hello world (explicit blocking)") {
        GlobalScope.launch {
            delay(300L)
            log("World!")
        }
        log("Hello,")
        // main thread continues here immediately
        runBlocking { // but this expression blocks the main thread
            delay(1000L) // ... while we delay to keep JVM alive
        }
    }

    Block("Hello world (explicit blocking on runBlocking Scope)") {
        runBlocking {
            GlobalScope.launch {
                delay(300L)
                log("World!")
            }
            log("Hello,")
            // main coroutine continues here immediately
            delay(1000L) // delaying to keep JVM alive
        }
    }

    Block("Waiting for job") {
        runBlocking {
            val job = GlobalScope.launch {
                delay(300L)
                log("World!")
            }
            log("Hello,")
            job.join() // wait until child coroutine completes
        }
    }

    Block("New CoroutineScope") {
        // Every coroutine builder, including runBlocking,
        // adds an instance of CoroutineScope to the scope of its code block
        runBlocking {
            // launch a new coroutine in the scope of runBlocking
            launch {
                delay(300L)
                log("World!")
            }
            log("Hello,")
            // no need to block since it does not complete until all the coroutine completes
        }
    }

    Block("Scope Builder") {
        // runBlocking : blocks the current thread for waiting
        // coroutineScope : just suspends, releasing the underlying thread for other usages
        log("Task start!")
        runBlocking {
            launch {
                delay(200L)
                log("Task from runBlocking")
            }

            coroutineScope { // Creates a coroutine scope
                launch {
                    delay(500L)
                    log("Task from nested launch")
                }

                delay(100L)
                // This line will be printed before the nested launch
                log("Task from coroutine scope")
            }

            // This line is not printed until the nested launch completes
            // since coroutineScope scope is not finished until it's child not complete
            log("Coroutine scope is over")
        }
    }

    Block("Suspending function") {
        // Suspending can use other suspending function (eg. delay)
        suspend fun doWorld() {
            delay(300L)
            log("World!")
        }

        runBlocking {
            launch { doWorld() }
            log("Hello,")
        }
    }

    Block("Coroutines are light-weight") {
        runBlocking {
            log("Start")
            repeat(1_000_000) { // launch a lot of coroutines
                launch {
                    delay(3000L)
                }
            }
        }
        log("Finished") // does not take long
    }

    Block("Global coroutines are like daemon threads") {
        runBlocking {
            GlobalScope.launch {
                repeat(1000) { i ->
                    log("I'm sleeping $i ...")
                    delay(500L)
                }
            }
            delay(1300L) // just quit after delay
        }
    }
}
