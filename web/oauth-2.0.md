# OAuth 2.0

- [OAuth 2.0](#oauth-20)
  - [Roles](#roles)
  - [Authorization Grant](#authorization-grant)
    - [Authorization Code](#authorization-code)
    - [Implicit](#implicit)
    - [Resource Owner Password Credentials](#resource-owner-password-credentials)
    - [Client Credentials](#client-credentials)
  - [References](#references)

## Roles

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

## Authorization Grant

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

## References

2012

https://tools.ietf.org/html/rfc6749