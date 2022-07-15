# Spring Core

- [History](#history)
- [What is Spring Framework](#what-is-spring-framework)
- [Di? IoC? IoC Container? Bean?](#di-ioc-ioc-container-bean)
- [Making Container](#making-container)
- [Bean Definition](#bean-definition)
  - [Bean Instantiation](#bean-instantiation)
  - [How to Dependency Injection](#how-to-dependency-injection)
  - [Bean Scope](#bean-scope)
  - [Java based vs Xml based](#java-based-vs-xml-based)
- [Reference](#reference)

## History

todo: overview 보고 하기

## What is Spring Framework

- DI framework 자체 (spring core).
- Enterprise application을 만들기 위한 여러 생태계 (spring web, spring security, ...)

## Di? IoC? IoC Container? Bean?

- DI : 객체간 의존성을 주입해주는 것.
- (Spring에서의) Inversion of Control : 객체 생성 후 DI를 통해 객체간 의존성을 주입해주는 것.
- IoC Container : DI를 통해 객체간 의존성을 주입해주는 친구.
- Bean : IoC container에서 관리하는 객체를 부르는 이름.

## Making Container

![spring-core-container](./img/spring-core-container.png)

- POJO를 가지고 어떻게 객체를 생성하고 할건지 명시한 Configuration Metadata를 읽어서 IoC container를 만듦.
- Implementation
  - java로 설정 읽기
    - `AnnotationConfigApplicationContext`
  - xml로 설정 읽기
    - `ClassPathXmlApplicationContext`
    - `FileSystemXmlApplicationContext`
  - `GenericApplicationContext`
    - context 읽는 reader를 delegate.
- IoC container interface
  - `BeanFactory` : IoC container
  - `ApplicationContext` : BeanFactory 상속. enterprise-specific 기능을 조금 더 추가한 것.
   
## Bean Definition

- Bean을 어떻게 만들건지에 대해 configuration metadata에 적어두는 것.
- Properties
  - Class : 어떤 클래스에 대한 bean인지.
  - Name : Bean을 뭐로 구분할건지 (identifier).
  - Scope : IoC container로부터 받은 Bean이 어떤 lifecycle을 가질건지.
  - Constructor arguments : bean 만들때 인자.
  - Properties : bean 만들때 사용할 속성값들.
  - Autowiring mode : autowiring을 어떤 방식으로 할지 (byName, byType, ...)
  - Lazy InitializingBean mode : singleton bean을 미리 안만들고 요청 받을 때 만드는 설정.
  - Initialization method : 빈 생성 시 callback.
  - Destruction method : 빈 제거 시 callback.
- [BeanDefinition interface](https://github.com/spring-projects/spring-framework/blob/main/spring-beans/src/main/java/org/springframework/beans/factory/config/BeanDefinition.java)

### Bean Instantiation

- Instantiation with a Constructor
  ```xml
  <bean id="exampleBean" class="examples.ExampleBean"/>
  ```
- Instantiation with a Static Factory Method
  ```xml
  <bean id="clientService"
      class="examples.ClientService"
      factory-method="createInstance"/>
  ```
  ```java
  public class ClientService {
      private static ClientService clientService = new ClientService();
      private ClientService() {}

      public static ClientService createInstance() {
          return clientService;
      }
  }
  ```
- Instantiation by Using an Instance Factory Method
  ```xml
  <!-- the factory bean, which contains a method called createInstance() -->
  <bean id="serviceLocator" class="examples.DefaultServiceLocator">
      <!-- inject any dependencies required by this locator bean -->
  </bean>

  <!-- the bean to be created via the factory bean -->
  <bean id="clientService"
      factory-bean="serviceLocator"
      factory-method="createClientServiceInstance"/>
  ```
  ```java
  public class DefaultServiceLocator {

      private static ClientService clientService = new ClientServiceImpl();

      public ClientService createClientServiceInstance() {
          return clientService;
      }
  }
  ```


### How to Dependency Injection

- Constructor-based : 생성자의 인자를 통해 의존하는 객체 주입.
- Setter-based : setter를 이용해서 의존하는 객체 주입.

### Bean Scope

### Java based vs Xml based

- Java based
  - 장점
    - type safe함. compiler가 error 잡아줌
    - refactoring이 좋음.
  - 단점
    - 설정 변경시 recompile이나 rebuild 과정이 새로 필요함.
- Xml based
  - Pros
    - configuration이 Java랑 별개로 있어서 recompile이나 rebuild 없이 설정을 바꿀 수 있음.
  - Cons
    - Java에 비해 가독성이 후짐.
    - 파일이 커질 수 있음.

## Reference

- [Spring Framework Overview (official)](https://docs.spring.io/spring-framework/docs/current/reference/html/overview.html)
- [Spring Framework Core (official)](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html)
- [What exactly is Spring Framework for? (stackoverflow)](https://stackoverflow.com/questions/1061717/what-exactly-is-spring-framework-for)
- [Need for Bean scopes in Spring frameowrk (stackoverflow)](https://stackoverflow.com/questions/61014383/need-for-bean-scopes-in-spring-frameowrk)
- [Benefits of JavaConfig over XML configurations in Spring? (stackoverflow)](https://stackoverflow.com/questions/29162278/benefits-of-javaconfig-over-xml-configurations-in-spring)