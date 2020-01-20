# WebSocket

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

-> A simpler solution would be to use a single TCP connection for traffic in both directions

## Protocol Structure

## References

[2011](https://tools.ietf.org/html/rfc6455)
