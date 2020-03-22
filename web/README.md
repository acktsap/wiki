# Protocol Summary

## HTTP 0.7 1.1 vs 2.0 vs 3.0

HTTP 1.1은 비연결형임.

HTTP 3.0은 UDP기반이라는 것 말고는 잘 모름.

## HTTP는 연결형인가 비연결형인가?

## HTTPS

그냥 HTTP에서 통신 구간의 transport layer에 TLS를 적용한 것임.

## OAuth1.0a vs OAuth2.0

## Streaming vs WebSocket

## URI

## JWT

## CSRF

Cross Site Request Forger. 정상적인 사이트에서 인증받은 쿠키를 가지고 이상한 사이트를 통해 요청하게 되면 이상한 사이트에서 인증받은 쿠키를 가지고 사용자가 원하지 않는 요청을 하는 것을 말함. 이를 막는 방법으로는 input form에 csrf라는 hidden value를 삽입해서 Client 요청시 그 값을 비교함으로써 서버가 전송한 페이지를 이용해서 들어온 요쳥만 서버에서 처리하는 식이 있음.

## CORS

## Cookie vs Session vs Cache

## Rest API

Representational State Transfer의 약자로 요청을 resources와 그것에 대한 HTTP Method로 디자인 하는 방식임. 여기서 resources는 복수 명사만 사용해야 함. 예를 들면 item이라는 resource가 있으면 items을 사용. stateless, cacheable, 계층형 구조, 서버-클라이언트 구조를 가져야 한다는 몇가지 특성이 있음. 이것에 대한 표준은 딱히 없고 그냥 이런 식으로 하자는게 있음. 생각해 보면 defacto 표준이지 않을까 싶음. OSI 7계층 말고 사실상 TCP/IP를 사용하는 것처럼.

## Rest API over HTTP2

## References

https://medium.com/platform-engineer/evolution-of-http-69cfe6531ba0

https://medium.com/platform-engineer/web-api-design-35df8167460

Http Security Headers

https://owasp.org/www-project-secure-headers/#tab=Headers

Rest API over HTTP2

https://dzone.com/articles/benefits-of-rest-apis-with-http2
