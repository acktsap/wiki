# Overview

- [What is Computer Network](#what-is-computer-network)
- [OSI 7 Layer vs TCP/IP Layer](#osi-7-layer-vs-tcpip-layer)
- [What happens](#what-happens)
- [References](#references)

## What is Computer Network

서로 다른 노드간 서로 자원을 공유할 수 있게 해주는 디지털 망

## OSI 7 Layer vs TCP/IP Layer

![osi-7-layer](./img/overview-osi-7-layer.jpeg)

OSI 7 Layer (공식 표준)

- Application Layer : 사용자에게 가장 가까운 계층. HTTP, FTP, SMTP 등이 있음
- Presentational Layer : 데이터 표현이 상이한 응용 프로세스의 독립성을 제공하고 암호화함. JPEG, MPEG등이 있음
- Session Layer : 어플리케이션간 세션을 관리하고 구축.
- Transport Layer : 실제 데이터 전송. 오류 검출 및 복구와 흐름 제어를 제공. TCP, UDP등이 있음
- Network Layer : 라우팅을 통해 Packet단위로 데이터를 전송. IP등이 있음.
- DataLink Layer : 스위치 등의 장비를 통해 Frame단위로 데이터를 전송. MAC address를 사용
- Physical Layer : 랜선

TCP/IP : de-facto standard (사실상 표준)

- Application Layer : Application + Presentational + Session Layer
- Transport Layer : Transport Layer
- Internet Layer : Network Layer
- Network Interface Layer : DataLink + Physical Layer

## What happens

- URL을 해석 (scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]). scheme + authority + path 등등.
- URI가 domain으로 되어 있는 경우 Domain Name Server를 조회를 해서 IP주소를 알아냄.
- IP주소를 기반으로 Socket을 열음. 이때 TCP 3-hand-shaking이 일어남.
- HTTP Protocol로 Request, Response.

## References

- What happens
  - [웹 브라우저에 URL을 입력하면 어떤 일이 일어날까?, owlgwang](https://owlgwang.tistory.com/1)
  - [https://github.com/alex/what-happens-when](https://github.com/alex/what-happens-when) 