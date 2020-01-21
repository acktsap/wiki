# Token Swap Service

서로 다른 블록체인간 token을 swap해주는 서비스

## What techs?

- React.js : Displaying page
- Spring Boot : Spring MVC
- Spring Security : Emulating rbac
  - RBAC (Role Based Access Control) : Admin page access control
- Kotlin
- Batch (Not spring batch) : Batch processing
- ZooKeeper : Store configs, Distributd Lock
- MySQL : Store batch information, Blockchain information

## Architecture?

![token-swap-db-schema](./img/token-swap-db-schema)

React.js

Spring Boot

Batch          RBAC

JPA, Hibernate, Liquibase

MySQL Blockchain (Aergo Ethereum Binance)

## What you did?

- Maintanence

## What you got (feeling)?

- 실 서비스 운영
