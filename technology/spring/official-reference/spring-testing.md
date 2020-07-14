# Spring Testing

- [Spring Testing](#spring-testing)
  - [1. Introduction to Spring Testing](#1-introduction-to-spring-testing)
  - [2. Unit Testing](#2-unit-testing)
    - [Mock Objects](#mock-objects)
    - [Unit Testing Support Classes](#unit-testing-support-classes)
  - [3. Integration Testing](#3-integration-testing)
    - [Overview](#overview)
    - [Goals of Integration Testing](#goals-of-integration-testing)
    - [JDBC Testing Support](#jdbc-testing-support)
    - [Annotations](#annotations)
  - [Reference](#reference)

## 1. Introduction to Spring Testing

Inversion of Control (IoC)은 의존관계 주입을 알아서 해줘서 테스트를 위한 객체를 쉽게 만들어준다.

## 2. Unit Testing

Spring은 Unit Testing을 위한 mock object와 util class들을 제공해줌.

### Mock Objects

- Environment : `org.springframework.mock.env` package. `Environment`와 `PropertySource`에 대한 mock을 제공.
- JNDI : `org.springframework.mock.jndi` package. `JNDI SPI` (JNDI : Java Naming and Directory Interface).
- Servlet API : `org.springframework.mock.web` package. Spring MVC를 테스트 하기 위한 mock을 제공.
- Spring Web Reactive : `he org.springframework.mock.http.server.reactive` package. `ServerHttpRequest`와 `ServerHttpResponse`에 대한 mock을 제공.

### Unit Testing Support Classes

- General Testing Utilities : `org.springframework.test.util` package.
  - ReflectionTestUtils : protected, private 로 선언된 값들을 바꿔서 테스트 할 때 사용.
  - AopTestUtils : 테스트 대상의 object가 proxy로 감싸져 있는 경우 해당 object를 mocking 할 때 사용.
- Spring MVC Testing Utilities : `org.springframework.test.web` package. `ModelAndView`를 JUnit같은 다른 framework를 사용해서 테스트 할 수 있는 기능을 제공.

## 3. Integration Testing

### Overview

- Spring Ioc context나 JDBC, ORM tool과 같은 것을 실제 배포하기 전에 통합해서 테스트 해볼 필요가 있음.
- spring-test module의 `org.springframework.test`에 통합 테스트를 위한 class들이 있음.
- 통합 테스트 모듈들은 unit test보단 느리지만 selenium같은 것을 사용해서 하는 실제 통합 테스트보다는 빠름.

### Goals of Integration Testing

- Context Management and Caching
  - `ApplicationContext`와 `WebApplicationContext`에 대한 caching을 지원해서 테스트 할 때 마다 매번 초기화 할 필요가 없게 함.
  - 테스트 도중 bean에 변경이 발생해서 reloading해야 하는 경우도 설정해 주면 처리해줌.
- Dependency Injection of Test Fixtures
  - Test Fixture : 테스팅을 할 때 필요해서 미리 준비해준 리소스. 환경이나 db, 객체도 될 수 있음.
  - Spring은 이러한 test fixture에 대한 dependency injection을 제공함.
  - 예를 들면 `HibernateTitleRepository`를 테스트 하는 경우 다음과 같은 것을 테스트 할 수 있음. 이것들에 대한 설정을 제공함.
    - The Spring configuration : `HibernateTitleRepository`에 대한 Spring 설정들이 잘 동작하는가?
    - `HibernateTitleRepository`에 대한 entity인 `Title`에 대한 hibernate mapping이 잘 동작하는가?
    - `HibernateTitleRepository`자체는 spec에 맞게 잘 동작하는가?
- Transaction Management
  - db를 사용하는 테스트는 persistence layer의 변경이 일어나면 테스트간 간섭이 있을 수 있음.
  - TestContext Framework는 transaction을 사용하는 테스트를 돌린 뒤 자동으로 roll back 해줌.
  - `@Commit` annotation을 사용하면 roll back을 하지 않고 commit을 해줌.
- Support Classes for Integration Testing
  - TestContext Framework는 integration test를 작성하기 위한 abstract class를 제공해줌. 이것들은 다음의 기능이 있음.
    - Test를 위한 `ApplicationContext`에 대한 접근.
    - SQL query를 위한 `JdbcTemplate`에 대한 접근. db에 관계되어 있는 테스트를 한 뒤 query를 할 때 같은 transaction위에 있게 함.

### JDBC Testing Support

- `org.springframework.test.jdbc`가 제공하는 `JdbcTestUtils`은 jdbc에 관계되어 있는 테스트 시나리오들에 대한 util을 제공함. 예를 들면.
  - `countRowsInTable(..)`, `countRowsInTableWhere(..)`
  - `deleteFromTables(..)`, `deleteFromTableWhere(..)`
  - `dropTables(..)`

### Annotations

- Spring Testing Annotations : TestContext framework와 함께 사용할 수 있는 annotation들을 제공
  - @BootstrapWith : Spring TestContext를 설정
  - @ContextConfiguration : 테스트용 ApplicationContext 설정
  - @WebAppConfiguration : @ContextConfiguration랑 같이 쓰여야 함. file:src/main/webapp를 기반으로 WebApplicationContext load함
  - @ContextHierarchy : @ContextConfiguration간 hierarchy를 정의
  - @ActiveProfiles : 테스트용 Profiles 설정
  - @TestPropertySource : 테스트용 PropertySources (in the Environment for an ApplicationContext) 설정
  - @DynamicPropertySource
  - @DirtiesContext
  - @TestExecutionListeners
  - @Commit
  - @Rollback
  - @BeforeTransaction
  - @AfterTransaction
  - @Sql
  - @SqlConfig
  - @SqlMergeMode
  - @SqlGroup
- Standard Annotation Support : Spring 일반 annotation 지원
  - @Autowired
  - @Qualifier
  - @Value
  - @Resource (javax.annotation) if JSR-250 is present
  - @ManagedBean (javax.annotation) if JSR-250 is present
  - @Inject (javax.inject) if JSR-330 is present
  - @Named (javax.inject) if JSR-330 is present
  - @PersistenceContext (javax.persistence) if JPA is present
  - @PersistenceUnit (javax.persistence) if JPA is present
  - @Required
  - @Transactional (org.springframework.transaction.annotation) with limited attribute support

## Reference

- [Spring Testing](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/testing.html)
