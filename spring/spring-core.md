# Spring Core

- [Spring Core](#spring-core)
  - [IoC Container](#ioc-container)
    - [Java Bean](#java-bean)
    - [Plain Old Java Object](#plain-old-java-object)
    - [Spring Bean](#spring-bean)
    - [Spring Bean Scope](#spring-bean-scope)
    - [Singleton Beans with Prototype-bean Dependencies](#singleton-beans-with-prototype-bean-dependencies)
    - [ApplicationContext](#applicationcontext)
    - [@Autowired](#autowired)
    - [@Component and @ComponentScan](#component-and-componentscan)
  - [AOP (Aspect Oriented Programming)](#aop-aspect-oriented-programming)
  - [References](#references)

## IoC Container

IoC(Inversion of Control)이란 사용자가 작성한 프로그램이 framework로부터 흐름 제어를 받는 것을 말함.\
Spring IoC Container는 Object간의 dependency를 주입시켜주는 역할을 함. 사용자가 직접 하는게 아니라. 이 때 주입시켜주는 것을 Dependency Injection이라고 함.

Dependency Injection을 하는 방법으로는 Constructor에 인자로 넘기는 방법과 먼저 객체를 생성한 후 Setter를 통해 주입시키는 방법이 있음. 더 Deep하게는 reflection을 사용해서 내부 멤버에 접근해서 하는 방법도 있음.

Spring의 ApplicationContext implements BeanFactory가 IoC Container역할을 함.

### Java Bean

여러 객체를 하나의 객체에 담아두는 객체. NoArgsConstructor가 있어야 하고, serializable, member들은 로 선언,, member들에 getter, setter로 접근할 수 있어야 함.

해당 bean이 다른 application에도 사용될 수 있다는 장점이 있으나, zero-argument constructor로 인해 불완전한 상태로 초기화된다는 문제가 있음. 그리고 mutable하기 때문에 multi thread에서 동시성 문제도 발생할 수 있음.

### Plain Old Java Object

프레임워크에 의존적인 무거운 객체를 사용하게 되는 것에 대한 불만으로 나옴. 프레임워크 등에 의존적이지 않은 일반 자바 객체. Spring은 POJO기반에 기반해서 DI를 xml로 설정했었음. But 요즘은 annotation으로 xml을 대체해서 의존적임.

### Spring Bean

스프링 IoC 컨테이너가 관리 하는 객체로 Spring에서 관리하는 LifeCycle이 있음.

생성시 먼저 Properties를 설정함. setBeanName, setBeanFactory, setApplicationContext등으로 우선 property를 설정함
**Creation**

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

**Destory**

destroy() of DisposableBean

custom destory method

### Spring Bean Scope

- Singleton : 하나만 생성
- Prototype : 매번 생성
  - Request : HTTP request마다 한개.
  - Session : Session마다 한개. Session객체의 lifecycle을 따름
  - Application : Application마다 한개. ServletContext객체의 lifecycle을 따름
  - Websocket : Websocket마다 한개. Websocket객체의 lifecycle을 따름

### Singleton Beans with Prototype-bean Dependencies

Prototype bean이 singleton bean을 참조하는 것은 문제가 없음. But singleton bean이 prototype bean을 참조하면 바뀌지 않는다는 문제가 있음.

-> `@Scope("prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)`를 Prototype bean에 설정함으로써 해결 (Proxy 설정)
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