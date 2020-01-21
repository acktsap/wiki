# Aergo

## Concept

- ethereum + eos
- 기본적으로 이더 기반, consensus만 eos에서 (dpos)
- nonce기반 (no UTXO, no double spend error)

## Structure

![aergo-structure](./img/aergo-structure.png)

- P2P : Mempool에 있는 tx를 받음
- Mempool : tx가 처음에 들어가면 여기 있음
- Block factory : (bp일 경우) 블록을 생성함
- Chain Service : Tx Sign 검증, Block 저장
- Chain DB : Blockchain을 저장
- State DB : Account State을 저장
