# Token Swap Service

서로 다른 블록체인간 token을 swap해주는 서비스

- [Token Swap Service](#token-swap-service)
  - [Concept](#concept)
  - [What techs](#what-techs)
    - [React.js](#reactjs)
    - [Spring Boot, Security, Data](#spring-boot-security-data)
    - [Kotlin](#kotlin)
    - [Batch](#batch)
    - [ZooKeeper](#zookeeper)
    - [Liquibase, MySQL](#liquibase-mysql)
  - [Architecture](#architecture)
    - [Batch Jobs](#batch-jobs)
  - [What you did](#what-you-did)
  - [What you got?](#what-you-got)

## Concept

이더 ERC-20 AERGO 토큰 -> Native AERGO 토큰의 경우.

1. 사용자가 받고 싶은 Native AERGO address를 입력
2. 서비스는 이더 ERC-20 AERGO 토큰을 받기 위한 주소를 생성 후 사용자에게 알려줌
3. 사용자는 2에서 생성한 주소에 이더 ERC-20 AERGO 토큰을 전송
4. 서비스는 주기적으로 crawling을 해서 3의 tx를 확인하면 수수료(2 aergo)를 차감하고 1에서 받은 주소로 Native AERGO를 송금

## What techs

- Batch (Not spring batch) : Batch processing

### React.js

Displaying page. Why? 모름...

### Spring Boot, Security, Data

그냥 요즘 많이 써서 쓴것 아닐까 레퍼런스도 많고 인기도 많고.\
특히 security쪽에서 RBAC (Role Based Access Control)를 구현함!\
Admin page에 대한 access control을 위해서.. 오버스펙임...

### Kotlin

Why kotkin? 그냥 시험삼아 해본거 같음. Java 아니라도 가능하단 것을 시험해보고 싶어서. 실제로 Spring docs 보면 kotlin도 같이 있음.

### Batch

Spring batch 안쓰고 직접 구현함...

### ZooKeeper

Store configs, Distributd Lock for batch processing

### Liquibase, MySQL

- Liquibase : Database schema management
- MySQL : Store batch information, Blockchain information

## Architecture

![token-swap-db-schema](./img/token-swap-db-schema)

React.js - UI

Spring Boot - Backend Core

Batch          RBAC

JPA, Hibernate, Liquibase

MySQL, Blockchain (Aergo / Ethereum / Binance)

### Batch Jobs

- 2분 간격으로 blockchain을 cwarling해서 관계되어 있는 db를 처리하는 잡 3개 (aergo, ethereum, binance)
- 12시간 간격으로 consignment account에 들어온 돈을 송금하는 job 1개
- 알림 job1개

## What you did

- Maintanence
- Split batch processing

## What you got?

- 실 서비스 운영
