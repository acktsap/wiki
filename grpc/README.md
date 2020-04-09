# GRPC

- [GRPC](#grpc)
  - [What is it](#what-is-it)
  - [*.proto를 작성할 때 고려해야 할 점](#proto%eb%a5%bc-%ec%9e%91%ec%84%b1%ed%95%a0-%eb%95%8c-%ea%b3%a0%eb%a0%a4%ed%95%b4%ec%95%bc-%ed%95%a0-%ec%a0%90)
  - [GRPC vs Binary blob over HTTP/2.0](#grpc-vs-binary-blob-over-http20)
  - [GRPC vs Rest API over HTTP/1.1](#grpc-vs-rest-api-over-http11)
  - [References](#references)

## What is it

- Google Remote Procedure Calls의 약자로 구글이 내부 microservice에 Stubby라는 불리던 RPC를 공개용으로 만든 것
- 장점
  - HTTP/2.0기반으로 Multiplexing, 양방향 통신이 가능하며 header, frame에 대한 높은 압축
  - protobuf로 rpc 규약을 정해두면 다양한 언어로 rpc를 실행하는 코드를 생성할 수 있어서 확장성이 좋음
- 단점
  - 규약을 정해야 해서 Rest API같은 것에 비해 상대적으로 복잡
  - Message가 binary의 형태로 전송되어서 사람이 읽기 힘듬

## *.proto를 작성할 때 고려해야 할 점

- 하위호환을 지키기 위해서는
- Update
  - field number를 바꾸지 말라
  - 쓸모없는 field를 reserved로 변경한 후 새로운 field를 추가하라
- Remove
  - 쓸모없는 field를 reserved로 변경하라

## GRPC vs Binary blob over HTTP/2.0

- GRPC는 HTTP/2.0이 제공하지 않는 다양한 기능을 지원
- eg. Protobuf를 사용해서 높은 메시지 압축률을 보여줌

## GRPC vs Rest API over HTTP/1.1

- GRPC
  - 장점 : HTTP/2.0기반으로 속도가 빠르고 양방향 통신이 가능
  - 단점 : 규약을 정의해야 하는 등 상대적으로 복잡하고 binary의 형태로 전송해서 사람이 읽기 힘듬
- Rest API
  - 장점 : 상대적으로 간단하고 text로 전송해서 사람이 읽기 쉬움
  - 단점 : 느리고 단방향 통신만 지원

## References

https://grpc.io/

https://docs.microsoft.com/ko-kr/aspnet/core/grpc/comparison?view=aspnetcore-3.1
