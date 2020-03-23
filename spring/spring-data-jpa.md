# Spring Data JPA

- [Spring Data JPA](#spring-data-jpa)
  - [Why](#why)
  - [How repository bean is injected](#how-repository-bean-is-injected)
  - [Query문을 작성하는 방법](#query%eb%ac%b8%ec%9d%84-%ec%9e%91%ec%84%b1%ed%95%98%eb%8a%94-%eb%b0%a9%eb%b2%95)
  - [Transactional](#transactional)
  - [References](#references)

## Why

대부분의 data access layer에서는 CRUD의 반복이 일어남. 이를 그냥 JPA만 사용해서 구현하는 것은 엄청난 반복이 일어남. Generic을 사용해서 이를 공통으로 처리하는 interface를 만들어보자! 해서 나온게 Spring Data JPA 프로젝트. JpaRepository같은 특정 인터페이스를 상속한 후 method를 규약에 맞게 작성하면 Spring이 CRUD에 대한 구현체를 주입시켜줌. 기본으로 제공되는 상속관계는 다음과 같음.

- Repostiory : Repository에 대한 Marker Interface
- CrudRepository : 기본 CRUD제공
- PagingAndSortingRepository : CrudRepository에 Pagination, Sorting 추가로 제공
- JpaRepository : Jpa version of Repostiory

## How repository bean is injected

ComponentScan을 한 뒤 `RepositoryFactorySupport` 를 상속받은 녀석이 적절한 Repository Bean을 주입시켜줌. 기본 Repository는 `SimpleJpaRepository`를 주입시켜주는데 사용자가 작성한 거는 dynamic proxy랑 AOP로 어찌어찌 해서 처리해 주는 것을 주입시켜주는 것으로 알음.

## Query문을 작성하는 방법

이름에 맞게 잘 작성하면 됨

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

## Transactional

Service에 붙는 annotation. 이 annotation이 있는 곳은 jpa의 transaction단위로 적용됨. unchecked exception의 경우에 roll-back함. 특정 설정을 해주면 checked exception일때도 rollback함.

## References

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/