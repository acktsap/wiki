# Spring Data Access

- [Transaction Management](#transaction-management)
  - [Advantage of Spring Transaction Support Model](#advantage-of-spring-transaction-support-model)
  - [Spring Transaction Abstraction](#spring-transaction-abstraction)
  - [Declarative Transaction Management](#declarative-transaction-management)
  - [Programmatic Transaction Management](#programmatic-transaction-management)
  - [Transaction bound event](#transaction-bound-event)
- [Reference](#reference)

Spring에서 data access를 하기 위한 추상화.

## Transaction Management

- Java Transaction API (JTA), JDBC, Hibernate, and the Java Persistence API (JPA)에 모두 쓸 수 있는 consistent programming model을 제공.

### Advantage of Spring Transaction Support Model

전통적인? 방법

- Global transaction
  - 여러 source에 대한 transaction.
  - JTA를 사용. 근데 사용하려면 JNDI가 필요해서 의존성이 생김.
  - JTA는 application server environment에서만 이용 가능해서 코드 재활용이 어려움.
  - 그래서 EJB CMT (Container Managed Transaction에서 제공하는 선언적인 transaction을 사용했음. But 이거도 JNDI, JTA에 의존성이 있고 application server environment에만 사용할 수 있다는 단점이 있었음.
- Local transaction
  - 단일 resource에 대한 transaction.
  - 특정 resource-specific함 (eg. JDBC connection에 관련된 transaction).
  - 여러 datasource에 대한 transaction을 할 수 없다는 단점이 있었음.

Spring은 기존의 단점을 보완해서 Global, Local 모두 consistent api를 사용하게 함.

### Spring Transaction Abstraction

- `TransactionManager` : Marker interface for Spring transaction manager.
- `PlatformTransactionManager` : Interface for imperative transaction management.
- `ReactiveTransactionManager` : Interface for reactive transaction management.
- 이런 interface들에 대한 구현체를 bean으로 등록해서 사용. 이런 훌륭한 추상화를 제공하기 때문에 적절한 구현체를 bean으로 등록해주면 됨 (eg. JTA를 쓰기 위해서는 `JtaTransactionManager` 사용).
- TransactionManager들은 unchecked exception인 `TransactionException`을 던짐. 기본적으로 transaction failure를 처리할 수 있는 상황 자체가 적으므로 unchecked exception으로 던짐.

### Declarative Transaction Management

### Programmatic Transaction Management 

- JTA보다 간단함.

### Transaction bound event

## Reference

- [Data Access (official)](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html)
