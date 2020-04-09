# Aergo

- [Aergo](#aergo)
  - [Concept](#concept)
  - [When new tx comes](#when-new-tx-comes)
  - [Vs Fabric](#vs-fabric)
  - [References](#references)

## Concept

- 엔터프라이즈에서 바로 사용할 수 있는 블록체인 솔루션을 추구
- 이더 기반해서 만든 것으로 이더하고 구조가 비슷. Consensus만 EOS와 같은 DPOS
- 이더와 같이 nonce기반이고 blockchain에 대한 정보를 담고 있는 chain db와 account에 대한 정보가 담겨 있는 state db로 이루어져 있음

## When new tx comes

![aergo-architecture](./img/aergo-architecture.png)

- mempool에 들어감
- dpos에 따라 다음에 블록을 생성할 차례인 녀석이 블록을 생성하겠다고 하고 p2p로 mempool에서 tx들 받아옴
- tx를 실행하면서 블록 생성
- Chain DB에 블록체인들 저장, State DB에 Account state 저장 하고 p2p로 다른 블록에 뿌림
- 블록을 받은 다른 노드들은 자체의 상태 변경

비가역성은 Last Irreversible Block라는게 있는데 이거 이후면 reorg안일어남

## Vs Fabric

## References

https://github.com/aergoio/aergo
