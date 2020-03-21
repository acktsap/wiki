# Spring Core

- [Spring Core](#spring-core)
  - [IoC Container](#ioc-container)
    - [Java Bean](#java-bean)
    - [Plain Old Java Object](#plain-old-java-object)
    - [Spring Bean](#spring-bean)
    - [Spring Bean LifeCycle (Scope)](#spring-bean-lifecycle-scope)
    - [Singleton Beans with Prototype-bean Dependencies](#singleton-beans-with-prototype-bean-dependencies)
    - [Spring LifeCycle Callback](#spring-lifecycle-callback)
    - [ApplicationContext](#applicationcontext)
  - [Spring Boot Application Flow](#spring-boot-application-flow)
    - [@Autowired](#autowired)
    - [@Component and @ComponentScan](#component-and-componentscan)
  - [AOP (Aspect Oriented Programming)](#aop-aspect-oriented-programming)
  - [Formatter vs Converter](#formatter-vs-converter)
  - [References](#references)

## IoC Container

IoC(Inversion of Control)이란 사용자가 작성한 프로그램이 framework로부터 흐름 제어를 받는 것을 말함. Spring IoC Container는 객체간의 의존관계를 주입시켜주는 역할을 함. 이 때 주입시켜주는 것을 Dependency Injection이라고 함.

Dependency Injection을 하는 방법으로는 Constructor에 인자로 넘기는 방법과 먼저 객체를 생성한 후 Setter를 통해 주입시키는 방법이 있음. 더 Deep하게는 reflection을 사용해서 내부 멤버에 접근해서 하는 방법도 있음.

Spring의 ApplicationContext implements BeanFactory가 IoC Container역할을 함.

### Java Bean

여러 객체를 하나의 객체에 담아두는 객체. NoArgsConstructor가 있어야 하고, serializable, member들은 로 선언, member들에 getter, setter로 접근할 수 있어야 함.

해당 bean이 다른 application에도 사용될 수 있다는 장점이 있으나, zero-argument constructor로 인해 불완전한 상태로 초기화된다는 문제가 있음. 그리고 mutable하기 때문에 multi thread에서 동시성 문제도 발생할 수 있음.

### Plain Old Java Object

프레임워크에 의존적인 무거운 객체를 사용하게 되는 것에 대한 불만으로 나옴. 프레임워크 등에 의존적이지 않은 일반 자바 객체. Spring은 POJO기반에 기반해서 DI를 xml로 설정했었음. But 요즘은 annotation으로 xml을 대체해서 의존적임.

### Spring Bean

스프링 IoC 컨테이너가 관리 하는 객체. Bean 자체의 LifeCycle이 있음.

### Spring Bean LifeCycle (Scope)

- Singleton : 하나만 생성
- Prototype : 매번 생성
  - Request : HTTP request마다 한개.
  - Session : Session마다 한개. Session객체의 lifecycle을 따름
  - Application : Application마다 한개. ServletContext객체의 lifecycle을 따름.
    - SingleTon하고 다른점은 Application은 ServletContext에 따르는 반면에 SingleTon은 ApplicationContext에 따름. ApplicationContext는 여러개가 있을 수 있음!

### Singleton Beans with Prototype-bean Dependencies

Prototype bean이 singleton bean을 참조하는 것은 문제가 없음. But singleton bean이 prototype bean을 참조하면 바뀌지 않는다는 문제가 있음. 그래서 이를 Proxy설정으로 해결함. `@Scope("prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)`를 Prototype bean에 설정.

### Spring LifeCycle Callback

Construct

1. Methods annotated with `@PostConstruct`
2. `afterPropertiesSet()` as defined by the `InitializingBean` callback interface
3. A custom configured `init()` method

Destory

1. Methods annotated with `@PreDestroy`
2. `destroy()` as defined by the `DisposableBean` callback interface
3. A custom configured `destroy()` method

### ApplicationContext

Spring IoC Container로써. 빈 설정을 읽고 빈 정의를 제공. 역할이 BeanFactory뿐만 아니라 여러 가지가 있음.

- ​EnvironmentCapable : Profile이나 Property을 제공. Profile은 bean들의 그룹 인데 test일 때는 어떤 거를 쓰고, dev에서는 어떤 빈들을 쓰고 이럴때 사용됨. Property는 그냥 key-value 형식의 property설정.
- MessageSource : 국제화 설정에 대한 정보를 `message.properies` 같은 걸로 읽고. `getMessage(String. Locale)`같은 걸로 Locale에 맞는 message를 제공해주는 역할.
- ApplicationEventPublisher : 이벤트 publish. EventListener를 등록한 후 applicationContext에서 event publish하면 이를 처리.
- ResouceLoader : "classpath:xxx", "file://xxx" 등 각종 리소스를 로딩.

## Spring Boot Application Flow

- @ComponentScan에 설정되어 있는 패키지를 `ClassLoader.findResources()`에 요청

### @Autowired

빈으로 등록되어 있는 `AutowiredAnnotationBeanPostProcessor​ extends BeanPostProcessor` 클래스가 DI를 해준다.

### @Component and @ComponentScan

`​ConfigurationClassPostProcessor​ extends ​BeanFactoryPostProcessor​`에의해 Bean으로 등록해줌

## AOP (Aspect Oriented Programming)

![spring-aop](./img/spring-aop.png)

관점 지향 프로그램으로 Object간의 Concern에 집중해서 흩어진 관점을 모듈화 할 수 있는 프로그래밍 기법. 용어로는

- Aspect : Concern 자체
- Advice : 어떤걸 적용할건지. Join point에서 취해야 할 Action.
- Join Point : 어디에 적용할 건지. 스프링에서는 항상 method execution임.
- Pointcut : Join point의 정확히 어디다 할건지 (method 실행 전? 후?)
- Weaving : Join Point에 Advice를 삽입하는 과정

Spring에서는 Proxy로 구현되어 있는데 Aspect같은 annotation을 spring bean 을 생성할 때 처리해서 대상 객체의 interface에 대한 dynamic proxy를 생성해서 request를 intercept?해서 구현. Runtime에 하는 식임. 구체적으로는 `AbstractAutoProxyCreator​ implements ​BeanPostProcessor`가 처리를 함.

그냥 Proxy를 사용하지 않은 이유는 그냥 Proxy를 모든 클래스에 다 정의하려면 모든 메소드를 다 구현하고 해서 귀찮아서 사용.

## Formatter vs Converter

Formatter는 특정 타입과 String간 변환해주는 것. Printer(to string), Parser(from string)를 상속하고 있음. 반면에 Converter는 서로 다른 타입간 변환을 해주는 역할. Converter에 한 객체가 String일 경우 Formatter처럼 쓸 순 있으나 어찌됬건 역할이 다름.

## References

https://ko.wikipedia.org/wiki/Plain_Old_Java_Object

Official

https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html

AOP

https://engkimbs.tistory.com/746