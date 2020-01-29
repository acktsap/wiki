# Spring Core

- [Spring Core](#spring-core)
  - [IoC Container](#ioc-container)
    - [Dependency Injection 방법](#dependency-injection-%eb%b0%a9%eb%b2%95)
    - [Terms](#terms)
      - [Java Bean](#java-bean)
      - [Plain Old Java Object](#plain-old-java-object)
    - [Bean](#bean)
    - [Bean Scope](#bean-scope)
    - [Singleton Beans with Prototype-bean Dependencies](#singleton-beans-with-prototype-bean-dependencies)
    - [Bean Lifecycle](#bean-lifecycle)
      - [Creation](#creation)
      - [Destory](#destory)
    - [ApplicationContext](#applicationcontext)
    - [@Autowired](#autowired)
    - [@Component and @ComponentScan](#component-and-componentscan)
  - [AOP (Aspect Oriented Programming)](#aop-aspect-oriented-programming)
  - [References](#references)

## IoC Container

IoC(Inversion of Control)이란 어떤 객체가 사용하는 ​의존 객체를 직접 만들어 사용하는게 아니라 주입 받아 사용하는 방법​을 말 함. 이를 Spring이 해줌. 이 때 주입하는 것을 Dependency Injection임.

### Dependency Injection 방법

- Constructor argument based
- Setter based

### Terms

#### Java Bean

여러개의 객체를 하나로 관리하는 클래스

- Serializable
- No args constructor
- Getter, Setter for all members

#### Plain Old Java Object

프레임워크에 의존적인 무거운 객체를 사용하게 되는 것에 대한 불만으로 나옴. 그냥 일반 자바 객체. Spring은 POJO기반

### Bean

스프링 IoC 컨테이너가 관리 하는 객체.

### Bean Scope

- Singleton : 하나만 생성
- Prototype : 매번 생성
  - Request : HTTP request마다 한개.
  - Session : Session마다 한개. Session객체의 lifecycle을 따름
  - Application : Application마다 한개. ServletContext객체의 lifecycle을 따름
  - Websocket : Websocket마다 한개. Websocket객체의 lifecycle을 따름

### Singleton Beans with Prototype-bean Dependencies

Prototype bean이 singleton bean을 참조하는 것은 문제가 없음. But singleton bean이 prototype bean을 참조하면 바뀌지 않는다는 문제가 있음.

-> `@Scope("prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)`를 Prototype bean에 설정함으로써 해결 (Proxy 설정)

### Bean Lifecycle

#### Creation

Instantiate

Properties 설정

setBeanName() of BeanNameAware

setBeanFactory() of BeanFactoryAware

setApplicationContext() of ApplicationContextAware

PostProcessBeforeInitialization() of BeanPostProcessors

afterpropertiesSet() of InitializingBeans

custom init method

postProcessAfterInitialization() of BeanPostProcessors

Bean Ready

#### Destory

destroy() of DisposableBean

custom destory method

### ApplicationContext

Spring IoC Container로써. 빈 설정 소스​로 부터 ​빈 ​정의​를 읽어들이고, 빈을 구성하고 제공​한다.

다음과 같은 인터페이스를 상속한다.

- BeanFactory : 빈 생성
- ​EnvironmentCapable : 환경 설정
- MessageSource : 국제화 설정
- ApplicationEventPublisher : 이벤트 publish, 옵져버 패턴으로 구현
- ResouceLoader : 리소스 로딩

### @Autowired

빈으로 등록되어 있는 `AutowiredAnnotationBeanPostProcessor​ extends BeanPostProcessor` 클래스가 DI를 해준다.

### @Component and @ComponentScan

`​ConfigurationClassPostProcessor​ extends ​BeanFactoryPostProcessor​`에의해 Bean으로 등록해줌

## AOP (Aspect Oriented Programming)

관점 지향 프로그램으로 object간의 concern에 집중한다. Spring에서는 runtime proxy로 구현한다.

- Aspect : 여러 개의 클래스들 간의 concern.
- Join point : concern을 적용할 대상. 스프링에서는 항상 method execution임.
- Pointcut : Join point에 대한 조건
- Advice : join point에서 취해야 할 Action.
- Weaving : Join point에 advice를 삽입하는 과정

## References

https://ko.wikipedia.org/wiki/Plain_Old_Java_Object

http://pigbrain.github.io/spring/2016/03/04/BeanLifeCycle_on_Spring