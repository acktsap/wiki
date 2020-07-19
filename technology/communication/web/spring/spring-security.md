# Spring Security

- [Spring Security](#spring-security)
  - [Features](#features)
    - [Authentication](#authentication)
      - [PasswordEncoder](#passwordencoder)
    - [Protection Against Exploits](#protection-against-exploits)
      - [CSRF](#csrf)
      - [HTTP Response Headers](#http-response-headers)
  - [Servlet Application](#servlet-application)
    - [Big Picture](#big-picture)
    - [Authentication](#authentication-1)
    - [Authorization](#authorization)
    - [OAuth Login](#oauth-login)
    - [OAuth Client](#oauth-client)
    - [OAuth Resource Server](#oauth-resource-server)
  - [References](#references)

## Features

### Authentication

Spring Security는 Username, password로 자원에 대한 인증 (authentication)과 허가 (authorization)을 하는 방법을 제공해줌.

#### PasswordEncoder

여기서 password는 `PasswordEncoder`라는 것을 사용해서 단방향 hash를 통해 안전하게 저장을 함. 처음에는 단순히 sha256같은 단순한 hash를 사용했으나 hash function에 대해서 결과값을 미리 저장해둔 Rainbow table같은 것을 공격자가 사용할 수 있기 때문에 salt + userpassword를 hash한 것을 가지고 hash함. salt + userpassword의 조합이 너무 많아서 **Rainbow table**같은거를 사용할 수가 없음.

Spring Security에서는 이것을 할 수 있는 여러 가지 구현체를 제공함. But 좋은 알고리즘은 상황마다 다를 수 있기 때문에 한단계 추상화한 `DelegatingPasswordEncoder`를 제공함. 이것을 통해 하위호환도 가능하고 Encoding방식의 update도 수월하게 할 수 있음.

```java
PasswordEncoder passwordEncoder =
    PasswordEncoderFactories.createDelegatingPasswordEncoder();
```

### Protection Against Exploits

#### CSRF

Cross Site Request Forgery란 정상적인 사이트에서 인증받은 쿠키를 가지고 이상한 사이트를 통해 요청하게 되면 이상한 사이트에서 인증받은 쿠키를 가지고 악의적인 요청을 하는 것을 말함.

예를 들면 이 사이트에서 쿠키를 발급 받고 어쩌다가

```html
<form method="post"
    action="/transfer">
<input type="text"
    name="amount"/>
<input type="text"
    name="routingNumber"/>
<input type="text"
    name="account"/>
<input type="submit"
    value="Transfer"/>
</form>

<!-- 요청 방법 -->
POST /transfer HTTP/1.1
Host: bank.example.com
Cookie: JSESSIONID=randomid
Content-Type: application/x-www-form-urlencoded

amount=100.00&routingNumber=1234&account=9876
```

여기로 와서 요청을 날리면 evils에 100.00의 돈을 날리게 됨.

```html
<form method="post"
    action="https://bank.example.com/transfer">
<input type="hidden"
    name="amount"
    value="100.00"/>
<input type="hidden"
    name="routingNumber"
    value="evilsRoutingNumber"/>
<input type="hidden"
    name="account"
    value="evilsAccountNumber"/>
<input type="submit"
    value="Win Money!"/>
</form>
```

이를 막는 방법은 두 가지가 있는데 하나는 Synchronizer Token Pattern이고 다른 하나는 SameSite Attribute가 있다.

Synchronizer Token Pattern은 서버가 페이지를 줄 때 input form에 hidden형태로 _csrf를 집어넣어서 요청한 것과 다른 browser에서 온 요청을 받지 않는 방법이다.

```html
<form method="post"
    action="/transfer">
<input type="hidden"
    name="_csrf"
    value="4bfd1575-3ad1-4d21-96c7-4ef2d9f86721"/>
<input type="text"
    name="amount"/>
<input type="text"
    name="routingNumber"/>
<input type="hidden"
    name="account"/>
<input type="submit"
    value="Transfer"/>
</form>

<!-- 요청 방법 -->
POST /transfer HTTP/1.1
Host: bank.example.com
Cookie: JSESSIONID=randomid
Content-Type: application/x-www-form-urlencoded

amount=100.00&routingNumber=1234&account=9876&_csrf=4bfd1575-3ad1-4d21-96c7-4ef2d9f86721
```

SameSite는 쿠키를 받은 사이트와 같은 사이트인 경우에만 쿠키를 집어넣어서 요청을 하는 기술로 표준의 일종이다.

```html
Set-Cookie: JSESSIONID=randomid; Domain=bank.example.com; Secure; HttpOnly; SameSite=Lax
```

#### HTTP Response Headers

HTTP의 Security를 높이기 위한 다양한 header들이 있음. 이것들에 대한 dsl을 제공함.

## Servlet Application

### Big Picture

### Authentication

### Authorization

### OAuth Login

### OAuth Client

### OAuth Resource Server

## References

Spring Security Official

https://docs.spring.io/spring-security/site/docs/current/reference/html5/

SameSite

https://tools.ietf.org/html/draft-west-first-party-cookies-07#section-2.1

Security Response Headers

https://owasp.org/www-project-secure-headers/#tab=Headers