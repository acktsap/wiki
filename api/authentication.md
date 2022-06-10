# Authentication

- [What is Authentication?](#what-is-authentication)
- [Authentication factors](#authentication-factors)
- [Basic Authentication](#basic-authentication)
  - [What?](#what)
  - [Protocol](#protocol)
- [Token Based Authentication](#token-based-authentication)
  - [What?](#what-1)
- [SAML](#saml)
- [SWT](#swt)
- [JWT](#jwt)
  - [What?](#what-2)
  - [Protocol](#protocol-1)
  - [Usage](#usage)
- [OAuth](#oauth)
  - [OAuth 1.0](#oauth-10)
  - [OAuth 1.0a](#oauth-10a)
  - [OAuth 2.0](#oauth-20)
  - [OpenID](#openid)
- [Reference](#reference)

## What is Authentication?

- The act of proving an assertion, such as the identity of a computer system user.
  > 니가 누군지 밝혀내는 과정.

## Authentication factors

- Something the user knows, something the user has, and something the user is.
  > 니가 아는 것 또는 니가 가지고 있는 것, 또는 너 자신이 누구인가?
- the knowledge factors: Something the user knows (eg. a password, partial password, passphrase, personal identification number (PIN), challenge–response (the user must answer a question or pattern), security question).
- the ownership factors: Something the user has (eg. wrist band, ID card, security token, implanted device, cell phone with a built-in hardware token, software token, or cell phone holding a software token).
- the inherence factors: Something the user is or does (eg. fingerprint, retinal pattern, DNA sequence (there are assorted definitions of what is sufficient), signature, face, voice, unique bio-electric signals, or other biometric identifiers).

> 웹에서는 거의 knowledge factor를 사용한다고 볼 수 있음.

## Basic Authentication

### What?

- http request를 할 때 인증을 하고 싶어 근데 간단하게 header에 인증 데이터를 넣고 해보자!
- http request header에 user name과 password를 제공해서 하는 인증방식.
- 장점
  - session 관리, cookie 같은거 없이 간단하게 사용 가능.

### Protocol

- http header에 `Authentication: Basic <credentials>`의 형식으로 추가함.
  - eg. `Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==`
- credentials은 ID와 password 사이에 `:`를 붙여서 base64로 encoding한 형식.
- browser에서 요청 할 때 마다 사용해야 해서 보통 cache해버림.
- server에서는 unauthenticate 요청일 경우 401 status code와 함께 다음의 header를 반환.
  - `WWW-Authenticate: Basic realm="User Visible Realm"`


## Token Based Authentication

### What?

- 인증을 할 때 username과 password를 계속 제공하는데 이게 싫다. 한번 인증하고 나면 제한시간이 있는 token이란걸 발급해서 그걸 사용해서 접근하게 해라.
- authentication에 one level을 추가한 것.
- 장점
  - usename과 password를 매번 제공 안해도 됨.

## SAML

- Security Assertion Markup Language.
- xml 형식으로 인증하는 표준.

> 사실 요즘은 json 쓰잖아?

## SWT

- Simple Web Token.
- 딱히 표준이 없는듯? <- todo: 맞아??

## JWT

### What?

- JSON Web Token
- 서로간에 json 형식으로 정보를 교환하고 싶은데 그냥 막 하면 안되잖아? 그래서 sign을 해서 이걸 기반으로 검증하고 하는 표준.
- 장점
  - vs Security Assertion Markup Language Tokens (SAML) : xml보다 보기 쉽고 기본적으로 xml보다 작아서 encoding되었을 때 compact함.

### Protocol

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

see also

- [jwt debugger](https://jwt.io/#debugger-io) : jwt 검증하는 사이트

### Usage

![jwt-usage](./img/authentication-jwt-usage.png)

- Http request에서 header에 `Authorization: Bearer <token>` 식으로 넣어서 사용함.
- 일반적인 flow는
  1. client가 authentication server에서 token 요청.
  2. token client에 발급.
  3. token을 사용해서 resource에 접근.

## OAuth

- Open Authorization.
- Internet user가 다른 website의 password를 제공하지 않고 현재 website가 다른 website에 있는 자기들의 정보를 이용할 수 있게 해주고 싶다? 그래서 만든 표준.

### OAuth 1.0

### OAuth 1.0a

### OAuth 2.0

### OpenID

- OAuth 2.0 위에 있는 protocal로.. todo

## Reference

- [Authentication (wiki)](https://en.wikipedia.org/wiki/Authentication)
- [Basic access authentication (wiki)](https://en.wikipedia.org/wiki/Basic_access_authentication)
- [What is token-based authentication? (stackoverflow)](https://stackoverflow.com/questions/1592534/what-is-token-based-authentication)
- [Simple Web Token (SWT) (Microsoft)](https://docs.microsoft.com/en-us/previous-versions/azure/azure-services/hh781551(v=azure.100)?redirectedfrom=MSDN)
- [Introduction to JSON Web Tokens (jwt)](https://jwt.io/introduction)
- [OAuth 2.0 (ietf)](https://tools.ietf.org/html/rfc6749)
- [OAuth (oauth.net)](https://oauth.net/2/)