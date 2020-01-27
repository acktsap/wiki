# Spring Core

- [Spring Core](#spring-core)
  - [5. Aspect Oriented Programming with Spring](#5-aspect-oriented-programming-with-spring)
    - [5.1. AOP Concepts](#51-aop-concepts)
    - [5.2. Spring AOP Capabilities and Goals](#52-spring-aop-capabilities-and-goals)
    - [5.3. AOP Proxies](#53-aop-proxies)
    - [5.4. @AspectJ support](#54-aspectj-support)
      - [5.4.1. Enabling @AspectJ Support](#541-enabling-aspectj-support)

## 5. Aspect Oriented Programming with Spring

OOP는 key unit이 class인 반면에 AOP는 aspect이다. AOP는 multiple types and objects간의 concern (eg. transaction management)에 대한 모듈화가 가능하게 해준다.

AOP가 IoC container에 꼭 필요한 것은 아니지만 middleware level에서의 상당히 유용한 solution을 제공해 준다.

Spring에서 AOP는 다음과 같이 사용된다.

- Provide declarative enterprise services.
- 사용자가 custom aspects를 사용할 수 있게.

### 5.1. AOP Concepts

- Aspect : 여러 개의 클래스들 간의 concern.
- Join point : 실행 중인 프로그램의 point (eg. method execution). 스프링에서는 항상 method execution임.
- Advice : 특정한 join point에서 취해야 할 Action. Aspect에 전달 됨. 스프링을 포함한 대부분의 프레임워크에서 chain of intercepter로 구현.
- Pointcut : Join point에 대한 predicate (eg. "aaa"라는 method). Advice는 Pointcut에 해당하는 Join point에서 실행 됨. 스프링에서는 AspectJ라는 것으로 표현.
- Introduction : type 대신에 추가적인 method나 field를 선언할 수 있는 방법을 제공.
- Target object : Aspect의 target object. Advised object라고도 불림. 스프링 AOP가 runtime proxy로 구현되어 있기 때문에 스프링에서는 항상 proxied object임. 
- AOP proxy : AOP framework에서 aspect contract를 구현하기 위해 생성한 프록시 객체. Spring AOP에서는 JDK dynamic proxy또는 CGLIB proxy임.
- Weaving : Advised object를 생성하기 위해 aspect를 application types or object과 linking하는 과정. Spring AOP에서는 runtime 단계에서 진행.

Spring AOP는 다음과 같은 advice를 제공한다.

- Before advice : Before join point. 프로그램이 Join point로 가서 실행되는 것을 막을 수 없음.
- After returning advice : Join point가 정상적으로 return한 경우
- After throwing advice : Join point가 throw exception을 한 경우
- After (finally) advice : Join point의 정상 종료 여부와 관계 없이 실행
- Around advice : Join point를 감싸는 advice. Before, after invocation에 대해 customizing이 가능.

Around advice가 일반적으로 사용됨. 하지만 필요에 따라 가능한 한 범위가 제한된 advice를 사용하는 것을 추천.

Pointcut에 match되는 join point를 찾는 개념이 AOP의 핵심임. 이는 object-oriented hierarchy에 관계 없이 target object를 구할 수 있음.

### 5.2. Spring AOP Capabilities and Goals

Spring AOP는 순수 java로 구현되어 있음. class loader를 건드릴 필요가 없기 때문에 servlet container 나 application server에 적합함.

Spring AOP는 method execution만 join point로 처리함.

다른 AOP framework는 AOP구현에 집중한다. 반면에 Spring AOP의 목표는 Spring IoC와 상호작용해서 여러 문제들을 해결하는데 있다.

AspectJ와 경쟁관계가 아니라 서로 보완하는 관계라고 생각한다. 하지만 Spring에서는 IoC with AspectJ도 사용할 수 있게 기능을 제공한다. 그쪽 유저에게도 좋은 경험을 제공하려고.

```text
어떤 프레임워크를 사용할 때는 그 프레임워크에 종속적인 것을 사용하면 안된다. 하지만 몇몇 경우 프레임워크에 종속적인 것을 사용하는게 편리한 경우가 있다. 스프링 프레임워크에서는 두개의 방법을 모두 제공한다. 그래서 Spring에서도 @AspectJ와 함께 사용하는 것을 제공한다.
```

### 5.3. AOP Proxies

Spring AOP는 JDK의 dynamic proxy를 사용한다. 이는 interface에 대한 proxy를 제공한다.

Spring AOP에서 CGLIB proxy도 사용할 수 있다. 이는 class에 대한 proxy를 제공한다. 하지만 꼭 필요한 경우가 아니라면 권장되지 않는다.

### 5.4. @AspectJ support

AspectJ가 새 버전이 나와도 runtime만 바꾸면 되기 때문에 문제 없음.

#### 5.4.1. Enabling @AspectJ Support



