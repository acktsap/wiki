package acktsap.webclient

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

fun main() {
    runBlocking {
        val webClient = WebClient.create()
        webClient.get()
            .uri("/test")
            .retrieve()
            .bodyToMono<String>()
            .awaitFirstOrNull()
    }
}
