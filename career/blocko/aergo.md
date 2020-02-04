# Aergo

엔터프라이즈에서 바로 사용할 수 있는 블록체인 솔루션

https://github.com/aergoio/aergo

- [Aergo](#aergo)
  - [Concept](#concept)
  - [Architecture](#architecture)
    - [비가역성을 어떻게 검증함?](#%eb%b9%84%ea%b0%80%ec%97%ad%ec%84%b1%ec%9d%84-%ec%96%b4%eb%96%bb%ea%b2%8c-%ea%b2%80%ec%a6%9d%ed%95%a8)
    - [강점이 뭐임?](#%ea%b0%95%ec%a0%90%ec%9d%b4-%eb%ad%90%ec%9e%84)
  - [References](#references)

## Concept

- ethereum + eos
- 기본적으로 이더 기반, consensus만 eos에서 (dpos)
- nonce기반 (no UTXO, no double spending error)

## Architecture

![aergo-architecture](./img/aergo-architecture.png)

> tx가 날라가면 우선 mempool에 들어감\
> 이건 블록체인마다 다른데 aergo같은 경우 dpos라서 다음에 블록을 생성할 노드가 있음\
> 얘가 블록을 생성하겠다고 하고 p2p로 mempool에서 tx들 받아옴\
> tx를 실행하면서 블록 생성\
> Chain DB에 블록체인들 저장, State DB에 Account state 저장\
> p2p로 다른 블록에 뿌리면 다른 노드들의 상태 변경\

### 비가역성을 어떻게 검증함?

> Last Irreversible Block라는게 있는데 이거 이후면 reorg안일어남

### 강점이 뭐임?

> Enterprise 시장에서 바로 사용할 수 있는 솔루션... 이라고는 합니다. 실제로 tps는 6500정도 나오고 쓸만함

## References

https://github.com/aergoio/aergo