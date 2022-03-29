package acktsap.coroutine

import acktsap.Block
import acktsap.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

@Suppress("BlockingMethodInNonBlockingContext", "DuplicatedCode")
fun main() {
    Block("IO Blocking Comparison") {
        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://www.naver.com:91"))
            .timeout(Duration.ofSeconds(5))
            .build()

        val nProcessor = Runtime.getRuntime().availableProcessors()

        fun sendRequest(dispatcherName: String) {
            try {
                log("started ($dispatcherName)")
                val client = HttpClient.newHttpClient()
                client.send(httpRequest, HttpResponse.BodyHandlers.ofString())
            } catch (ignored: Exception) {
            } finally {
                log("finished ($dispatcherName)")
            }
        }

        // 바로 이거임 이렇게 해라
        suspend fun sendRequestAsync(dispatcherName: String) {
            try {
                log("started ($dispatcherName)")
                val client = HttpClient.newHttpClient()
                val response = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                response.await()
            } catch (ignored: Exception) {
            } finally {
                log("finished ($dispatcherName)")
            }
        }

        val executorService = Executors.newFixedThreadPool(2 * nProcessor)
        val threadPoolMs = measureTimeMillis {
            val futures = (1..2 * nProcessor).map {
                // started가 한번에 찍힌 이후 finished가 한번에 찍힘
                executorService.submit {
                    log("started (executorService)")
                    sendRequest("executorService")
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
            runBlocking {
                withContext(Dispatchers.Default) {
                    (1..2 * nProcessor).map {
                        // started가 core개수만큼씩만 한번에 찍힘
                        // Dispatchers.Default는 core 수만큼 thread가 있는데
                        // 다 blocking이 되어서 다른 task로 context switching이 미발생
                        async { sendRequest("Dispatchers.Default") }
                    }.awaitAll()
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
            runBlocking {
                withContext(Dispatchers.IO) {
                    (1..2 * nProcessor).map {
                        // started가 한번에 찍힌 이후 finished가 한번에 찍힘
                        // Dispatchers.IO는 I/O intensive job을 위해 pool size를 넉넉하게 잡아놓아서 (기본 : 64) suspend해도 문제 없음
                        async { sendRequest("Dispatchers.IO") }
                    }.awaitAll()
                }
            }
        }

        val coroutineDefaultMsWithSuspend = measureTimeMillis {
            /*
                Dispatchers.Default

                - default CoroutineDispatcher used by all standard builders like (launch, async)
                - default # of thread == # of core
             */
            runBlocking {
                withContext(Dispatchers.Default) {
                    (1..2 * nProcessor).map {
                        // started가 전부 한번에 찍힘
                        // Dispatchers.Default는 core 수만큼 thread가 있지만
                        // suspend function을 async로 호출해서 병렬처리 가능
                        async { sendRequestAsync("Dispatchers.Default with suspend") }
                    }.awaitAll()
                }
            }
        }

        log("threadPool(size: 2 * core): ${threadPoolMs}ms") // 5000ms
        log("coroutine(Dispatchers.Default): ${coroutineDefaultMs}ms") // 10000ms
        log("coroutine(Dispatchers.IO): ${coroutineIoMs}ms") // 5000ms
        log("coroutine(Dispatchers.Default with suspend): ${coroutineDefaultMsWithSuspend}ms") // 5000ms
    }
}
