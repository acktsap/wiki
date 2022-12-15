# JWT

- [Concept](#concept)
- [Protocol](#protocol)
- [Usage](#usage)
- [See also](#see-also)

## Concept

- JSON Web Token
- 서로간에 json 형식으로 정보를 교환하고 싶은데 그냥 막 하면 안되잖아? 그래서 sign을 해서 이걸 기반으로 검증하고 하는 표준.

## Protocol

![jwt](./img/authentication-jwt.png)

- Header : algorithm, token type을 저장.
  ```json
  {
    "alg": "HS256",
    "typ": "JWT"
  }
  ```
- Payload : 전달하고 싶은 데이터
  ```json
  {
    "sub": "1234567890",
    "name": "John Doe",
    "admin": true
  }
  ```
- Signature : header와 payload를 base64로 encoding한다음 `.`으로 합쳐서 sign하고 sha256 hash해버린 값
  ```sh
  HMACSHA256(
    base64UrlEncode(header) + "." +
    base64UrlEncode(payload),
    secret)
  ```
- `header.payload.signature`

Pros & Cons

- Pros
  - vs Security Assertion Markup Language Tokens (SAML) : xml보다 보기 쉽고 기본적으로 xml보다 작아서 encoding되었을 때 compact함.
- Cons
  - 

## Usage

![jwt-usage](./img/authentication-jwt-usage.png)

- Authentication Server에서 Header와 Payload를 합친거에 private key로 signature 생성해서 token 발급.
- Client는 Http request의 Header에 `Authorization: Bearer <token>` 식으로 넣어서 Resource Server에 요청.
- Resource Server는 Authentication Server에서 서명한 private key와 매칭되는 public key를 가지고 있음. 이걸로 token의 signature를 검증.

## See also

- [Introduction to JSON Web Tokens (jwt)](https://jwt.io/introduction)
- [JSON Web Token (JWT) (rfc7519)](https://datatracker.ietf.org/doc/html/rfc7519)
- [jwt debugger](https://jwt.io/#debugger-io) : jwt 검증하는 사이트