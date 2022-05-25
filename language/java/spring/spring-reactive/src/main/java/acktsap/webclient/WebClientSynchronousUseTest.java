package acktsap.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class WebClientSynchronousUseTest {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://www.naver.com");

        // block mono
        {
            String person = client.get()
                .uri("/person/{id}", 1)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        }

        // block flux
        {
            List<String> persons = client.get()
                .uri("/persons")
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
        }

        {
            Mono<String> a = client.get()
                .uri("/persons")
                .retrieve()
                .bodyToMono(String.class);
        }
    }
}
