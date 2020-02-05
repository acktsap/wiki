# Grpc

gRPC : Google Remote Procedure Calls, pronounced by Jee-Arr-Pee-See

## History

Google has been using a single general-purpose RPC infrastructure called Stubby to connect\
the large number of microservices running within and across our data centers for over a decade

Stubby has powered all of Google’s microservices interconnect for over a decade and\
is the RPC backbone behind every Google service that you use today

2015, google decided to build the next version of Stubby in the open

## FAQs

### Why should i use it

- Low latency, highly scalable, distributed systems.
- Developing mobile clients which are communicating to a cloud server.
- Designing a new protocol that needs to be accurate, efficient and language independent.
- Layered design to enable extension eg. authentication, load balancing, logging and monitoring etc.

> 빠름

## Can I use gRPC with my favorite data format (JSON, Protobuf, Thrift, XML)

Yes. gRPC is designed to be extensible to support multiple content types

> Just a primary type is protobuf

## Why is gRPC better/worse than REST?

gRPC largely follows HTTP semantics over HTTP/2 but we explicitly allow for full-duplex streaming.

We diverge from typical REST conventions as we use static paths for performance reasons during call dispatch\
as parsing call parameters from paths, query parameters and payload body adds latency and complexity.

We have also formalized a set of errors that we believe are more directly applicable to API use cases than the HTTP status codes.

## References

https://grpc.io/
