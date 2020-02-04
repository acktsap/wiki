# Coinstack Sign-On

블록체인 기반 OAuth 2.0 서버.

- [Coinstack Sign-On](#coinstack-sign-on)
  - [What techs](#what-techs)
    - [Blockchain](#blockchain)
    - [OAuth2.0](#oauth20)
    - [Spring Security OAuth](#spring-security-oauth)
    - [Jmeter](#jmeter)
  - [Concept](#concept)
  - [Architecture](#architecture)
  - [What you did?](#what-you-did)
  - [What you got?](#what-you-got)

## What techs

### Blockchain

비가역성이 특징. CoinStack (bitcoin based blockchain solution)을 사용.

### OAuth2.0

### Spring Security OAuth

### Jmeter

## Concept

## Architecture

Core Class

- RepositoryFactory
- CoinstackRepositoryFactory : Create DynamicProxy for Repository
- CoinstackRepositoryHandler : Proxy handler, calls save and findById
- EntityManager : Extract address from className + id. Store state transfer in payload to chat address

Spring Security OAuth

Middle Libraries (coinstack-fsm, coinstack-spring)

EntityManager

Coinstack

## What you did?

- Implement AuthorizationCodeService, TokenService using State machine
- Wrote load test script using jmeter

## What you got?

- Contributed to Blockchain based OAuth server
