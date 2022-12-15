# OAuth

- [Concept](#concept)
- [OAuth 1.0](#oauth-10)
- [OAuth 1.0a](#oauth-10a)
- [OAuth 2.0](#oauth-20)
  - [Roles](#roles)
  - [Authorization Grant](#authorization-grant)
  - [Authorization Code](#authorization-code)
  - [Implicit](#implicit)
  - [Resource Owner Password Credentials](#resource-owner-password-credentials)
  - [Client Credentials](#client-credentials)
  - [OpenID](#openid)
  - [OpenID Connect](#openid-connect)
- [See also](#see-also)

## Concept

- Open Authorization.
- Internet user가 다른 website의 password를 제공하지 않고 현재 website가 다른 website에 있는 자기들의 정보를 이용할 수 있게 해주고 싶어서 만든 표준.

## OAuth 1.0

## OAuth 1.0a

## OAuth 2.0

https://meetup.toast.com/posts/105

### Roles

```text
resource owner
  An entity capable of granting access to a protected resource.
  When the resource owner is a person, it is referred to as an
  end-user.

resource server
  The server hosting the protected resources, capable of accepting
  and responding to protected resource requests using access tokens.

client
  An application making protected resource requests on behalf of the
  resource owner and with its authorization.  The term "client" does
  not imply any particular implementation characteristics (e.g.,
  whether the application executes on a server, a desktop, or other
  devices).

authorization server
  The server issuing access tokens to the client after successfully
  authenticating the resource owner and obtaining authorization.

> resource owner : 사용자\
> resource server : 사용자에게 자원을 제공하는 서버. access token을 통해 인증함\
> client : resource request를 만드는 주체로써 resource owner가 사용함\
> authorization server : 사용자를 인증함으로써 access token을 제공하는 서버
```

### Authorization Grant

```text
An authorization grant is a credential representing the resource
owner's authorization (to access its protected resources) used by the
client to obtain an access token
```

> Authorization grant는 resource owner에게 인증을 부여하는 방식을 말함. 4가지 방식이 있음.

### Authorization Code

```text
    +----------+
    | Resource |
    |   Owner  |
    |          |
    +----------+
         ^
         |
        (B)
    +----|-----+          Client Identifier      +---------------+
    |         -+----(A)-- & Redirection URI ---->|               |
    |  User-   |                                 | Authorization |
    |  Agent  -+----(B)-- User authenticates --->|     Server    |
    |          |                                 |               |
    |         -+----(C)-- Authorization Code ---<|               |
    +-|----|---+                                 +---------------+
      |    |                                         ^      v
     (A)  (C)                                        |      |
      |    |                                         |      |
      ^    v                                         |      |
    +---------+                                      |      |
    |         |>---(D)-- Authorization Code ---------'      |
    |  Client |          & Redirection URI                  |
    |         |                                             |
    |         |<---(E)----- Access Token -------------------'
    +---------+       (w/ Optional Refresh Token)

(A)  The client initiates the flow by directing the resource owner's
    user-agent to the authorization endpoint.  The client includes
    its client identifier, requested scope, local state, and a
    redirection URI to which the authorization server will send the
    user-agent back once access is granted (or denied).

(B)  The authorization server authenticates the resource owner (via
    the user-agent) and establishes whether the resource owner
    grants or denies the client's access request.

(C)  Assuming the resource owner grants access, the authorization
    server redirects the user-agent back to the client using the
    redirection URI provided earlier (in the request or during
    client registration).  The redirection URI includes an
    authorization code and any local state provided by the client
    earlier.

(D)  The client requests an access token from the authorization
    server's token endpoint by including the authorization code
    received in the previous step.  When making the request, the
    client authenticates with the authorization server.  The client
    includes the redirection URI used to obtain the authorization
    code for verification.

(E)  The authorization server authenticates the client, validates the
    authorization code, and ensures that the redirection URI
    received matches the URI used to redirect the client in
    step (C).  If valid, the authorization server responds back with
    an access token and, optionally, a refresh token.
```

### Implicit

### Resource Owner Password Credentials

### Client Credentials

### OpenID

- Decentralized authentication protocol.

### OpenID Connect

- Authentication를 위한 protocal.
- OpenID의 일종인데 OAuth2.0 위에서 돌아감.
- OAuth2.0의 scope에다가 openid라는걸 정의해서 요청하면 access token을 줄 때 authentication을 위한 id token을 주는 형식으로 동작함. 보통 JWT token.

## See also

- [OAuth 1.0 (ietf)](https://tools.ietf.org/html/rfc5849)
- [OAuth 1.0a (ietf)](https://oauth.net/core/1.0a/)
- [OAuth 2.0 (oauth.net)](https://oauth.net/2/)
- [OAuth 2.0 (ietf)](https://tools.ietf.org/html/rfc6749)
- [OpenID (wiki)](https://en.wikipedia.org/wiki/OpenID)
- [openid-connect-core](https://openid.net/specs/openid-connect-core-1_0.html)