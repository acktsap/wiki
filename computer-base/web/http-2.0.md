# Http 2.0

- [Http 2.0](#http-20)
  - [Background](#background)
  - [Structure](#structure)
    - [Frame](#frame)
    - [Stream](#stream)
    - [Server Push](#server-push)
  - [References](#references)

## Background

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

## Structure

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

## References

[2015](https://tools.ietf.org/html/rfc7540)