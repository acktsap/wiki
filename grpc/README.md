# GRPC

- [GRPC](#grpc)
  - [What is it](#what-is-it)
  - [GRPC vs Binary blob over HTTP/2.0](#grpc-vs-binary-blob-over-http20)
  - [GRPC vs REST API over HTTP/1.1](#grpc-vs-rest-api-over-http11)
  - [References](#references)

## What is it

Google Remote Procedure Calls의 약자로 구글이 내부 microservice에 Stubby라는 RPC를 쓰고 있었음. 이것을 공개해보자! 해서 나온게 GRPC.

HTTP/2.0기반으로 Server Push, Multiplexing, frame이나 header에 대한 높은 압축률을 자랑함. GRPC를 쓰려면 RPC에 대한 규약을 정의해둬야 하는데 보통 쓰는게 protobuf임. 미리 정해둔 규약에 기반해서 RPC를 위한 코드를 다양한 언어로 생성할 수 있기 때문에 확장성이 좋음.

하지만 단점이 있는데 규약을 정해야 해서 REST API등에 비해 상대적으로 복잡함. 그리고 메시지의 형태가 binary의 형태로 전송되기 때문에 사람이 읽기 힘들고 브라우저에서 지원하지 않음.

## GRPC vs Binary blob over HTTP/2.0

GRPC가 HTTP/2.0기반으로 이루어져 있으나 HTTP/2.0이 제공하지 않는 다양한 기능을 지원하는 것으로 알음. 또 Protobuf를 사용하면 높은 메시지 압축률을 보여줌.

## GRPC vs REST API over HTTP/1.1

GRPC는 HTTP/2.0, protobuf를 사용해서 높은 압축률을 자랑하고 양방향 통신이 가능하지만 규약이 필요하고 REST API보다 복잡하고 binary의 형태로 전송해서 사람이 읽기 힘들다는 단점이 있음. REST API는 text의 형태로 전송해서 해서 사람이 읽기 편하고 상대적으로 간단하지만 단방향만 지원하고 GRPC에 비해 느리다.

## References

https://grpc.io/

https://docs.microsoft.com/ko-kr/aspnet/core/grpc/comparison?view=aspnetcore-3.1
