# Spring Data Jpa

- [Spring Data Jpa](#spring-data-jpa)
  - [How repository bean is injected](#how-repository-bean-is-injected)
  - [Transactional](#transactional)

## How repository bean is injected

Proxy pattern이란 Interface가 있고 실제 일하는 클래스 하나 있고 그걸 감싸는 클래스가 하나 있는 식임. 프록시 클래스가 뭔가 요청을 받으면 그 요청에 대해 앞뒤로 다른 작업을 할 수 있음. 이 방법에는 여러가지가 proxy에 대해 직접 구현을 해서 모든 method를 구현하는게 있음. But 전부 method구현하는건 귀찮음. 그래서 jdk에서는 dynamicproxy가 있음 method하나로 reflection사용해서 method이름 가져와서 어찌 잘 처리함. Spring에서는 ProxyFactory를 통해 추상화 해서 제공함. Spring에서는 ComponentScan을 한 뒤 `RepositoryFactorySupport` 라는 녀석이 Repository를 상속한 녀석을 찾은 후 ProxyFactory를 써서 bean 만들어줌. 여기서 이름에 따라 적절하게 일을 해주는 (findByIdAndName) 이런거에 맞는 jpa호출을 해줌

## Transactional

Service에 붙는 annotation. Database의 transaction (all or nothing)을 지원 unchecked exception의 경우에만 roll-back함.
