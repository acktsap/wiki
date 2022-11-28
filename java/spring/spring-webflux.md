# Spring Reactive

- [Spring WebFlux](#spring-webflux)
- [WebClient](#webclient)
- [WebSockets](#websockets)
- [Testing](#testing)
- [RSocket](#rsocket)
- [Reactive Libraries](#reactive-libraries)
- [References](#references)

## Spring WebFlux

## WebClient

- WebClient
  - A functional, fluent API based on Reactor.
  - Fully non-blocking, supports streaming, uses the same codecs in `spring-core`, `spring-web`.
  - Connector
    - WebClient가 실제 연결이나 타임아웃 설정 같은거를 위임하는 interface.
    - ReactorClientHttpConnector
      - [uses Reactor HttpClient](https://projectreactor.io/docs/netty/release/reference/index.html#http-client)
        - 기본 pool size는 500개 고정

## WebSockets

## Testing

## RSocket

## Reactive Libraries

## References

- [Web Reactive (spring official docs)](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

