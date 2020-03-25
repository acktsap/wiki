# GRPC

- [GRPC](#grpc)
  - [What is it](#what-is-it)
  - [GRPC vs Binary blob over HTTP/2.0](#grpc-vs-binary-blob-over-http20)
  - [GRPC vs REST API over HTTP/1.1](#grpc-vs-rest-api-over-http11)
  - [References](#references)

## What is it

- Google Remote Procedure Calls의 약자로 구글이 내부 microservice에 Stubby라는 불리던 RPC를 공개용으로 만든 것
- 장점
  - HTTP/2.0기반으로 Multiplexing, 양방향 통신이 가능하며 header, frame에 대한 높은 압축
  - protobuf로 rpc 규약을 정해두면 다양한 언어로 rpc를 실행하는 코드를 생성할 수 있어서 확장성이 좋음
- 단점
  - 규약을 정해야 해서 Rest API같은 것에 비해 상대적으로 복잡
  - Message가 binary의 형태로 전송되어서 사람이 읽기 힘듬

## GRPC vs Binary blob over HTTP/2.0

- GRPC는 HTTP/2.0이 제공하지 않는 다양한 기능을 지원
- GRPC는 Protobuf를 사용해서 높은 메시지 압축률을 보여줌

## GRPC vs REST API over HTTP/1.1

GRPC는 HTTP/2.0, protobuf를 사용해서 높은 압축률을 자랑하고 양방향 통신이 가능하지만 규약이 필요하고 REST API보다 복잡하고 binary의 형태로 전송해서 사람이 읽기 힘들다는 단점이 있음. REST API는 text의 형태로 전송해서 해서 사람이 읽기 편하고 상대적으로 간단하지만 단방향만 지원하고 GRPC에 비해 느리다.

## References

https://grpc.io/

https://docs.microsoft.com/ko-kr/aspnet/core/grpc/comparison?view=aspnetcore-3.1
