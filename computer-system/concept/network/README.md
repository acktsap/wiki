# Computer Network

- [Computer Network](#computer-network)
  - [What is Computer Network](#what-is-computer-network)
  - [OSI 7 Layer vs TCP/IP Layer](#osi-7-layer-vs-tcpip-layer)
  - [Application Layer](#application-layer)
    - [GET vs POST](#get-vs-post)
    - [HTTPS](#https)
    - [Domain](#domain)
    - [Load Balancing](#load-balancing)
    - [Keep Alive on HTTP](#keep-alive-on-http)
  - [Transport Layer](#transport-layer)
    - [TCP vs UDP](#tcp-vs-udp)
    - [TCP 3 way handshaking : on connection](#tcp-3-way-handshaking--on-connection)
    - [TCP 4 way handshaking : on disconnection](#tcp-4-way-handshaking--on-disconnection)
    - [Keep Alive on TCP](#keep-alive-on-tcp)
  - [Internet Layer](#internet-layer)
    - [Router](#router)
    - [Gateway](#gateway)
  - [Network Interface Layer](#network-interface-layer)
    - [Switch](#switch)
  - [Web Flow](#web-flow)
  - [References](#references)

---

## What is Computer Network

서로 다른 노드간 서로 자원을 공유할 수 있게 해주는 디지털 망

## OSI 7 Layer vs TCP/IP Layer

![osi-7-layer](./img/osi-7-layer.jpeg)

OSI 7 Layer (공식 표준)

- Application Layer : 사용자에게 가장 가까운 계층. HTTP, FTP, SMTP 등이 있음
- Presentational Layer : 데이터 표현이 상이한 응용 프로세스의 독립성을 제공하고 암호화함. JPEG, MPEG등이 있음
- Session Layer : 어플리케이션간 세션을 관리하고 구축.
- Transport Layer : 실제 데이터 전송. 오류 검출 및 복구와 흐름 제어를 제공. TCP, UDP등이 있음
- Network Layer : 라우팅을 통해 Packet단위로 데이터를 전송. IP등이 있음.
- DataLink Layer : 스위치 등의 장비를 통해 Frame단위로 데이터를 전송. MAC address를 사용
- Physical Layer : 랜선

TCP/IP : de-facto standard (사실상 표준)

- Application Layer : Application + Presentational + Session Layer of OSI 7 layer
- Transport Layer : Transport Layer of OSI 7 layer
- Internet Layer : Network Layer of OSI 7 layer
- Network Interface Layer : DataLink + Physical Layer of OSI 7 layer

[위로](#computer-network)

## Application Layer

### GET vs POST

- GET
  - 보통 정보를 조회할 때 사용
  - 데이터를 uri의 parameter로 전송
  - 같은 요청에 대해서 cache를 할 수 있음
- POST
  - 보통 서버의 값이나 상태를 변경할 때 사용
  - Request Body에 데이터를 담아서 전송
  - 같은 요청에 대해서 cache를 할 수 없음

### HTTPS

![ssl-process](./img/ssl-process.jpg)

- HTTP통신할 때 Transfer layer에 TLS(Transport Layer Security)를 적용한 것로 덮어쓴 것
- 동작 과정
  1. 클라이언트가 서버에 접속
  2. 서버가 인증서를 전송 클라이언트에 전송. 인증서는 서버의 공개키를 인증기관이 전자서명으로 인증한 것임
  3. 클라이언트는 받은 서버 인증서를 검증하여 **공개키**를 추출. 인증서 검증은 인증서 체인으로 신뢰할 수 있는 인증서의 리스트를 브라우저가 이미 가지고 있음.
  4. 클라이언트가 대칭키로 사용할 임의의 키를 서버의 **공개키**로 암호화하여 서버에 전송
  5. 서버에서는 자신의 **개인키**로 임의의 키를 복호화하여 대칭키 암호방식으로 암호화 해서 클라이언트와 통신
- 장점 : Plain HTTP에 비해서 보안성 높음
- 단점 : 암호화/복호화 하는 비용이 들음

### Domain

- 숫자로만 쓰인 ip는 외우기 어려움. 여기 이름을 부여하자!
- 이름을 부여 하면 이걸 ip로 변환시켜줘야 하는 작업이 필요. 이것을 하는 것이 DNS(Domain Name System) 서버

### Load Balancing

- 서버의 부하를 분산시키는 매커니즘. 외부에서는 한대의 IP로 요청을 받고 내부적으로 다른 서버에 요청하는 식임
- 장점 : 서버의 처리 속도가 올라가고 분산되어 있기 때문에 가용성이 올라감
- 단점 : 부하를 나누는 메커니즘이 필요. 부하른 나누는 방식
  - Round Robin : 각 서버에 돌아가면서 연결
  - Response Time : 응답시간이 빠른 서버에 conneciton을 우선 분배
  - Least Connection : 가장 적은 collection을 가지고 있는 서버에 연결

### Keep Alive on HTTP

- HTTP 는 비연결형 통신이기에 처리가 끝나면 연결을 해제함. 따라서 재요청시 TCP의 3-way handshaking을 다시 해야함
- 이를 해결하기 위해 커넥션을 열어두고 일정 timeout 이내에 요청이 들어오면 열려있는 커넥션을 재사용
- 장점 : 요청할 때 마다 커넥션을 새로 만들 필요가 없음
- 단점 : 커넥션이 너무 많으면 서버의 가용성이 떨어질 수 있음

[위로](#computer-network)

## Transport Layer

### TCP vs UDP

- TCP (Transmission Control Protocol)
  - 연결지향형
  - 데이터 전송의 신뢰성을 보장 but 상대적으로 느림
- UDP (User Datagram Protocol)
  - 비연결형
  - 데이터 전송의 신뢰성을 no 보장 but 상대적으로 빠름

### TCP 3 way handshaking : on connection

![3-way-handshaking](./img/3-way-handshaking.png)

- 클라이언트에서 서버로 SYN(M)보냄
- SYN(M)을 받은 서버는 클라이언트에 ACK(M + 1)과 SYN(N)을 보냄
- ACK(M + 1), SYN(N)을 받은 클라이언트는 **ESTABLISHED** 상태로 전환하고 ACK(N + 1)을 보냄
- ACK(N + 1)을 받은 서버는 **ESTABLISHED** 상태로 전환

### TCP 4 way handshaking : on disconnection

![4-way-handshaking](./img/4-way-handshaking.png)

A: 끊을게. B: 어 잠깐만 마저 하고, B: 어 다 했어 끊어. A : ok bye~

- 클라이언트가 FIN을 서버에 보냄
- FIN으 받은 서버가 클라이언트에 ACK를 보냄
- 서버가 보내던 데이터를 다 보내면 클라이언트에 FIN을 보냄
- FIN을 받은 클라이언트는 **TIME_WAIT** 상태로 변경되고 서버에 ACK를 보냄
- ACK를 받은 서버는 **CLOSED** 로 변경

### Keep Alive on TCP

- payload가 없는 패킷을 주기적으로 보내는 것
- 양쪽중 한쪽이 죽었을 때 다른 한 쪽을 정리하기 위해 사용

[위로](#computer-network)

## Internet Layer

### Router

IP주소를 이용해 목적지로 패킷을 전송

### Gateway

로컬망 라우터와 외부 망 라우터간의 통로

[위로](#computer-network)

## Network Interface Layer

### Switch

전송거리를 연장시켜주는 장치. MAC 주소를 통해 데이터를 전송.

[위로](#computer-network)

## Web Flow

우리가 Chrome 을 실행시켜 주소창에 특정 URL 값을 입력시키면 어떤 일이 일어나는가?

1. URL을 해석 (scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]). scheme + authority + path 등등
2. URI가 domain으로 되어 있는 경우 Domain Name Server를 조회를 해서 IP주소를 알아냄
3. IP주소를 기반으로 Socket을 열음. 이때 TCP 3-hand-shaking이 일어남
4. HTTP Protocol로 Request, Response

[위로](#computer-network)

## References

Common

https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/Network

https://hyeonu1258.github.io/2018/03/10/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC%ED%86%B5%EC%8B%A0%20%EB%A9%B4%EC%A0%91/

SSL

https://swalloow.github.io/https-ssl

3 way handshaking

http://asfirstalways.tistory.com/356

Web flow

https://owlgwang.tistory.com/1