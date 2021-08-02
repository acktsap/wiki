package acktsap.snippet.coroutine

import acktsap.Block
import acktsap.log
import kotlinx.coroutines.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

@Suppress("BlockingMethodInNonBlockingContext", "DuplicatedCode")
fun main() {
    Block("Sleep with Dispatchers.IO") {
        runBlocking {
            val dispatcher = Dispatchers.IO

            log("Start with Dispatchers.IO using sleep")
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

            log("Start")
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

    Block("IO Blocking Comparison") {
        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://www.naver.com:91"))
            .timeout(Duration.ofSeconds(5))
            .build()

        val nProcessor = Runtime.getRuntime().availableProcessors()
        val tryCount = 2 * nProcessor

        fun sendRequest() {
            try {
                val client = HttpClient.newHttpClient()
                client.send(httpRequest, HttpResponse.BodyHandlers.ofString())
            } catch (ignored: Exception) {
            }
        }

        runBlocking {
            val executorService = Executors.newFixedThreadPool(2 * nProcessor)
            val threadPoolMs = measureTimeMillis {
                val futures = (1..tryCount).map {
                    // started가 한번에 찍힌 이후 finished가 한번에 찍힘
                    executorService.submit {
                        log("started (executorService)")
                        sendRequest()
                        log("finished (executorService)")
                    }
                }
                futures.forEach { it.get() }
            }
            executorService.shutdownNow()

            val coroutineDefaultMs = measureTimeMillis {
                /*
                    Dispatchers.Default

                    - default CoroutineDispatcher used by all standard builders like (launch, async)
                    - default # of thread == # of core
                 */
                withContext(Dispatchers.Default) {
                    repeat(tryCount) {
                        // started가 core개수만큼씩만 한번에 찍힘
                        // Dispatchers.Default는 core 수만큼 thread가 있는데
                        // 다 blocking이 되어서 다른 task로 context switching이 미발생
                        launch {
                            log("started (Dispatchers.Default)")
                            sendRequest()
                            log("finished (Dispatchers.Default)")
                        }
                    }
                }
            }

            val coroutineIoMs = measureTimeMillis {
                /*
                    Dispatchers.IO

                    - designed for offloading blocking IO tasks to a shared pool of threads
                    - allocates additional threads to keep up with concurrent blocking IO operations
                    - default # of thread == 64
                    - set pool size : kotlinx.coroutines.scheduler.max.pool.size
                 */
                withContext(Dispatchers.IO) {
                    repeat(tryCount) {
                        // started가 한번에 찍힌 이후 finished가 한번에 찍힘
                        // Dispatchers.IO는 I/O intensive job을 위해 pool size를 넉넉하게 잡아놓음
                        launch {
                            log("started (Dispatchers.IO)")
                            sendRequest()
                            log("finished (Dispatchers.IO)")
                        }
                    }
                }
            }

            // java threadPool 하고 Dispatcher.IO 하고 비슷
            log("threadPool: ${threadPoolMs}ms")
            log("coroutine(Dispatchers.Default): ${coroutineDefaultMs}ms")
            log("coroutine(Dispatchers.IO): ${coroutineIoMs}ms")
        }
    }
}
