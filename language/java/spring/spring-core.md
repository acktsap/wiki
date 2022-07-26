# Spring Core

- [History](#history)
- [What is Spring Framework](#what-is-spring-framework)
- [Ioc Container](#ioc-container)
  - [Di? IoC? IoC Container? Bean?](#di-ioc-ioc-container-bean)
  - [Making Container](#making-container)
  - [Bean Definition](#bean-definition)
  - [Bean Instantiation](#bean-instantiation)
  - [How to Dependency Injection](#how-to-dependency-injection)
  - [Bean Scope](#bean-scope)
  - [Java based vs Xml based](#java-based-vs-xml-based)
- [Resources](#resources)
  - [Usage](#usage)
- [Validation, Data Binding and Type Conversion](#validation-data-binding-and-type-conversion)
- [Spring Expression Language (SpEL)](#spring-expression-language-spel)
- [Spring AOP](#spring-aop)
- [Null-safety](#null-safety)
- [Data Buffer and Codecs](#data-buffer-and-codecs)
- [Logging](#logging)
- [Reference](#reference)

## History

todo: overview 보고 하기

## What is Spring Framework

- DI framework 자체 (spring core).
- Enterprise application을 만들기 위한 여러 생태계 (spring web, spring security, ...)

## Ioc Container

### Di? IoC? IoC Container? Bean?

- DI : 객체간 의존성을 주입해주는 것.
- (Spring에서의) Inversion of Control : 객체 생성 후 DI를 통해 객체간 의존성을 주입해주는 것.
- IoC Container : DI를 통해 객체간 의존성을 주입해주는 친구.
- Bean : IoC container에서 관리하는 객체를 부르는 이름.

### Making Container

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

### Bean Definition

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

todo

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

## Resources

- `java.net.URL`의 기능이 부족해서 만듬. 다음의 기능 등이 부족.
  - 해당 리소스가 있는지.
  - classpath에서 리소스 가져오기.
- [`org.springframework.core.io`](https://github.com/spring-projects/spring-framework/blob/main/spring-core/src/main/java/org/springframework/core/io) 패키지에 I/O 관련 추상화를 놓음.
- Interface
  - `Resource` extends `InputStreamSource`
    - 기본 interface
  - `WritableResource` extends `Resource`
    - 쓸 수 있는 친구.
  - `ResourceLoader` : 말 그대로 resource load. 모든 `ApplicationContext`는 `ResourceLoader`임.
  - `ResourcePatternResolver` extends `ResourceLoader` : 패턴같은거로 load하는거. 모든 `ApplicationContext`는 `ResourceLoader`임.
    - eg. `classpath*:`
  - `ResourceLoaderAware` : resourceLoader 넣는 callback.
- Built-in Implementation
  - `UrlResource`
    - eg. `file:some_path`, `ftp:some_path`, `https:some_path`
  - `ClassPathResource`
    - Load by
      - thread context class loader.
      - a given class loader.
      - a given class for loading resources.
  - `FileSystemResource`
    - `java.io.File`
  - `PathResource`
    - `java.nio.file.Path`
  - `ServletContextResource`
    - relative paths to web application’s root directory.
  - `InputStreamResource`
    - Resource implementation for a given InputStream.
    - descriptor for an already-opened resource.
  - `ByteArrayResource`
    - Byte array.

> (공식 문서에서) `java.net.URL`를 대신해서 라이브러리 처럼도 사용하래. 나는 그런 용도로 쓰지는 않을테지만 스프링을 라이브러리로 쓴다는 기준은 어떤걸까?

### Usage

- [ResourceLoaderTest](./spring-core/src/main/java/acktsap/resource/ResourceLoaderTest.java)

## Validation, Data Binding and Type Conversion

## Spring Expression Language (SpEL)

## Spring AOP

## Null-safety

- Spring은 다음의 3 가지 null관련 annotation을 제공함. nullability를 명시하는것 뿐만 아니라 IDE, Kotlin null safety에서도 사용됨.
  - `@Nullable` : a parameter, return value, or field can be null.
  - `@NonNull` : a parameter, return value, or field cannot be null (not needed on parameters / return values and fields where @NonNullApi and @NonNullFields apply, respectively).
  - `@NonNullApi` : parameters and return values are to be considered as non-nullable by default for a given package.
  - `@NonNullFields` : fields are to be considered as non-nullable by default for a given package.
- Spring annotation은 [JSR-305](https://jcp.org/en/jsr/detail?id=305)로 meta-annotated 되어 있음. 그래서 이거 사용하는 spring 기반 라이브러리에서 `@Nullable`같은거 사용하면 `com.google.code.findbugs:jsr305:3.0.2`를 compileOnly (provided in maven)으로 import 시켜줘야 compile warning이 제거됨.
  - Only projects such as Spring-based libraries that use null-safety annotations in their codebase should add `com.google.code.findbugs:jsr305:3.0.2` with compileOnly Gradle configuration or Maven provided scope to avoid compile warnings
    > 내가 적어놓은 말이 맞나???

## Data Buffer and Codecs

## Logging

- spring-jcl로 자체 logging facade api 제공. classpath에서 Log4j 2.x API나 the SLF4J 1.7를 찾아서 binding. 둘다 못찾으면 `java.util.logging`에 있는거 사용.

## Reference

- [Spring Framework Overview (official)](https://docs.spring.io/spring-framework/docs/current/reference/html/overview.html)
- [Spring Framework Core (official)](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html)
- [What exactly is Spring Framework for? (stackoverflow)](https://stackoverflow.com/questions/1061717/what-exactly-is-spring-framework-for)
- [Need for Bean scopes in Spring frameowrk (stackoverflow)](https://stackoverflow.com/questions/61014383/need-for-bean-scopes-in-spring-frameowrk)
- [Benefits of JavaConfig over XML configurations in Spring? (stackoverflow)](https://stackoverflow.com/questions/29162278/benefits-of-javaconfig-over-xml-configurations-in-spring)
