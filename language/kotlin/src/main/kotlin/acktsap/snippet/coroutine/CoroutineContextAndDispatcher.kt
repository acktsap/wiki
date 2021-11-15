package acktsap.snippet.coroutine

import acktsap.Block
import acktsap.log
import kotlinx.coroutines.*

fun main() {
    Block("Dispatchers and threads") {
        runBlocking {
            launch { // use context of the parent
                log("runBlocking launch")
            }
            launch(Dispatchers.Unconfined) { // use a coroutine in the caller thread,
                log("Dispatchers.Unconfined")
            }
            launch(Dispatchers.Default) { // use DefaultDispatcher
                log("Dispatchers.Default")
            }
            GlobalScope.launch { // use DefaultDispatcher
                log("GlobalScope.launch")
            }
            launch(newSingleThreadContext("MyOwnThread")) { // use 'MyOwnThread'
                log("newSingleThreadContext(\"MyOwnThread\")")
            }
        }
    }

    Block("runBlocking in function scope") {
        log("start")
        fun test() = runBlocking {
            async {
                delay(100)
                log("runBlocking in function scope")
            }
        }

        @Suppress("DeferredResultUnused")
        test()

        log("finished")
    }

    Block("Unconfined vs confined dispatcher") {
        // The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread,
        // but only until the first suspension point.
        // After suspension it resumes the coroutine in the thread that is fully determined by the suspending function that was invoked
        // It should be used in corner cases
        runBlocking {
            launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
                log("Dispatchers.Unconfined")
                delay(500)
                log("Dispatchers.Unconfined after delay")
            }
            launch { // context of the parent, main runBlocking coroutine
                log("runBlocking launch")
                delay(1000)
                log("runBlocking launch after delay")
            }
        }
    }

    Block("Jumping between threads") {
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    log("Started in ctx1")
                    withContext(ctx2) {
                        log("Working in ctx2")
                    }
                    log("Back to ctx1")
                }
            }
        }
    }

    Block("Job in the context") {
        runBlocking {
            // retrieved job from coroutineContext
            log("My job is ${coroutineContext[Job]}")
        }
    }

    Block("Children of a coroutine") {
        runBlocking {
            val request = launch {
                GlobalScope.launch {
                    log("job1: I run in GlobalScope and execute independently!")

                    delay(300)
                    log("job1: I am not affected by cancellation of the request")
                }

                launch {
                    log("job2: I am a child of the request coroutine")

                    delay(500)
                    log("job2: I will not execute this line if my parent request is cancelled")
                }
            }

            delay(100)
            request.cancel() // cancel processing of the request

            delay(300)
            log("main: Who has survived request cancellation?")
        }
    }

    Block("Parent responsibilities") {
        runBlocking {
            val request = launch {
                repeat(3) { i -> // launch a few children jobs
                    launch {
                        delay((i + 1) * 100L)
                        log("Coroutine $i is done")
                    }
                }
                log("request: I'm done and I don't explicitly join my children that are still active")
            }

            request.join()
            log("Now processing of the request is complete")
        }
    }

    Block("Naming coroutines for debugging") {
        // need jvm option : -Dkotlinx.coroutines.debug
        runBlocking {
            log("Started main coroutine")
            val v1 = async(CoroutineName("v1coroutine")) {
                delay(200)
                log("Computing v1")
                252
            }
            val v2 = async(CoroutineName("v2coroutine")) {
                delay(300)
                log("Computing v2")
                6
            }
            log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
        }
    }

    Block("Combining context elements") {
        runBlocking {
            launch(Dispatchers.Default + CoroutineName("test")) {
                log("I'm working in $coroutineContext")
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
                            log("Coroutine $i is done")
                        }
                    }
                }
            }

            val activity = Activity()
            activity.doSomething()
            log("Launched coroutines")
            delay(250L)

            log("Destroying activity!")
            activity.destroy()
            delay(500) // visually confirm that they don't work
        }
    }

    Block("Thread-local data") {
        runBlocking {
            val threadLocal = ThreadLocal<String>()

            threadLocal.set("main")
            log("Pre-main, thread local value: '${threadLocal.get()}'")
            val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
                log("Launch start, thread local value: '${threadLocal.get()}'")
                yield()
                log("After yield, thread local value: '${threadLocal.get()}'")
            }
            job.join()
            log("Post-main, thread local value: '${threadLocal.get()}'")
        }
    }
}
