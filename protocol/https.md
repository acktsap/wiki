# Https

## Background

```text
HTTP [RFC2616] was originally used in the clear on the Internet.
However, increased use of HTTP for sensitive applications has
required security measures. SSL, and its successor TLS [RFC2246] were
designed to provide channel-oriented security. This document
describes how to use HTTP over TLS.
```

## Structure

```text
Conceptually, HTTP/TLS is very simple. Simply use HTTP over TLS
precisely as you would use HTTP over TCP
```

```text
When HTTP/TLS is being run over a TCP/IP connection, the default port
is 443. This does not preclude HTTP/TLS from being run over another
transport.
```

## References

[2000](https://tools.ietf.org/html/rfc2818)