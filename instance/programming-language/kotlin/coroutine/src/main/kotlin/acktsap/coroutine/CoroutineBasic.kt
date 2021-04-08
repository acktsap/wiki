package acktsap.coroutine

import acktsap.Block
import acktsap.printWithThread
import kotlinx.coroutines.*

fun main() {
    Block("Hello world (implicit blocking)") {
        GlobalScope.launch { // launch a new coroutine without blocking current thread
            delay(300L) // non-blocking delay (default time unit is ms)
            printWithThread("${System.currentTimeMillis()}ms ${"World!"}")
        }
        printWithThread("${System.currentTimeMillis()}ms ${"Hello,"}")
        // main thread continues while coroutine is delayed
        Thread.sleep(1000L) // block main thread to keep JVM
    }

    Block("Hello world (explicit blocking)") {
        GlobalScope.launch {
            delay(300L)
            printWithThread("${System.currentTimeMillis()}ms ${"World!"}")
        }
        printWithThread("${System.currentTimeMillis()}ms ${"Hello,"}")
        // main thread continues here immediately
        runBlocking { // but this expression blocks the main thread
            delay(1000L) // ... while we delay to keep JVM alive
        }
    }

    Block("Hello world (explicit blocking on runBlocking Scope)") {
        runBlocking {
            GlobalScope.launch {
                delay(300L)
                printWithThread("${System.currentTimeMillis()}ms ${"World!"}")
            }
            printWithThread("${System.currentTimeMillis()}ms ${"Hello,"}")
            // main coroutine continues here immediately
            delay(1000L) // delaying to keep JVM alive
        }
    }

    Block("Waiting for job") {
        runBlocking {
            val job = GlobalScope.launch {
                delay(300L)
                printWithThread("${System.currentTimeMillis()}ms ${"[World!"}")
            }
            printWithThread("${System.currentTimeMillis()}ms ${"Hello,"}")
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
                printWithThread("${System.currentTimeMillis()}ms ${"World!"}")
            }
            printWithThread("${System.currentTimeMillis()}ms ${"Hello,"}")
            // no need to block since it does not complete until all the coroutine completes
        }
    }

    Block("Scope Builder") {
        // runBlocking : blocks the current thread for waiting
        // coroutineScope : just suspends, releasing the underlying thread for other usages
        printWithThread("${System.currentTimeMillis()}ms ${"Task start!"}")
        runBlocking {
            launch {
                delay(200L)
                printWithThread("${System.currentTimeMillis()}ms ${"Task from runBlocking"}")
            }

            coroutineScope { // Creates a coroutine scope
                launch {
                    delay(500L)
                    printWithThread("${System.currentTimeMillis()}ms ${"Task from nested launch"}")
                }

                delay(100L)
                printWithThread("${System.currentTimeMillis()}ms ${"Task from coroutine scope"}")
                // This line will be printed before the nested launch
            }

            // This line is not printed until the nested launch completes
            // since coroutineScope scope is not finished until it's child not complete
            printWithThread("${System.currentTimeMillis()}ms ${"Coroutine scope is over"}")
        }
    }

    Block("Suspending function") {
        // Suspending can use other suspending function (eg. delay)
        suspend fun doWorld() {
            delay(300L)
            printWithThread("${System.currentTimeMillis()}ms ${"World!"}")
        }

        runBlocking {
            launch { doWorld() }
            printWithThread("${System.currentTimeMillis()}ms ${"Hello,"}")
        }
    }

    Block("Coroutines ARE light-weight") {
        runBlocking {
            printWithThread("${System.currentTimeMillis()}ms ${"Start"}")
            repeat(1_000_000) { // launch a lot of coroutines
                launch {
                    delay(3000L)
                }
            }
        }
        printWithThread("${System.currentTimeMillis()}ms ${"Finished"}")
    }

    Block("Global coroutines are like daemon threads") {
        runBlocking {
            GlobalScope.launch {
                repeat(1000) { i ->
                    printWithThread("${System.currentTimeMillis()}ms ${"I'm sleeping $i ..."}")
                    delay(500L)
                }
            }
            delay(1300L) // just quit after delay
        }
    }
}
