# Heraj

Java client framework for aergo chain

https://github.com/aergoio/heraj

- [Heraj](#heraj)
  - [Techs](#techs)
    - [grpc](#grpc)
    - [JDK 7](#jdk-7)
    - [gradle](#gradle)
  - [Architecture](#architecture)
  - [What you did](#what-you-did)
  - [What you got?](#what-you-got)
  - [Etc](#etc)
    - [왜 framework임?](#%ec%99%9c-framework%ec%9e%84)
    - [어려웠던 점](#%ec%96%b4%eb%a0%a4%ec%9b%a0%eb%8d%98-%ec%a0%90)

## Techs

### grpc

일차적으로는 aergo node가 사용해서 그런걸로 알긴 하는데... 빨라서 쓴 것으로 알음. Rest over http1.1보다 압축률도 뛰어나고 id가 부여된 frame기반으로 보내기 때문에 streaming도 됨. 더 빠름. 무엇보다 protobuf라는 프로토콜에 기반해서 server, client 코드를 생성해주는 것을 제공하기 때문에 프로토콜을 맞추기가 쉬웠음. 아 물론 protobuf말고 json도 될텐데 이렇게는 사용 안해봄.

단점은... boilerplate code가 조금 더 있는거 같음. protobuf 형식도 맞춰야 하고 코드를 생성하게 되서 어쩔 수 없이 생기는 코드들이 있음.

### JDK 7

원래 JDK 8을 썼었음. 3버전마다 나오는 LTS이기도 하고 Functional style interface를 제공하려고 했음. Scala의 Either에 기반해서 설계를 해보았음 `ResultOrError<Integer, Exception>` 이런 식이고 map같은 operation을 제공했었음.. But 금융권에서 아직 낮은 버전을 사용한다고 해서.. 1.6으로 갔다가 지원이 종료된다고 해서 1.7로 다시 갔음. lambda로 짜놓은거를 anonymous class로 바꾸느냐 노가다를 했음.

### gradle

maven보다 더 빨라서 씀

## Architecture

High level

Wallet, Smart Contract

Low level

Transport
Protobuf Common
Utils
Annotation

## What you did

Entire work after initial boilerplate

## What you got?

- 사용자는 내가 원하는대로 움직이지 않는다
- Design java api (다 숨겨라 어떤걸 쓸줄 모른다, 변경을 최소화하라)
- Important of dsl (domain에 맞는 domain specific language를 제공해야 함)
- Designed functional structure (행위에 대한 wrapper을 제공, retry나 timeout같은거). 이제 보니 AOP같음.

## Etc

### 왜 framework임?

보통 내가 가서 실행되면 framework이고 내가 실행시키면 라이브러리라고 한다. framework는 통제에 대한 흐름을 가지고 있다. 그래서 보통 main method를 누가 실행하느냐에 따라 구분하는데 이게 다라고 생각하지 않는다. 풍부한 DSL을 가지고 있고 비즈니스 로직이 어떠한 것 위에서 돌아가는 형식이면 framework라고 생각한다. Netty도 framework라고 불리고 있지 않은가? Context를 ThreadLocal에 저장하면서 이것저것 제공할 수 있는 틀을 만들어 놓았음.

### 어려웠던 점

Crypto부분 sign하는 부분이 어려웠음. 처음에는 라이브러리에 구현체가 있는줄 모르고 표준 보고 구현했었는데 이 부분이 제일 어려웠음. golang에 서버 코드 보고 하나 하나 따라가면서 했었음.. 결국 라이브러리를 쓰는 것을 바꾸었지만 제일 어려웠던 기억임.
