# Network Layer

- [Router](#router)
- [Gateway](#gateway)
- [IP](#ip)
  - [서브넷 마스크](#서브넷-마스크)
  - [Port](#port)
  - [IPv4 vs IPv6](#ipv4-vs-ipv6)
- [ICMP](#icmp)
- [References](#references)


## Router

IP주소를 이용해 목적지로 패킷을 전송.

## Gateway

로컬망 라우터와 외부 망 라우터간의 통로.

## IP

- Internet Protocol.
- 보내는 주소 지정과 데이터 전송을 담당.
- Network 주소와 host 주소로 구성.

### 서브넷 마스크

- Network 주소와 Host 주소를 구분

https://github.com/jhyuk316/study/blob/main/Backend%20Roadmap/02%20General%20Knowledge/02.10%20Basic%20Networking%20Concepts.md#12-ip%EC%A3%BC%EC%86%8Cip-address

### Port

https://hanamon.kr/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EA%B8%B0%EB%B3%B8-ip-%EC%A3%BC%EC%86%8C%EC%99%80-%ED%8F%AC%ED%8A%B8-port/

### IPv4 vs IPv6

IPv4

- IP version 4
- 32 bit
- `0.0.0.0`을 미지정 주소로, `127.0.0.1`을 loopback으로

IPv6

- IP version 6
- 128 bit
- `::`을 미지정 주소로, `::1`을 loopback으로

## ICMP

- Internet Control Message Protocol
- packet header에 있는 IP address에 packet를 보냄

## References

- Common
  - [Computer-Networks-for-Interviews, notescs](https://github.com/notescs/notes/tree/main/Computer-Networks-for-Interviews)
- IP
  - [Internet Protocol, wiki](https://en.wikipedia.org/wiki/Internet_Protocol)
