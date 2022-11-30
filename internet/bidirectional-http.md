# Known Issues and Best Practices for Bidirectional HTTP

- [Known Issues and Best Practices for Bidirectional HTTP](#known-issues-and-best-practices-for-bidirectional-http)
  - [History](#history)
  - [HTTP Long Pooling](#http-long-pooling)
  - [HTTP Streaming](#http-streaming)
  - [Server Push Technique](#server-push-technique)
  - [Best Practice for Bidirectional HTTP](#best-practice-for-bidirectional-http)
  - [References](#references)

## History

```text
In the standard HTTP model, a server cannot initiate a connection
with a client nor send an unrequested HTTP response to a client;
thus, the server cannot push asynchronous events to clients.
Therefore, in order to receive asynchronous events as soon as
possible, the client needs to poll the server periodically for new
content.  However, continual polling can consume significant
bandwidth by forcing a request/response round trip when no data is
available.  It can also be inefficient because it reduces the
responsiveness of the application since data is queued until the
server receives the next poll request from the client.
```

> Server가 Client으로부터 요청이 들어오지 않는 이상 connection을 맺는 방법이 없음.\
> 그래서 Client가 pooling을 해야 함... But 이것은 bandwidth의 낭비가 발생.\
> 이것을 해결하려고 시도한 몇가지 방법이 있음.

## HTTP Long Pooling

## HTTP Streaming

```text
The HTTP streaming mechanism keeps a request open indefinitely.  It
never terminates the request or closes the connection, even after the
server pushes data to the client.  This mechanism significantly
reduces the network latency because the client and the server do not
need to open and close the connection.
```

> connection을 계속 열어두고 데이터를 조각조각 계속 보내는 형식. connection을 다시 맺지 않아도 된다는 장점이 있음.

```text
The HTTP streaming mechanism is based on the capability of the server
to send several pieces of information in the same response, without
terminating the request or the connection.

An HTTP response content length can be defined using three options:

Content-Length header:  This indicates the size of the entity body in
  the message, in bytes.

Transfer-Encoding header:  The 'chunked' valued in this header
  indicates the message will break into chunks of known size if
  needed.

End of File (EOF):  This is actually the default approach for
  HTTP/1.0 where the connections are not persistent.  Clients do not
  need to know the size of the body they are reading; instead they
  expect to read the body until the server closes the connection.

> HTTP streaming에서는 content length에 대한 정보를 담기 위해 길이 정보를 Header에 포함하거나 EOF를 보내는 방식이 있음.

Network Intermediaries:  The HTTP protocol allows for intermediaries
  (proxies, transparent proxies, gateways, etc.) to be involved in
  the transmission of a response from the server to the client.
  There is no requirement for an intermediary to immediately forward
  a partial response, and it is legal for the intermediary to buffer
  the entire response before sending any data to the client (e.g.,
  caching transparent proxies).

Maximal Latency:  Theoretically, on a perfect network, an HTTP
  streaming protocol's average and maximal latency is one network
  transit.  However, in practice, the maximal latency is higher due
  to network and browser limitations.  The browser techniques used
  to terminate HTTP streaming connections are often associated with
  JavaScript and/or DOM (Document Object Model) elements that will
  grow in size for every message received.  Thus, in order to avoid
  unlimited growth of memory usage in the client, an HTTP streaming
  implementation occasionally needs to terminate the streaming
  response and send a request to initiate a new streaming response
  (which is essentially equivalent to a long poll).  Thus, the
  maximal latency is at least three network transits.  Also, because
  HTTP is carried over TCP/IP, packet loss and retransmission can
  occur; therefore maximal latency for any TCP/IP protocol will be
  more than three network transits (lost packet, next packet,
  negative ack, retransmit).

Client Buffering:  There is no requirement in existing HTTP
  specifications for a client library to make the data from a
  partial HTTP response available to the client application.  For
  example, if each response chunk contains a statement of
  JavaScript, there is no requirement in the browser to execute that
  JavaScript before the entire response is received.

Framing Techniques:  Using HTTP streaming, several application
  messages can be sent within a single HTTP response.  The
  separation of the response stream into application messages needs
  to be performed at the application level and not at the HTTP
  level.
```

> Network intermediaries가 있으면 streaming하는것들 막 cache할 수 있음.\
> Streaming하면 계속 쌓여서 가끔 재시작 해줘야 함. TCP/IP기반이라서 packet loss일 경우 retransmit도 해야 함. 이런 저런 이유로 제대로 하려면 Latency가 높아짐.\
> 또 Frame단위로 조각조각 받아오기 때문에 데이터를 다 받아야 처리할 수 있는 경우 Client단에서 Buffering이 생길 수 있음. 비슷한 이유로 조각 조각 오기 때문에 이것들을 조립하는 것을 Application 단에서 해야 함.

## Server Push Technique

## Best Practice for Bidirectional HTTP

## References

2011

https://tools.ietf.org/html/rfc6202