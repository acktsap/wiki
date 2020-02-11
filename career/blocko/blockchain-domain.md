# Blockchain

- [Blockchain](#blockchain)
  - [Distributer Ledger](#distributer-ledger)
  - [Hirarchical Deterministic Wallet](#hirarchical-deterministic-wallet)
  - [Consensus](#consensus)
  - [Blockchain Solutions](#blockchain-solutions)
    - [Bitcoin vs Ethereum](#bitcoin-vs-ethereum)
    - [EOS](#eos)
    - [Hyperledger Fabric](#hyperledger-fabric)
  - [References](#references)

## Distributer Ledger

그냥 분산된 원장이고 이 원장의 변경 사항을 crypto기술로 연결해놓아서 위변조가 불가능한 것. 거래 내력을 block으로 만들어서 chain으로 연결해놓음 이 연결 과정에 crypto기술이 쓰이는 것.

한개의 tx가 발행하는 과정을 설명하면, 우선 Private key로 거래에 서명을 하고 이를 보냄. 여기서 개인키란 비대칭 키에서의 개인키를 말하는데 블록체인에서는 보통 타원곡선 (ECC)기반의 키가 쓰임. Private key로 한 서명은 public key로 검증을 할 수 있기 때문에 누가 했는지를 알 수 있음. 누가 어떤 거래를 했는지 정확하게 알고 이를 block에 포함시킴으로써 state db, 즉, 원장을 갱신함. 여기서 이제 분산 원장이라고 했으니까 새로 생성한 블록을 다른 원장에서도 받아야 함. 이것이 Consensus로 PoW, PoS, DPoS 등이 있음.

## Hirarchical Deterministic Wallet

특정 Seed로 생성된 private key를 의미함. Private key는 보통 큰 숫자값이라서 기억하기고 힘들고 저장도 어려움. But seed는 상대적 으로 쉽기 때문에 이것으로 wallet을 생성할 수 있음. 여기서 이 seed값도 대충 지으면 쉬우니까  Seed phrase라고 12~24단어로 구성된 문자들을 가지고 만듬. 이렇게 생성한 키를 가지고 자손 키를 만들고 할 수 있음. 삼성 KeyStore가 이걸로 되어 있는것으로 알음.

## Consensus

PoW (Proof of Work)

PoS (Proof of Staking)

DPoS (Distributed Proof of Staking)

RAFT

CFT

## Blockchain Solutions

### Bitcoin vs Ethereum

둘다 POW 기반 블록체인 (현재로써는). 핵심적인 차이는 Bitcoin은 utxo(unspent transaction output)기반으로 이전 tx의 output이 다음 tx의 input으로 들어가는 형식임. 그래서 한번에 여러 tx를 날리는 경우 output을 두번 이상 사용하는 에러가 발생할 수 있음. 이것이 Double spending 문제. 반면에 이더는 account기반으로 nonce라고 임의의 숫자 (라고 하고 그냥 증가하는 숫자)를 account의 tx별로 부여해서 double spending problem을 처리함.

이더는 스컨을 처음 이야기 한 블록체인으로 스컨이란 프로그램을 의미함. 블록체인이란게 결국 분산되서 값을 저장하고 이것에 대한 변경을 hash와 signature같은 것으로 엮어놔서 변경이 불가능한 것을 의미하는데 이러한 상태를 바꿀 수 있는 프로그램이라고 보면 됨. DAPP의 경우 그래서 core bussiness logic이 Node의 스컨에 있고 DApp Client가 이것과 상호작용하기 쉬운 인터페이스를 제공한다고 보면 됨.

이더에는 스컨때문에 Gas라는 개념이 있는데 이는 스컨에서 무한 루프를 방지하려고 나온 것임. 스컨을 실행하는 수행 시간에 gas라에 fee를 지불함으로써 수행 시간에 제약이 걸리게 됨. Fee를 다 쓰면 끝나버림.

Bitcoin, Ethreum 두개의 Chain DB는 거의 같음 (block들의 chain), State DB의 경우 Bitcoin은 UTXO를 저장하는 반면에 Ethereum은 Account의 nonce와 balance등을 저장함.

### EOS

### Hyperledger Fabric

## References

Blockchain

https://hyperledger-fabric.readthedocs.io/en/latest/blockchain.html

Deterministic Wallet

https://en.bitcoin.it/wiki/Deterministic_wallet

Consensus

https://steemit.com/kr/@brownbears/pow-pos-dpos

Hyperledger

https://medium.com/decipher-media/%ED%95%98%EC%9D%B4%ED%8D%BC%EB%A0%88%EC%A0%80-%ED%8C%A8%EB%B8%8C%EB%A6%AD-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EA%B5%AC%EC%A1%B0-hyperledger-fabric-network-structure-d7fd9c759983