# Authentication

- [What is Authentication?](#what-is-authentication)
- [Authentication factors](#authentication-factors)
- [Token Based Authentication](#token-based-authentication)
- [OAuth](#oauth)
  - [1.0](#10)
  - [1.0a](#10a)
  - [2.0](#20)
- [JWT](#jwt)
- [On Http](#on-http)
  - [Basic Authentication](#basic-authentication)
- [OpenID](#openid)
- [SAML](#saml)
- [Reference](#reference)

## What is Authentication?

- The act of proving an assertion, such as the identity of a computer system user.
  > 니가 누군지 밝혀내는 과정.

## Authentication factors

- Something the user knows, something the user has, and something the user is.
- 니가 아는 것 또는 니가 소유하고 있는 것, 또는 너 자신이 누구인가?
- the knowledge factors: Something the user knows (eg. a password, partial password, passphrase, personal identification number (PIN), challenge–response (the user must answer a question or pattern), security question).
- the ownership factors: Something the user has (eg. wrist band, ID card, security token, implanted device, cell phone with a built-in hardware token, software token, or cell phone holding a software token).
- the inherence factors: Something the user is or does (eg. fingerprint, retinal pattern, DNA sequence (there are assorted definitions of what is sufficient), signature, face, voice, unique bio-electric signals, or other biometric identifiers).

> 웹에서는 거의 knowledge factor를 사용한다고 볼 수 있음.

## Token Based Authentication

What?

- 인증을 할 때 username과 password를 계속 제공하는데 이게 싫다. 한번 인증하고 나면 제한시간이 있는 token이란걸 발급해서 그걸 사용해서 접근하게 해라.
- authentication에 one level을 추가한 것.

장점

- usename과 password를 매번 제공 안해도 됨.

## OAuth

### 1.0

### 1.0a

### 2.0

## JWT

## On Http

### Basic Authentication

What?

- http request를 할 때 인증을 하고 싶어 근데 간단하게 header에 인증 데이터를 넣고 해보자!
- http request header에 user name과 password를 제공해서 하는 인증방식.

Protocol

- http header에 `Authentication: Basic <credentials>`의 형식으로 추가함.
  - eg. `Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==`
- credentials은 ID와 password 사이에 `:`를 붙여서 base64로 encoding한 형식.
- browser에서 요청 할 때 마다 사용해야 해서 보통 cache해버림.
- server에서는 unauthenticate 요청일 경우 401 status code와 함께 다음의 header를 반환.
  - `WWW-Authenticate: Basic realm="User Visible Realm"`

장점

- session 관리, cookie 같은거 없이 간단하게 사용 가능.

## OpenID

## SAML

## Reference

- [Authentication (wiki)](https://en.wikipedia.org/wiki/Authentication)
- [Basic access authentication (wiki)](https://en.wikipedia.org/wiki/Basic_access_authentication)
- [What is token-based authentication? (stackoverflow)](https://stackoverflow.com/questions/1592534/what-is-token-based-authentication)