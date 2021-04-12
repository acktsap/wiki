package acktsap.coroutine

import acktsap.Block
import acktsap.printWithThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

fun main() {
    Block("IO Blocking Comparison") {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://www.naver.com:91"))
            .timeout(Duration.ofSeconds(5))
            .build()

        val nProcessor = Runtime.getRuntime().availableProcessors()
        val tryCount = 2 * nProcessor

        fun request() {
            try {
                val client = HttpClient.newHttpClient()
                client.send(request, HttpResponse.BodyHandlers.ofString())
            } catch (ignored: Exception) {
            }
        }

        runBlocking {
            val executorSevice = Executors.newFixedThreadPool(2 * nProcessor)
            val threadPoolMs = measureTimeMillis {
                val futures = (1..tryCount).map {
                    // started가 한번에 찍힌 이후 finished가 한번에 찍힘
                    executorSevice.submit {
                        printWithThread("started (executorSevice)")
                        request()
                        printWithThread("finished (executorSevice)")
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
                        // started가 core개수만큼씩만 한번에 찍힘
                        // Dispatchers.Default는 core 수만큼 thread가 있는데
                        // 다 blocking이 되어서 다른 task로 context switching이 미발생
                        launch {
                            printWithThread("started (Dispatchers.Default)")
                            request()
                            printWithThread("finished (Dispatchers.Default)")
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
                            printWithThread("started (Dispatchers.IO)")
                            request()
                            printWithThread("finished (Dispatchers.IO)")
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
