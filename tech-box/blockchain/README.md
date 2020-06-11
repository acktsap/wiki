# Blockchain

- [Blockchain](#blockchain)
  - [What is it](#what-is-it)
  - [Transaction Making](#transaction-making)
  - [Smart Contract](#smart-contract)
  - [Mining](#mining)
  - [Consensus](#consensus)
  - [Hirarchical Deterministic Wallet](#hirarchical-deterministic-wallet)
  - [Blockchain Solutions](#blockchain-solutions)
    - [Bitcoin vs Ethereum vs EOS vs Hyperledger Fabric](#bitcoin-vs-ethereum-vs-eos-vs-hyperledger-fabric)
  - [References](#references)

## What is it

- 그냥 분산된 원장 (Distributed Ledger) 이 원장의 변경 사항을 crypto기술로 연결해놓아서 위변조가 불가능한 것
- 거래, transaction, 누가 누구한테 돈을 보냄. 각각의 transaction의 hash들을 모아서 merkle root의 hash를 만듬. 이거랑 이전 블록의 hash를 block header에 포함해서 block을 linked list처럼 hash로 연결. block들간 chain으로 연결되어 있기 때문에 blockchain.
- 이전 블록, 거래들 끼리도 hash로 연결되어 있기 때문에 변조를 하려면 이후의 블록들을 모두 다 바꿔야 함. 근데 분산되어 있기 때문에 쉽지 않음. 그래서 위 변조가 힘듬!

## Transaction Making

- 누가 누구한테 보낸다는 raw transaction을 만듬. 여기서 비트는 UTXO (Unspend Transaction Output)을 모아서 input으로 넣고 이더는 nonce값을 넣음.
- 만든 raw transaction에 private key로 서명을 함. 그러면 해당하는 address가 그 거래를 만든 것이 증명이 됨. 그래서 암호화폐라고도 부름.

## Smart Contract

- 블록체인이란게 결국 분산되서 값을 저장하고 이것에 대한 변경을 hash와 signature같은 것으로 엮어놔서 변경이 불가능한 것을 의미하는데 이러한 상태를 바꿀 수 있는 프로그램임
- DAPP의 경우 그래서 core bussiness logic이 node의 스컨에 있고 dApp client가 이것과 상호작용하기 쉬운 인터페이스를 제공함
- 이를 최초로 사용한 것이 이더리움

## Mining

- PoW (Proof of Work)에서 Block hash가 특정 값보다 낮은 값 (난이도)이 나올 때 까지 nonce를 brute force로 찾는 과정
- 그러한 nonce를 찾으면 해당 블록을 생성하고 수수료 보상을 받음
- 네트워크를 통해 전파를 하는데 다른 곳에서도 비슷한 시기에 생성을 한 경우 충돌이 발생. 이 경우 가장 긴 블록을 채택하도록 되어 있음. 그렇다고 짧은 체인에서 생성한 거래가 버려지진 않음. 선택된 chain에 거래 정보는 다 들어가 있어서 처리 순서만 좀 다르지 처리는 됨.
- Bitcoin에는 Mining 주기가 평균 10분이 되게 난이도 조절이 자동으로 됨

## Consensus

- PoW (Proof of Work)
  - nonce 값을 바꿔가면서 block header의 hash값이 일정 값 (난이도) 보다 낮은 값을 찾아내는 과정
  - 장점
    - 보안성이 좋음
  - 단점
    - 많은 컴퓨팅 파워가 필요
- PoS (Proof of Staking)
  - 토큰을 많이 보유한 소유자가 블록을 생성할 권한을 부여받는 방식
  - 장점
    - 컴퓨팅 파워가 많이 필요하지 않음
    - 블록 생성자가 토큰을 많이 보유했기 때문에 악의적 행동을 방지
  - 단점
    - 토큰을 묶어놓아서 토큰이 순환하지 않을 수가 있음
    - 토큰을 많이 보유한 사람이 권력을 가짐
- DPoS (Distributed Proof of Staking)
  - 토큰을 가진 계정들이 투표해서 선택된 소수의 노드가 돌아가면서 블록 생성
  - 장점
    - 빠름
  - 단점
    - 탈중앙화인가?
- RAFT
  - TODO
- CFT
  - TODO

## Hirarchical Deterministic Wallet

- Private key는 보통 큰 숫자값이라서 기억하기고 힘들고 저장도 어려움. But seed는 상대적으로 쉬움. 이것으로 private key를 생성한 것
- 여기서 이 seed값도 대충 지으면 쉬우니까 Seed phrase라고 12~24단어로 구성된 문자들을 가지고 만듬
- 이렇게 생성한 키를 가지고 자손 키를 결정적으로 만들고 할 수 있는걸로 알음. 삼성 KeyStore가 이걸로 되어 있는것으로 알음

## Blockchain Solutions

### Bitcoin vs Ethereum vs EOS vs Hyperledger Fabric

- Bitcoin
  - UTXO(unspent transaction output)기반으로 이전 tx의 output이 다음 tx의 input으로 들어가는 형식. 그래서 한번에 여러 tx를 날리는 경우 output을 두번 이상 사용하는 에러가 발생할 수 있음. 이것이 Double spending problem.
  - chain db는 block들의 chain을 저장 함, state db의 경우 UTXO를 저장
  - Smart contract가 없음
- Ethereum
  - account 기반으로 nonce라고 임의의 숫자 (라고 하고 그냥 증가하는 숫자)를 account의 tx별로 부여해서 double spending problem을 처리
  - chain db는 bitcoin과 거의 같음 (block들의 chain), state db의 경우 account의 nonce와 balance를 저장
  - Smart contract가 있음
    - 스컨에서 무한 루프를 방지하기 위해 Gas라는 개념이 있음. 스컨을 수행하면 gas라는 fee를 지불. gas를 다 쓰면 끝나버림
- EOS
  - 토큰을 staking함에 따라 CPU, NETWORK 등을 사용할 수 있는 권한을 부여받는 방식
- Hyperledger Fabric
  - TODO

## References

Blockchain

http://homoefficio.github.io/2016/01/23/BlockChain-%EA%B8%B0%EC%B4%88-%EA%B0%9C%EB%85%90/

https://homoefficio.github.io/2017/11/19/%EB%B8%94%EB%A1%9D%EC%B2%B4%EC%9D%B8-%ED%95%9C-%EB%B2%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0/

https://hyperledger-fabric.readthedocs.io/en/latest/blockchain.html

Deterministic Wallet

https://en.bitcoin.it/wiki/Deterministic_wallet

Consensus

https://steemit.com/kr/@brownbears/pow-pos-dpos

EOS

https://brunch.co.kr/@bumgeunsong/52

Hyperledger

https://medium.com/decipher-media/%ED%95%98%EC%9D%B4%ED%8D%BC%EB%A0%88%EC%A0%80-%ED%8C%A8%EB%B8%8C%EB%A6%AD-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EA%B5%AC%EC%A1%B0-hyperledger-fabric-network-structure-d7fd9c759983
