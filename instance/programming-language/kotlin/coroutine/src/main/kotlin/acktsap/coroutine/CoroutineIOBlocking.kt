package acktsap.coroutine

import acktsap.Block
import acktsap.printWithThread
import kotlinx.coroutines.*
import java.net.URL
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

fun main() {
    Block("IO Blocking Comparison") {
        val nProcessor = Runtime.getRuntime().availableProcessors()
        val url = "https://www.bing.com/"
        val tryCount = nProcessor * 10
        val printEach = false

        fun request(url: String) {
            // cpu process
            (1..100_000).reduce { acc, next -> acc + next }

            // io request
            URL(url).readBytes()
        }

        runBlocking {
            val executorSevice = Executors.newFixedThreadPool(nProcessor)
            val threadPoolMs = measureTimeMillis {
                val futures = (1..tryCount).map {
                    executorSevice.submit {
                        request(url)
                        if (printEach) {
                            printWithThread("finished (executorSevice)")
                        }
                    }
                }
                futures.forEach { it.get() }
            }
            executorSevice.shutdownNow()

            val coroutineDefaultMs = measureTimeMillis {
                /*
                    Dispatchers.Default

                    - default CoroutineDispatcher used by all standard builders like (launch, async)
                    - default # of thread == # of core
                 */
                withContext(Dispatchers.Default) {
                    repeat(tryCount) {
                        launch {
                            request(url)
                            if (printEach) {
                                printWithThread("finished (Dispatchers.Default)")
                            }
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
                        launch {
                            request(url)
                            if (printEach) {
                                printWithThread("finished (Dispatchers.IO)")
                            }
                        }
                    }
                }
            }

            printWithThread("threadPool: ${threadPoolMs}ms")
            printWithThread("coroutine(Dispatchers.Default): ${coroutineDefaultMs}ms")
            printWithThread("coroutine(Dispatchers.IO): ${coroutineIoMs}ms")
        }
    }
}
