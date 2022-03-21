package acktsap.webclient;

import org.springframework.web.reactive.function.client.WebClient;

/*
 * WebClient has a functional, fluent API based on Reactor,
 * which enables declarative composition of asynchronous logic without the need to deal with threads or concurrency.
 *
 * It is fully non-blocking, it supports streaming, and relies on the same codecs
 * that are also used to encode and decode request and response content on the server side.
 */
public class WebClientConfigTest {
    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://www.naver.com");
    }
}
