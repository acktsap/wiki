package acktsap.coroutine

import acktsap.Block
import acktsap.printWithThread
import kotlinx.coroutines.*
import java.util.*

fun main() {
    Block("Dispatchers and threads") {
        runBlocking {
            launch { // use context of the parent
                println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) { // use a coroutine in the caller thread,
                println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) { // use DefaultDispatcher
                println("Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            GlobalScope.launch { // use DefaultDispatcher
                println("GlobalScope.launch    : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) { // use 'MyOwnThread'
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }

    Block("Unconfined vs confined dispatcher") {
        // The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread,
        // but only until the first suspension point.
        // After suspension it resumes the coroutine in the thread that is fully determined by the suspending function that was invoked
        // It should be used in corner cases
        runBlocking {
            launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
                println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
                delay(500)
                println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
            }
            launch { // context of the parent, main runBlocking coroutine
                println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
                println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
            }
        }
    }

    Block("Jumping between threads") {
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    printWithThread("Started in ctx1")
                    withContext(ctx2) {
                        printWithThread("Working in ctx2")
                    }
                    printWithThread("Back to ctx1")
                }
            }
        }
    }

    Block("Job in the context") {
        runBlocking {
            // retrieved job from coroutineContext
            println("My job is ${coroutineContext[Job]}")
        }
    }

    Block("Children of a coroutine") {
        runBlocking {
            val request = launch {
                GlobalScope.launch {
                    printWithThread("job1: I run in GlobalScope and execute independently!")

                    delay(500)
                    printWithThread("job1: I am not affected by cancellation of the request")
                }

                launch {
                    printWithThread("job2: I am a child of the request coroutine")

                    delay(500)
                    printWithThread("job2: I will not execute this line if my parent request is cancelled")
                }
            }

            delay(100)
            request.cancel() // cancel processing of the request

            delay(500) // delay a second to see what happens
            printWithThread("main: Who has survived request cancellation?")
        }
    }

    Block("Parent responsibilities") {
        runBlocking {
            val request = launch {
                repeat(3) { i -> // launch a few children jobs
                    launch {
                        delay((i + 1) * 100L)
                        printWithThread("Coroutine $i is done")
                    }
                }
                printWithThread("request: I'm done and I don't explicitly join my children that are still active")
            }

            request.join()
            printWithThread("Now processing of the request is complete")
        }
    }

    Block("Naming coroutines for debugging") {
        runBlocking {
            printWithThread("Started main coroutine")
            val v1 = async(CoroutineName("v1coroutine")) {
                delay(200)
                printWithThread("Computing v1")
                252
            }
            val v2 = async(CoroutineName("v2coroutine")) {
                delay(300)
                printWithThread("Computing v2")
                6
            }
            printWithThread("The answer for v1 / v2 = ${v1.await() / v2.await()}")
        }
    }

    Block("Combining context elements") {
        runBlocking {
            launch(Dispatchers.Default + CoroutineName("test")) {
                printWithThread("I'm working in $coroutineContext")
            }
        }
    }

    Block("Coroutine Scope") {
        runBlocking {
            class Activity {
                private val mainScope = CoroutineScope(newFixedThreadPoolContext(8, "tester"))

                fun destroy() {
                    mainScope.cancel()
                }

                fun doSomething() {
                    repeat(10) { i ->
                        mainScope.launch {
                            delay((i + 1) * 100L)
                            printWithThread("Coroutine $i is done")
                        }
                    }
                }
            }

            val activity = Activity()
            activity.doSomething()
            printWithThread("Launched coroutines")
            delay(250L)

            printWithThread("Destroying activity!")
            activity.destroy()
            delay(500) // visually confirm that they don't work
        }
    }

    Block("Thread-local data") {
        runBlocking {
            val threadLocal = ThreadLocal<String?>()

            threadLocal.set("main")
            println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
                println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
                yield()
                println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            }
            job.join()
            println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        }
    }
}
