# Spring Core

- [Spring Core](#spring-core)
  - [IoC Container](#ioc-container)
    - [Java Bean](#java-bean)
    - [Plain Old Java Object](#plain-old-java-object)
    - [Spring Bean](#spring-bean)
      - [Singleton Beans with Prototype-bean Dependencies](#singleton-beans-with-prototype-bean-dependencies)
    - [Spring LifeCycle Callback](#spring-lifecycle-callback)
    - [ApplicationContext](#applicationcontext)
  - [Spring Boot Application Flow (대충)](#spring-boot-application-flow-%eb%8c%80%ec%b6%a9)
  - [AOP (Aspect Oriented Programming)](#aop-aspect-oriented-programming)
  - [Formatter vs Converter](#formatter-vs-converter)
  - [References](#references)

## IoC Container

- IoC (Inversion of Control)이란 사용자가 작성한 코드가 framework로부터 흐름 제어를 받는 것을 함
- Spring IoC Container는 객체간의 의존관계를 제어해줌. 즉 의존관계를 주입 (Dependency Injection)
- Spring에서는 ApplicationContext가 IoC Container임
- Dependency Injection 방법
  - Setter
  - 생성자에 인자로
  - Reflection기법으로 내부 멤버에 접근해서 초기화

### Java Bean

- 여러 객체를 하나의 객체에 담아두는 객체.
- 조건
  - 인자 없는 생성자
  - Serializable을 상속
  - field들은 private로 선언, getter/setter가 있어야 함
- 장점 : 객체가 별도의 조작 없이도 다른 application에서 사용될 수 있음
- 단점 : 인자 없는 생성자 때문에 불완전한 상태로 초기화, mutable해서 동시성 문제 발생여지

### Plain Old Java Object

- 프레임워크에 의존적인 무거운 객체를 사용하게 되는 것에 대한 불만으로 나온 프레임워크 등에 의존적이지 않은 일반 자바 객체
- Spring은 원래 POJO기반에 기반해서 의존성 주입을 xml로 했었음 (요즘은 `@Component`같은 annotation으로 설정해서 의존적)

### Spring Bean

- 스프링 IoC Container, 즉 ApplicationContext가 관리하는 객체
- Spring Bean LifeCycle (Scope)
  - Singleton : 하나만 생성
  - Prototype : 매번 생성
    - Request : HTTP request마다 한개.
    - Session : Session마다 한개. Session객체의 lifecycle을 따름
    - Application : Application마다 한개. ServletContext객체의 lifecycle을 따름.
      - SingleTon하고 다른점은 Application은 ServletContext에 따르는 반면에 SingleTon은 ApplicationContext에 따름. ApplicationContext는 여러개가 있을 수 있음!

#### Singleton Beans with Prototype-bean Dependencies

- Prototype bean이 Singleton bean을 참조하는 것은 문제가 없음
- But singleton bean이 prototype bean을 참조하면 바뀌지 않는다는 문제가 있음
- Proxy설정을 해서 매번 다른 instance를 return해서 해결

### Spring LifeCycle Callback

- Construct 시
  1. Methods annotated with `@PostConstruct`
  2. `afterPropertiesSet()` as defined by the `InitializingBean` callback interface
  3. A custom configured `init()` method
- Destory 시
  1. Methods annotated with `@PreDestroy`
  2. `destroy()` as defined by the `DisposableBean` callback interface
  3. A custom configured `destroy()` method

### ApplicationContext

- Spring IoC Container로써 빈 설정을 읽고 빈 정의를 제공. 다른 여러 가지 역할이 있음
  - ​EnvironmentCapable : Profile (bean들의 그룹)이나 Property (key-value형식의 환경설정)를 제공
  - MessageSource : 국제화 설정에 대한 정보 읽어서 locale에 맞는 정보를 제공
  - ApplicationEventPublisher : EventListener를 application context에 등록한 후 event를 publish 할 수 있음
  - ResouceLoader : "classpath:xxx", "file://xxx" 등 각종 리소스를 로딩

## Spring Boot Application Flow (대충)

- `@Component` and `@ComponentScan`
  - `@ComponentScan`에 설정되어 있는 패키지를 `ClassLoader.findResources()`에 요청
  - `​ConfigurationClassPostProcessor​ extends ​BeanFactoryPostProcessor​`가 bean으로 등록
- `@Autowired`
  - 빈으로 등록되어 있는 `AutowiredAnnotationBeanPostProcessor​ extends BeanPostProcessor` 클래스가 DI를 해줌

## AOP (Aspect Oriented Programming)

![spring-aop](./img/spring-aop.png)

- 관점 지향 프로그램으로 Object간의 Concern에 집중해서 흩어진 관점을 모듈화 할 수 있는 프로그래밍 기법
- AOP 용어
  - Aspect : Concern 자체
  - Advice : 어떤걸 적용할건지. Join point에서 취해야 할 Action.
  - Join Point : 어디에 적용할 건지. 스프링에서는 항상 method execution임.
  - Pointcut : Join point의 정확히 어디다 할건지 (method 실행 전? 후?)
  - Weaving : Join Point에 Advice를 삽입하는 과정
- Spring에서는 Aspect같은 annotation을 사용한 객체를 DynamicProxy로 감싸서 구현

## Formatter vs Converter

- Formatter
  - 특정 타입과 String간 변환해주는 것
  - Printer(to string), Parser(from string)를 상속
- Converter
  - 서로 다른 객체간 변환해 주는 것
  - 한 타입이 String일 경우 Formatter처럼 쓸 수 있으나 역할이 다름

## References

https://ko.wikipedia.org/wiki/Plain_Old_Java_Object

Official

https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html

AOP

https://engkimbs.tistory.com/746