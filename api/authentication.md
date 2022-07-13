# Authentication

- [Authentication vs Authorization](#authentication-vs-authorization)
- [Authentication factors](#authentication-factors)
- [Authorization Methods](#authorization-methods)
  - [Role-based access controls (RBAC)](#role-based-access-controls-rbac)
  - [Attribute-based access control (ABAC)](#attribute-based-access-control-abac)
- [Security Realms (보안영역)](#security-realms-보안영역)
- [Basic Authentication](#basic-authentication)
- [Proxy Authentication](#proxy-authentication)
- [Digest Authentication](#digest-authentication)
- [Session vs Cookie](#session-vs-cookie)
- [Token Based Authentication](#token-based-authentication)
- [SAML](#saml)
- [SWT](#swt)
- [JWT](#jwt)
  - [Usage](#usage)
- [OAuth](#oauth)
  - [OAuth 1.0](#oauth-10)
  - [OAuth 1.0a](#oauth-10a)
  - [OAuth 2.0](#oauth-20)
  - [OpenID](#openid)
  - [OpenID Connect](#openid-connect)
- [Kerberos](#kerberos)
- [Reference](#reference)

## Authentication vs Authorization

- Authentication : The act of proving who you are.
  > 니가 누군지 밝혀내는 과정.
- Authorization : The function of specifying access rights/privileges to resources.
  > 인증 이후 자원에 접근이 가능한지 확인하는 과정.

## Authentication factors

- the knowledge factors : Something the user knows (eg. a password, partial password, passphrase, personal identification number (PIN), challenge–response (the user must answer a question or pattern), security question).
  > 니가 아는 것.
- the ownership factors : Something the user has (eg. wrist band, ID card, security token, implanted device, cell phone with a built-in hardware token, software token, or cell phone holding a software token).
  > 니가 가지고 있는 것.
- the inherence factors : Something the user is or does (eg. fingerprint, retinal pattern, DNA sequence (there are assorted definitions of what is sufficient), signature, face, voice, unique bio-electric signals, or other biometric identifiers).
  > 니 몸에 있는거.

## Authorization Methods

### Role-based access controls (RBAC)

### Attribute-based access control (ABAC)

## Security Realms (보안영역)

- It gives you the ability to protect a resource with a defined security constraint and then define the user roles that can access the protected resource.
- Authentication 요청을 했을 때 

## Basic Authentication

Intention

- http request를 할 때 인증을 하고 싶어 근데 간단하게 header에 인증 데이터를 넣고 해보자!
- http request header에 user name과 password를 `:`를 사이에 끼워서 concat하고 base64로 인코딩해서 보내는 방식.
- base64 encoding은 `:`같은 특수문자를 그대로 보내면 http header parsing 할 때 이상하게 되어서 그럼.

Protocol

- http header에 `Authentication: Basic <credentials>`의 형식으로 추가함.
  - eg. `Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==`
- server에서는 인증이 없는요청일 경우 401 status code와 함께 다음의 header를 반환.
  - `WWW-Authenticate: Basic realm="User Visible Realm"`
  - Realm은 보안 영역으로 이 영역에 맞게 인증 요청을 해라고 보면 됨. role이라고 보면 됨.

Pros & Cons

- Pros
  - Protocol이 simple함.
- Cons
  - Http header를 보면 id와 pw를 바로 알 수 있기 때문에 보안에 취약. 그래서 https랑 사용하기도 함.

## Proxy Authentication

## Digest Authentication

## Session vs Cookie

- Cookie
  - 브라우저에서 저장해두고 요청시마다 쓰는 값.
- Session
  - 인증 정보를 서버에서 관리하는 방식.
  - 그래서 Session을 쓸 때 보통 Cookie를 써서 Session Id를 보냄. 근데 꼭 cookie를 쓰지 않아도 됨. 핵심은 서버에서 관리하는 것.
 
## Token Based Authentication

- 서버에 인증정보를 가지고 싶지 않다. 그래서 인증 정보를 기간에 있는 token자체에 넣는 방식. Server가 Stateless함.
- authentication에 one level을 추가한 것.
- 보통 이렇게 사용 : `Authorization: Barrier ${token_value}`

Pros & Cons

- Pros
  - Server가 Stateless함.
  - usename과 password를 매번 제공 안해도 됨.
- Cons
  - 인증 정보가 token 자체에 있고 거기 기간이 있기 때문에 logout 시킬 수 없다.

## SAML

- Security Assertion Markup Language.
- xml 형식으로 인증하는 표준.

> 사실 요즘은 json 쓰잖아?

## SWT

- Simple Web Token.
- 딱히 표준이 없는듯? 그냥 토큰인데 단순한 형식인거 다 말하는 듯?

## JWT

Intension

- JSON Web Token
- 서로간에 json 형식으로 정보를 교환하고 싶은데 그냥 막 하면 안되잖아? 그래서 sign을 해서 이걸 기반으로 검증하고 하는 표준.

Protocol

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
- Internet user가 다른 website의 password를 제공하지 않고 현재 website가 다른 website에 있는 자기들의 정보를 이용할 수 있게 해주고 싶어서 만든 표준.

### OAuth 1.0

### OAuth 1.0a

### OAuth 2.0

### OpenID

- Decentralized authentication protocol.

### OpenID Connect

- Authentication를 위한 protocal.
- OpenID의 일종인데 OAuth2.0 위에서 돌아감.
- OAuth2.0의 scope에다가 openid라는걸 정의해서 요청하면 access token을 줄 때 authentication을 위한 id token을 주는 형식으로 동작함. 보통 JWT token.

## Kerberos

## Reference

- Authentication
  - [Authentication (wiki)](https://en.wikipedia.org/wiki/Authentication)
  - [Basic access authentication (wiki)](https://en.wikipedia.org/wiki/Basic_access_authentication)
  - [HTTP Authentication: Basic and Digest Access Authentication (rfc2617)](https://www.ietf.org/rfc/rfc2617.txt)
  - [What is the exact uses of REALM term in security? (stackoverflow)](https://stackoverflow.com/questions/8468075/what-is-the-exact-uses-of-realm-term-in-security)
  - [Create a Session Using Basic Authentication (vmware)](https://vdc-download.vmware.com/vmwb-repository/dcr-public/94b8bd8d-74ff-4fe3-b7a4-41ae31516ed7/1b42f3b5-8b31-4279-8b3f-547f6c7c5aa8/doc/GUID-536ED934-ECE3-4B17-B7E5-F8D0765C9ECB.html)
- Authorization
  - [Authorization (wiki)](https://en.wikipedia.org/wiki/Authorization)
- Common
  - [Cookie-based vs Session vs Token-based vs Claims-based authentications (softwareenginerring)](https://softwareengineering.stackexchange.com/questions/350092/cookie-based-vs-session-vs-token-based-vs-claims-based-authentications)
- Token
  - [What is token-based authentication? (stackoverflow)](https://stackoverflow.com/questions/1592534/what-is-token-based-authentication)
  - [Simple Web Token (SWT) (Microsoft)](https://docs.microsoft.com/en-us/previous-versions/azure/azure-services/hh781551(v=azure.100)?redirectedfrom=MSDN)
  - [Introduction to JSON Web Tokens (jwt)](https://jwt.io/introduction)
  - [JSON Web Token (JWT) (rfc7519)](https://datatracker.ietf.org/doc/html/rfc7519)
- OAuth
  - [OAuth (oauth.net)](https://oauth.net/2/)
  - [OAuth 2.0 (ietf)](https://tools.ietf.org/html/rfc6749)
  - [OpenID (wiki)](https://en.wikipedia.org/wiki/OpenID)
  - [openid-connect-core](https://openid.net/specs/openid-connect-core-1_0.html)
- [Kerberos (wiki)](https://en.wikipedia.org/wiki/Kerberos_(protocol))