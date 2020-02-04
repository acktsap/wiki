# Blockchain

- [Blockchain](#blockchain)
  - [Crypto](#crypto)
    - [Private Key, Public Key](#private-key-public-key)
    - [Deterministic Wallet](#deterministic-wallet)
  - [Block, Transaction](#block-transaction)
  - [Consensus](#consensus)
    - [Proof of Work](#proof-of-work)
    - [Proof of Staking](#proof-of-staking)
    - [Distributed Proof of Staking](#distributed-proof-of-staking)
    - [Bitcoin vs Ethereum](#bitcoin-vs-ethereum)

## Crypto

### Private Key, Public Key

### Deterministic Wallet

## Block, Transaction

## Consensus

### Proof of Work

### Proof of Staking

### Distributed Proof of Staking

### Bitcoin vs Ethereum

Bitcoin은 utxo(unspent transaction output)기반으로 이전 tx의 output이 다음 tx의 input으로 들어가고 state db에도 utxo가 들어감. 그래서 한번에 여러 tx를 날리는 경우 output을 두번 이상 사용하는 에러가 발생할 수 있음. 이것이 Double spending 문제.\
반면에 이더는 account기반으로 nonce라고 임의의 숫자 (라고 하고 그냥 증가하는 숫자)를 account의 tx별로 부여해서 double spending problem을 처리함.\
두개의 Chain State DB는 거의 같음 (block들의 chain)
