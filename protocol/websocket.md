# WebSocket

- [WebSocket](#websocket)
  - [Background](#background)
  - [Protocol Structure](#protocol-structure)
    - [Closing handshake](#closing-handshake)
    - [Relationship to TCP and HTTP](#relationship-to-tcp-and-http)
  - [References](#references)

## Background

```text
Historically, creating web applications that need bidirectional
communication between a client and a server has required an abuse of HTTP to poll the
server for updates while sending upstream notifications as distinct
HTTP calls [RFC6202].
```

This results in a variety of problems:

- The server is forced to use a number of different underlying TCP\
  connections for each client: one for sending information to the\
  client and a new one for each incoming message.
- The wire protocol has a high overhead, with each client-to-server\
  message having an HTTP header.
- The client-side script is forced to maintain a mapping from the\
  outgoing connections to the incoming connection to track replies

A simpler solution would be to use a single TCP connection for traffic in both directions

> HTTP1.1은에서 header도 매번 날라가고 incoming/outgoing message에 대해 각각 관리해야 하는 등 (2개여야 함) 비효율적임. 심지어 server쪽에서 client에 push도 할 수 없음.
> 이를 해결하기 위해 Single TCP connection으로 bidirectional connection을 해보자!

```text
The WebSocket Protocol is designed to supersede existing
bidirectional communication technologies that use HTTP as a transport
layer to benefit from existing infrastructure (proxies, filtering,
authentication).  Such technologies were implemented as trade-offs
between efficiency and reliability because HTTP was not initially
meant to be used for bidirectional communication (see [RFC6202] for
further discussion).

As such, it is designed to work
over HTTP ports 80 and 443 as well as to support HTTP proxies and
intermediaries, even if this implies some complexity specific to the
current environment

However, the design does not limit WebSocket to
HTTP, and future implementations could use a simpler handshake over a
dedicated port without reinventing the entire protocol
```

> websocket은 기존의 http기반의 인프라를 활용하기 위해 기존의 http기반에서 bidirectional communication을 제공하려고 만들어짐. 그래서 포트도 http와 같이 80, 443을 씀.
> But 그렇다고 http에 제한되는거는 아님. 추후에는 다른걸 쓸 수도 있음.

## Protocol Structure

```text
The protocol has two parts: a handshake and the data transfer.

The handshake from the client looks as follows:

    GET /chat HTTP/1.1
    Host: server.example.com
    Upgrade: websocket
    Connection: Upgrade
    Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
    Origin: http://example.com
    Sec-WebSocket-Protocol: chat, superchat
    Sec-WebSocket-Version: 13

The handshake from the server looks as follows:

    HTTP/1.1 101 Switching Protocols
    Upgrade: websocket
    Connection: Upgrade
    Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
    Sec-WebSocket-Protocol: chat

After a successful handshake, clients and servers transfer data back
and forth in conceptual units referred to in this specification as
"messages".  On the wire, a message is composed of one or more frames
```

> Handshake와 그 이후의 Frame단위의 data transfer로 이루어져 있음.

### Closing handshake 

```text
Either peer can send a control frame with data containing a specified
control sequence to begin the closing handshake (detailed in
Section 5.5.1).  Upon receiving such a frame, the other peer sends a
Close frame in response, if it hasn't already sent one.  Upon
receiving _that_ control frame, the first peer then closes the
connection, safe in the knowledge that no further data is
forthcoming.

After sending a control frame indicating the connection should be
closed, a peer does not send any further data; after receiving a
control frame indicating the connection should be closed, a peer
discards any further data received.

The closing handshake is intended to complement the TCP closing
handshake (FIN/ACK), on the basis that the TCP closing handshake is
not always reliable end-to-end, especially in the presence of
intercepting proxies and other intermediaries.
```

> FIN -> ACK의 구조로 단순하다. FIN을 보낸 녀석은 데이터를 더이상 보내지 않음. FIN을 받아서 ACK를 보낼 녀석은 데이터를 더 받아도 무시함.
> TCP의 FIN/ACK구조가 Proxy 등의 intermediary로 인해서 end-to-end로 안되는 경우가 있어서 이를 보완하기도 한다.

### Relationship to TCP and HTTP

```text
The WebSocket Protocol is an independent TCP-based protocol.  Its
only relationship to HTTP is that its handshake is interpreted by
HTTP servers as an Upgrade request.

By default, the WebSocket Protocol uses port 80 for regular WebSocket
connections and port 443 for WebSocket connections tunneled over
Transport Layer Security (TLS)
```

> WebSocket은 TCP 기반의 프로토콜임. HTTP와의 관계는 WebSocket의 handshake가 HTTP의 upgrade request로 이루어진다는 것 뿐임. 포트는 HTTP와 같게 80(Plaintext), 443(TLS)를 사용함.

## References

2011

https://tools.ietf.org/html/rfc6455

keep alive vs websocket

https://stackoverflow.com/questions/7620620/whats-the-behavioral-difference-between-http-stay-alive-and-websockets
