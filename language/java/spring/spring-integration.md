# Spring Integration

- [Rest Clients](#rest-clients)
  - [WebClient](#webclient)
  - [RestTemplate](#resttemplate)
  - [Http Interface](#http-interface)
- [See also](#see-also)

## Rest Clients

### WebClient

### RestTemplate

- Initialization
  - Without ClientHttpRequestFactory
    - ClientHttpRequestFactory 설정 안하면 `java.net.HttpURLConnection` 사용.
  - Using ClientHttpRequestFactory
    - Apache HttpComponents
    - Netty
    - OkHttp
- Request
  - URI
  - Header
  - Body
- [Message Conversion](./spring-integration/src/main/java/acktsap/spring/resttemplate/converter)
  - [참고](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-message-conversion)

### Http Interface

## See also

- [Spring Integration](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#spring-integration)
