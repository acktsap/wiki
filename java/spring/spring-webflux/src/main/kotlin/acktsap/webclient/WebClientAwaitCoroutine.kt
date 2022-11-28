package acktsap.webclient

import io.netty.channel.ChannelOption
import kotlinx.coroutines.async
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.netty.http.client.HttpClient
import java.time.Duration
import kotlin.system.measureTimeMillis

fun main() {
    val httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
        .responseTimeout(Duration.ofSeconds(1))
    val webClient = WebClient.builder()
        .baseUrl("https://www.naver.com:91")
        .clientConnector(ReactorClientHttpConnector(httpClient))
        .build()

    val withoutAwait = measureTimeMillis {
        runBlocking {
            (1..3).map {
                async {
                    webClient.get()
                        .retrieve()
                        .bodyToMono<String>()
                        .onErrorReturn("failed")
                        .blockOptional()
                }
            }
        }
    }

    val usingAwait = measureTimeMillis {
        runBlocking {
            (1..3).map {
                async {
                    webClient.get()
                        .retrieve()
                        .bodyToMono<String>()
                        .onErrorReturn("failed")
                        .awaitFirstOrNull()
                }
            }
        }
    }

    println("without await: ${withoutAwait}ms") // 느려
    println("using await: ${usingAwait}ms") // 빨라
}
