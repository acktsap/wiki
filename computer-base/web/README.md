# Protocol Summary

- [Protocol Summary](#protocol-summary)
  - [HTTP](#http)
  - [HTTPS](#https)
  - [OAuth](#oauth)
    - [OAuth1.0a](#oauth10a)
    - [OAuth2.0](#oauth20)
  - [Streaming vs WebSocket](#streaming-vs-websocket)
  - [URI](#uri)
  - [JWT](#jwt)
  - [CSRF](#csrf)
  - [CORS](#cors)
  - [Cookie vs Session vs Cache](#cookie-vs-session-vs-cache)
  - [Rest API](#rest-api)
  - [Rest API over HTTP2](#rest-api-over-http2)
  - [References](#references)

## HTTP

HTTP (HyperText Transfer Protocol)는 웹에서 request-response를 받을 수 있는 규약임

HTTP/0.9는 초기의 Protocol로 GET방식으로 단순히 데이터를 받아오는 것만 제공함. Header도 없음.

HTTP/1.0은 0.9에 이어서 나왔는데 0.9에서 여러 기능을 확장하려고 나옴. GET에 HEAD, POST등이 추가됨. But 여기서는 요청을 할 때 마다 TCP handshaking을 해야 하는 문제점이 있었음.

HTTP/1.1은 1.0의 단점을 보완하고 여러 기능 추가를 한 것임. DELETE, PUT등이 추가되었고 Keep-Alive라는 것을 추가함으로써 특정 timeout 이내에 요청이 오면 이전에 만든 Connection을 재사용하는 방법을 제공함. HTTP/1.1은 Upgrade header라는 것을 제공해서 HTTP/2.0이나 WebSocket등으로 connection을 바꾸는 방법도 제공함.

HTTP/2.0은 HTTP/1.1과 호환을 유지하면서 속도만 빠르게 할려고 나옴. 큰 차이점은 HTTP1.1은 비연결형이라서 keep-alive를 하지 않으면 요청을 한 후 닫아버리는데 HTTP2.0은 연결형임.HTTP/2.0은 연결형이기 때문에 frame단위로 동시에 여러 요청을 할 수 있고 (multiplexing) Server push도 가능함. 또 Header나 frame에 압축도 적용해서 HTTP/1.1에 비해 속도가 더 빠름.

HTTP 3.0은 UDP기반이라는 것 말고는 잘 모름.

## HTTPS

그냥 HTTP에서 통신 구간의 transport layer에 TLS를 적용한 것임.

## OAuth

OAuth는 Open Authentication (또는 Authorization)를 뜻하는 것으로 User의 password를 Third party앱에 제공하지 않고 인증, 허가를 해주는 프로토콜임. 인증은 Service Provider가 해주고 Access Token을 발급해줘서 Third party가 Service Provider의 API를 안전하게 이용하게해줌.

### OAuth1.0a

### OAuth2.0

OAuth 2.0은 1.0보다 간소화된 모델임. 다음의 role이 있음

- Resource Owner : 사용자 (User in 1.0a)
- Resource Server : 리소스 서버 (Protected Resource in 1.0a)
- Authorizaiton Server : 인증 서버로 Access Token을 받급해줌 (Service Provider in 1.0a)
- Client : third-party application으로 access token을 가지고 resource server에 요청을 함 (Service Provider in 1.0a)

4가지 방식이 있는데 대표적으로 사용되는 방식은 Authorization Grant방식임. 간단하게 설명하면 다음과 같음

![oauth2.0-authorization-code-grant](./img/oauth2.0-authorization-code-grant.png)

1. Client가 Resource Owner의 User Agent에 Client ID와 redirection URI를 주면서 Authroization Endpoint로 접근하라고 지시
2. User Agent가 Client ID, redirect URI를 주면서 Authorization Server에 login 페이지 요청
3. Authorization Server가 User Agent에 Login Page제공
4. User가 Authentication 정보 전달.
5. Authorization Server가 Authorization Code발급.
6. User Agent가 Redirection URI로 Client에 Authorization Code 전달
7. Client가 Authorization Code를 활용하여 Authorization Server에 Access token 요청
8. Authorization Server가 Access Token을 Client에 발급
9. Client는 User Agent로부터 서비스 요청을 받으면 Access Token을 사용해서 Resource Server에서 API를 호출.

## Streaming vs WebSocket

Streaming은 HTTP요청을 한번 한 후 body에서 계속 받는 식임. WebSocket은 TCP기반으로 Bi-directional하게 보낼 수 있는 프로토콜임. HTTP Upgrade할 때만 HTTP를 사용함. Streaming은 body에 데이터를 계속 채워넣는 식이라서 보내는 데이터 조각들간 간섭이 있을 수 있지만 WebSocket은 이전 데이터와 상관 없이 보낼 수 있음.

## URI

Uniform Resource Identifier의 약자로 Resource를 식별하기 위한 표준임. schema://authority/path 이런 식으로 구분됨. URL (Uniform Resource Locator)와 URN (Uniform Resource Name)을 포함함.

## JWT

JSON Web Token은 인증 정보를 스스로 담고 있는 토큰으로 JSON형태의 인증 정보에 Signature포함하여 encoding한 후 전달하는 식임. 사용자 인증시 필요한 정보가 토큰에 모두 담겨있기 때문에 별도의 인증 저장소가 필요없다는 장점이 있으나 유효기간이 만료되기 전에 탈취당하면 서버에서는 이를 알 수 없기 때문에 보안상 취약. session cookie방식에 비해 길이가 길다는 단점도 있음.

## CSRF

Cross Site Request Forger. 정상적인 사이트에서 인증받은 쿠키를 가지고 이상한 사이트를 통해 요청하게 되면 이상한 사이트에서 인증받은 쿠키를 가지고 사용자가 원하지 않는 요청을 하는 것을 말함. 이를 막는 방법으로는 input form에 csrf라는 hidden value를 삽입해서 Client 요청시 그 값을 비교함으로써 서버가 전송한 페이지를 이용해서 들어온 요쳥만 서버에서 처리하는 식이 있음.

## CORS

Cross-Origin Resource Sharing은 브라우져에 요청한 출처가 아 닌 다른 출처로부터 자원을 받아올 수 있게 권한을 부여하도록 알려주는 것. 쉽게 말해서 브라우져로 'www.naver.com'에 들어갔는데 naver에서 'www.daum.com'로부터 자원을 불러올 수 있게 알려주는 것임. 보안상의 이유로 보통은 제한하는데 image resource등을 가져오기 위해 특정 uri만 풀어주기도 함. 허락되는 URI는 HTTP Header에 'Access-Control-Allow-Origin'으로 표현함.

## Cookie vs Session vs Cache

Cookie는 클라이언트에 대한 정보를 클라이언트의 PC에 저장하기 위해 서버에서 클라이언트로 보내는 정보를 말함. Session은 일정 시간 동안 사용자로부터 들어오는 요구를 하나의 상태로 보고 그것에 ID를 부여하여 일정하게 유지시키는 기술로 서버에서 ID를 저장함. Session ID를 사용할 때 Cookie를 이용할 수 있음.

Cache는 같은 요청에 대해 네트워크를 타지 않으려고 사용하는 것으로 요청이 들어왔을 때 데이터나 값을 복사해 그것을 반환하는 것임. Browser단, Proxy, Gateway에서 cache를 사용할 수 있음.

## Rest API

Representational State Transfer의 약자로 요청을 resources와 그것에 대한 HTTP Method로 디자인 하는 방식임(CRUD -> POST, GET, PUT, DELETE). 여기서 resources는 복수 명사만 사용해야 함. 예를 들면 item이라는 resource가 있으면 items을 사용. 특징으로는 다음과 같음

- Stateless : 상태가 없어야 함
- Cacheable : cache할 수 있어야 함
- 계층형 구조 : 다중 계층으로 구성될 수 있어서 Load Balancing, 보안, 암호화 계층을 쉽게 추가할 수 있어야함
- Server-Client : 서버는 Rest API제공, 클라이언트는 session같은 것을 관리하는 식으로 각각의 역할이 구분되어 있음.

이것에 대한 표준은 딱히 없고 그냥 이런 식으로 하자는게 있음. 생각해 보면 defacto 표준이지 않을까 싶음. OSI 7계층 말고 사실상 TCP/IP를 사용하는 것처럼.

## Rest API over HTTP2

HTTP/2.0이 HTTP/1.1와 하위호환을 유지하면서 속도만 빠르게 하려고 한거라 그대로 사용 가능함. 형태는 유지하면서 Multiflexing, header & frame압축, Server push 등을 이용할 수 있음. But HTTP/2.0은 binary단위로 전송하기 때문에 이를 클라이언트에서 해석해야 하는 번거로움이 생김.

## References

HTTP

https://medium.com/platform-engineer/evolution-of-http-69cfe6531ba0

https://medium.com/platform-engineer/web-api-design-35df8167460

OAuth

http://homoefficio.github.io/2018/08/27/%EC%8A%A4%ED%8E%99%EB%94%B0%EB%9D%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EB%8A%94-OAuth-1-0a-Client/

JWT Token

https://velopert.com/2389

https://brownbears.tistory.com/440

CORS

https://developer.mozilla.org/ko/docs/Web/HTTP/CORS

Cookie, Session, Cache

Http Security Headers

https://owasp.org/www-project-secure-headers/#tab=Headers

Rest API over HTTP2

https://dzone.com/articles/benefits-of-rest-apis-with-http2
