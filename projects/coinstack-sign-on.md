# Coinstack Sign-On

Blockchain (coinstack) based Single-Sign-On server

## What techs?

- CoinStack (bitcoin based blockchain solution)
- OAuth2.0
- Spring Security OAuth
- Jmeter

## Architecture?

Core Class

- RepositoryFactory
- CoinstackRepositoryFactory : Create DynamicProxy for Repository
- CoinstackRepositoryHandler : Proxy handler, calls save and findById
- EntityManager : Extract address from className + id. Store state transfer in payload

Spring Security OAuth

Middle Libraries (coinstack-fsm, coinstack-spring)

EntityManager

Coinstack

## What you did?

- Implement AuthorizationCodeService, TokenService using State machine
- Wrote load test script using jmeter

## What you got?

- Contributed to Blockchain based OAuth server
