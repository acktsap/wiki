# Http 2.0

## Background

```text
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
```

## Structure

TODO

https://tools.ietf.org/html/rfc7540#page-5

## References

[2015](https://tools.ietf.org/html/rfc7540)