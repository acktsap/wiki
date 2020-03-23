# GRPC (Google Remote Procedure Calls)

- [GRPC (Google Remote Procedure Calls)](#grpc-google-remote-procedure-calls)
  - [What is it](#what-is-it)
  - [GRPC vs Binary blob over HTTP/2.0](#grpc-vs-binary-blob-over-http20)
  - [GRPC vs HTTP API](#grpc-vs-http-api)
  - [References](#references)

## What is it

구글이 Stubby라는 RPC 구조를 만들어서 내부 microservice에 쓰고 있었음. 이것을 공개해보자! 해서 나온게 GRPC.

HTTP/2.0기반으로 높은 메시지 압축률과 성능을 보여주고 Single HTTP 연결을 통해 multiplexing을할 수 있음. 어떤 식으로 메시지를 보낼건지 미리 정해두고 다양한 코드로 규약에 대한 코드를 생성할 수 있기 때문에 다양한 언어에 대한 확장성이 좋음. 메시지에 대한 규약은 보통 protobuf를 사용. But 브라우저에서 지원하지 않고 메시지의 형태가 압축되서 전송되기 때문에 보통 사람이 읽기 힘들다는 단점이 있음. 그래서 간단한 것을 쓸 때는 간단한 Rest API를 사용하는 것이 더 나음.

## GRPC vs Binary blob over HTTP/2.0

HTTP/2.0기반으로 이루어져 있으나 HTTP/2.0이 제공하지 않는 다양한 기능을 지원하는 것으로 알음. 또 Protobuf를 사용하면 높은 메시지 압축률을 보여줌.

## GRPC vs HTTP API

GRPC는 protocol이 필요하고 full-duplex가 가능하지만 HTTP API보다 복잡하다는 단점이 있음. HTTP는 GRPC는 단방향만 지원하고 상대적으로 더 느리지만 보통 JSON으로 해서 사람이 읽기 편하고 상대적으로 간단함.

## References

https://grpc.io/

https://docs.microsoft.com/ko-kr/aspnet/core/grpc/comparison?view=aspnetcore-3.1
