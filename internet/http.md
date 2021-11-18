# Http

- [Http](#http)
  - [Http](#http-1)
  - [Http 1.0](#http-10)
  - [Http 1.1](#http-11)
    - [Background](#background)
    - [Terms](#terms)
    - [Structure](#structure)
    - [Simplest case](#simplest-case)
    - [With intermediary](#with-intermediary)
    - [With a cache](#with-a-cache)
    - [Http over TCP/IP](#http-over-tcpip)
    - [Keep-Alive](#keep-alive)
  - [Https](#https)
    - [Background](#background-1)
    - [Structure](#structure-1)
  - [Http 2.0](#http-20)
    - [Background](#background-2)
    - [Structure](#structure-2)
    - [Frame](#frame)
    - [Stream](#stream)
    - [Server Push](#server-push)
  - [Http 3.0](#http-30)
  - [References](#references)

## Http

HTTP (HyperText Transfer Protocol). 웹에서 request-response를 받을 수 있는 규약

## Http 1.0

## Http 1.1

### Background

```text
HTTP/1.0 does not sufficiently take into consideration the effects
of hierarchical proxies, caching, the need for persistent connections,
or virtual hosts
```

> Http/1.0의 한계를 보완해서 만듬

### Terms

```text
connection
  A transport layer virtual circuit established between two programs
  for the purpose of communication.

message
  The basic unit of HTTP communication, consisting of a structured
  sequence of octets and transmitted via the connection.

request
  An HTTP request message

response
  An HTTP response message

resource
  A network data object or service that can be identified by a URI,
  Resources may be available in multiple
  representations (e.g. multiple languages, data formats, size, and
  resolutions) or vary in other ways.

entity
  The information transferred as the payload of a request or
  response. An entity consists of metainformation in the form of
  entity-header fields and content in the form of an entity-body

representation
  An entity included with a response that is subject to content
  negotiation, as described in section 12. There may exist multiple
  representations associated with a particular response status.

content negotiation
  The mechanism for selecting the appropriate representation when
  servicing a request, The representation of entities in any response
  can be negotiated (including error responses).

variant
  A resource may have one, or more than one, representation(s)
  associated with it at any given instant.

client
  A program that establishes connections for the purpose of sending
  requests.

user agent
  The client which initiates a request. These are often browsers,
  editors, spiders (web-traversing robots), or other end user tools.

server
  An application program that accepts connections in order to
  service requests by sending back responses.

origin server
  The server on which a given resource resides or is to be created.

proxy
  An intermediary program which acts as both a server and a client
  for the purpose of making requests on behalf of other clients.
  
gateway
  A server which acts as an intermediary for some other server.
  Unlike a proxy, a gateway receives requests as if it were the
  origin server for the requested resource; the requesting client
  may not be aware that it is communicating with a gateway.

tunnel
  An intermediary program which is acting as a blind relay between
  two connections. Once active, a tunnel is not considered a party
  to the HTTP communication, though the tunnel may have been
  initiated by an HTTP request. The tunnel ceases to exist when both
  ends of the relayed connections are closed.

cache
  A program's local store of response messages and the subsystem
  that controls its message storage, retrieval, and deletion. A
  cache stores cacheable responses in order to reduce the response
  time and network bandwidth consumption on future, equivalent
  requests.

cacheable
  A response is cacheable if a cache is allowed to store a copy of
  the response message for use in answering subsequent requests.

first-hand
  A response is first-hand if it comes directly and without
  unnecessary delay from the origin server, perhaps via one or more
  proxies.

explicit expiration time
  The time at which the origin server intends that an entity should
  no longer be returned by a cache without further validation.

heuristic expiration time
  An expiration time assigned by a cache when no explicit expiration
  time is available.

age
  The age of a response is the time since it was sent by, or
  successfully validated with, the origin server.

freshness lifetime
  The length of time between the generation of a response and its
  expiration time.

fresh
  A response is fresh if its age has not yet exceeded its freshness
  lifetime.

stale
  A response is stale if its age has passed its freshness lifetime.

semantically transparent
  A cache behaves in a "semantically transparent" manner, with
  respect to a particular response, when its use affects neither the
  requesting client nor the origin server, except to improve
  performance.

validator
  A protocol element (e.g., an entity tag or a Last-Modified time)
  that is used to find out whether a cache entry is an equivalent
  copy of an entity.

upstream/downstream
  Upstream and downstream describe the flow of a message: all
  messages flow from upstream to downstream.

inbound/outbound
  Inbound and outbound refer to the request and response paths for
  messages: "inbound" means "traveling toward the origin server",
  and "outbound" means "traveling toward the user agent"
```

> 그냥 이것저것..

### Structure

```text
The HTTP protocol is a request/response protocol. A client sends a
request to the server in the form of a request method, URI, and
protocol version, followed by a MIME-like message containing request
modifiers, client information, and possible body content over a
connection with a server.

The server responds with a status line, including the message's protocol
version and a success or error code, by a MIME-like message containing
server information, entity metainformation, and possible entity-body content
```

> Client가 request method, URI, protocol version, MIME-like message (eg. text/plain, text/html)같은 것을 서버에게 요청.\
> Server는 message protocol version과 message content 그리고 response code를 client에 전송함

### Simplest case

```text
In the simplest case, this may be accomplished via a single connection (v)
between the user agent (UA) and the origin server (O).

      request chain ------------------------>
    UA -------------------v------------------- O
      <----------------------- response chain
```

> Single User Agent and server

### With intermediary

A more complicated situation occurs when one or more intermediaries
are present in the request/response chain. There are three common
forms of intermediary: proxy, gateway, and tunnel.

      request chain -------------------------------------->
    UA -----v----- A -----v----- B -----v----- C -----v----- O
      <------------------------------------- response chain

> Proxy, gateway같은게 User Agent랑 server 사이에 추가

### With a cache

Any party to the communication which is not acting as a tunnel may
employ an internal cache for handling requests. The effect of a cache
is that the request/response chain is shortened if one of the
participants along the chain has a cached response applicable to that
request.

      request chain ---------->
    UA -----v----- A -----v----- B - - - - - - C - - - - - - O
      <--------- response chain

> 중간 단계(tunnel) 에서 cache를 하게 되면 최종 server까지 가지 않고. 동일한 request에 대한 동일한 response를 보낼 수 있음

### Http over TCP/IP

HTTP communication usually takes place over TCP/IP connections. The
default port is TCP 80 [19], but other ports can be used. This does
not preclude HTTP from being implemented on top of any other protocol
on the Internet, or on other networks.

> HTTP는 보통 TCP/IP 위에서 돌아감. 계층이 달라서 그럼. But 다른 프로토콜 위에서도 돌아갈 수 있음

### Keep-Alive

In HTTP/1.0, each connection is established by the client prior to
the request and closed by the server after sending the response.
However, some implementations implement the explicitly negotiated.

In HTTP/1.0, most implementations used a new connection for each
request/response exchange. In HTTP/1.1, a connection may be used for
one or more request/response exchanges, although connections may be
closed for a variety of reasons (see section 8.1).

> 보통은 response받고 그냥 끊어버리는데 port를 열어둔 상태를 일정 기간 유지하자!\
> Http Persistent Connections임.

## Https

### Background

```text
HTTP [RFC2616] was originally used in the clear on the Internet.
However, increased use of HTTP for sensitive applications has
required security measures. SSL, and its successor TLS [RFC2246] were
designed to provide channel-oriented security. This document
describes how to use HTTP over TLS.
```

> HTTP 그냥 쓰면 보안상 문제가 있을 수 있어서 등장

### Structure

```text
Conceptually, HTTP/TLS is very simple. Simply use HTTP over TLS
precisely as you would use HTTP over TCP
```

> TLS 위에서의 HTTP임 그냥

```text
When HTTP/TLS is being run over a TCP/IP connection, the default port
is 443. This does not preclude HTTP/TLS from being run over another
transport.
```

> Default port : 443

## Http 2.0

### Background

The Hypertext Transfer Protocol (HTTP) is a wildly successful
protocol.  However, the way HTTP/1.1 uses the underlying transport
on connection management which has several characteristics
that have a negative overall effect on application performance today.

In particular, HTTP/1.0 allowed only one request to be outstanding at
a time on a given TCP connection.

Furthermore, HTTP header fields are often repetitive and verbose,
causing unnecessary network traffic as well as causing the initial
TCP [TCP] congestion window to quickly fill

HTTP/2 addresses these issues by defining an optimized mapping of
HTTP's semantics to an underlying connection.  Specifically, it
allows interleaving of request and response messages on the same
connection and uses an efficient coding for HTTP header fields

> Http/1.0은 동시에 하나의 요청만 처리 가능, header도 많아서 트레픽이 증가됨. 이를 해결하자!

### Structure

HTTP/2 provides an optimized transport for HTTP semantics.  HTTP/2
supports all of the core features of HTTP/1.1 but aims to be more
efficient in several ways.

> Http/1.1에 대한 하위호환성을 유지하면서 보다 효율적으로 함.

### Frame

+-----------------------------------------------+
|                 Length (24)                   |
+---------------+---------------+---------------+
|   Type (8)    |   Flags (8)   |
+-+-------------+---------------+-------------------------------+
|R|                 Stream Identifier (31)                      |
+=+=============================================================+
|                   Frame Payload (0...)                      ...
+---------------------------------------------------------------+

The basic protocol unit in HTTP/2 is a frame (Section 4.1).  Each
frame type serves a different purpose.  For example, HEADERS and DATA
frames form the basis of HTTP requests and responses (Section 8.1);
other frame types like SETTINGS, WINDOW_UPDATE, and PUSH_PROMISE are
used in support of other HTTP/2 features.

> basic protocol unit은 frame임.

### Stream

A "stream" is an independent, bidirectional sequence of frames
exchanged between the client and server within an HTTP/2 connection.
Streams have several important characteristics:

- A single HTTP/2 connection can contain multiple concurrently open
  streams, with either endpoint interleaving frames from multiple
  streams.

- Streams can be established and used unilaterally or shared by
  either the client or server.

- Streams can be closed by either endpoint.

- The order in which frames are sent on a stream is significant.
  Recipients process frames in the order they are received.  In
  particular, the order of HEADERS and DATA frames is semantically
  significant.

- Streams are identified by an integer.  Stream identifiers are
  assigned to streams by the endpoint initiating the stream.

> frame들의 independent한 sequence가 stream임.\
> single connection에서 multiple stream을 사용함으로써 multiplexing을 함

Flow control and prioritization ensure that it is possible to
efficiently use multiplexed streams.  Flow control (Section 5.2)
helps to ensure that only data that can be used by a receiver is
transmitted.  Prioritization (Section 5.3) ensures that limited
resources can be directed to the most important streams first.

### Server Push

HTTP/2 adds a new interaction mode whereby a server can push
responses to a client (Section 8.2).  Server push allows a server to
speculatively send data to a client that the server anticipates the
client will need, trading off some network usage against a potential
latency gain.  The server does this by synthesizing a request, which
it sends as a PUSH_PROMISE frame.  The server is then able to send a
response to the synthetic request on a separate stream.

> network usage를 포기하면서 latency를 얻음. 그래서 server가 client에 push 할 수 있음\
> (보통은 client 가 server에 request해야만 server가 response함)

## Http 3.0

## References

- [HTTP Specifications and Drafts](https://www.w3.org/Protocols/Specs.html)
- HTTP/1.0
  - [HTTP/1.0 (rfc Informational, 1996)](https://www.w3.org/Protocols/rfc1945/rfc1945)
- HTTP/1.1
  - [HTTP/1.1 (rfc Proposed Standard, 1997)](https://tools.ietf.org/html/rfc2068)
  - [HTTP/1.1 Enhancement (rfc Draft Standard 1999)](https://tools.ietf.org/html/rfc2616)
  - [HTTP/1.1 Message Syntax and Routing (rfc Proposed Standard, 2014)](https://tools.ietf.org/html/rfc7230#page-79)
- HTTPS
  - [HTTPS (rfc Informational, 2000)](https://tools.ietf.org/html/rfc2818)
- HTTP/2.0
  - [HTTP/2.0 (rfc Proposed Standard, 2015)](https://tools.ietf.org/html/rfc7540)
- HTTP/3.0