# Http 1.1

## Background

```text
HTTP/1.0 does not sufficiently take into consideration the effects
of hierarchical proxies, caching, the need for persistent connections,
or virtual hosts
```

## Terms

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

## Structure

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

Simplest case

```text
In the simplest case, this may be accomplished via a single connection (v)
between the user agent (UA) and the origin server (O).

      request chain ------------------------>
    UA -------------------v------------------- O
      <----------------------- response chain
```

With intermediary

```text
A more complicated situation occurs when one or more intermediaries
are present in the request/response chain. There are three common
forms of intermediary: proxy, gateway, and tunnel.

      request chain -------------------------------------->
    UA -----v----- A -----v----- B -----v----- C -----v----- O
      <------------------------------------- response chain
```

With a cache

```text
Any party to the communication which is not acting as a tunnel may
employ an internal cache for handling requests. The effect of a cache
is that the request/response chain is shortened if one of the
participants along the chain has a cached response applicable to that
request.

      request chain ---------->
    UA -----v----- A -----v----- B - - - - - - C - - - - - - O
      <--------- response chain
```

Http over TCP/IP

```text
HTTP communication usually takes place over TCP/IP connections. The
default port is TCP 80 [19], but other ports can be used. This does
not preclude HTTP from being implemented on top of any other protocol
on the Internet, or on other networks.
```

Persistent Connections

```text
In HTTP/1.0, most implementations used a new connection for each
request/response exchange. In HTTP/1.1, a connection may be used for
one or more request/response exchanges, although connections may be
closed for a variety of reasons (see section 8.1).
```

## Keep-Alive

```text
In HTTP/1.0, each connection is established by the client prior to
the request and closed by the server after sending the response.
However, some implementations implement the explicitly negotiated.
```

> 보통은 response받고 그냥 끊어버리는데 port를 열어둔 상태를 일정 기간 유지하자!
> Http Persistent Connections임

## Reference

[Origin (1997)](https://tools.ietf.org/html/rfc2068)

[Enhancement (1999)](https://tools.ietf.org/html/rfc2616)

[(HTTP/1.1): Message Syntax and Routing](https://tools.ietf.org/html/rfc7230#page-79)
