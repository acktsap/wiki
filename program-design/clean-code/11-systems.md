# 11. Systems

- [11. Systems](#11-systems)
  - [Introduction](#introduction)
  - [How Would You Build a City?](#how-would-you-build-a-city)
  - [Separate Constructing a System from Using It](#separate-constructing-a-system-from-using-it)
    - [Separation of Main](#separation-of-main)
    - [Factories](#factories)
    - [Dependency Injection](#dependency-injection)
  - [Scaling Up](#scaling-up)
    - [Cross-Cutting Concerns](#cross-cutting-concerns)
  - [Java Proxies](#java-proxies)
  - [Pure Java AOP Frameworks](#pure-java-aop-frameworks)
  - [AspectJ Aspects](#aspectj-aspects)
  - [Test Drive the System Architecture](#test-drive-the-system-architecture)
  - [Optimize Decision Making](#optimize-decision-making)
  - [Use Standards Wisely, When They Add DemonstrableValue](#use-standards-wisely-when-they-add-demonstrablevalue)
  - [Systems Need Domain-Specific Languages](#systems-need-domain-specific-languages)
  - [Conclusion](#conclusion)

## Introduction

- Complexity kills. It sucks the life out of developers, it makes products difficult to plan, build, and test.

## How Would You Build a City?

- 도시는 너무 커서 혼자서 관리할 수 없음. 그래서 여럿이서 관리. 일부는 큰 그림을 보고 일부는 상수도같은 디테일을 관리함.
- 도시는 module간 추상화를 통해 큰 그림을 몰라도 component끼리 상호작용할 수 있음.
- 소프트웨어 시스템도 비슷하게 돌아감. 여기서 Clean Code는 좋은 추상화에 도움을 줌.

## Separate Constructing a System from Using It

- System을 구축하는거랑 사용하는것은 완전히 다름. 그래서 startup process 관심사를 분리시켜야 햠.
- 예제 코드
  ```java
  public Service getService() {
    if (service == null) {
      service = new MyServiceImpl(...); // Good enough default for most cases?
    }
    return service;
  }
  ```
  - 장점
    - lazy evaluation
  - 단점
    - MyServiceImpl 클래스에 의존성이 생김. 심지어 사용하지 않아도 compile time에 필요.
    - Test하기 힘듬. Test할 때 생성되는 시점도 다 테스트 해야 함.
    - 심지어 이 snippet이 여러 곳에 퍼져있으면 한 관심사가 여러곳에 있는거임.

### Separation of Main

- 생성하는 부분을 main logic에 놓고 사용하는 쪽에서는 생성이 이미 잘 되었다고 가정하고 사용하는 방법이 있음.
- 이렇게 하면 사용하는 쪽에서는 생성이 어떻게 되는지 알 필요가 없음.

> 마치 스프링이 bean간 의존성 알아서 주입해주고 하는 것처럼

### Factories

- 가끔은 언제 생성되느냐가 중요해서 생성을 사용하는 쪽에서 해야 하는 경우가 있음. 이럴때 팩토리를 사용하는 쪽에 넘겨서 적절한 시기에 사용하는 쪽에서 객체를 생성하게 하면 됨.
- Factory가 생성하게 되면 어떻게 생성하느냐가 사용하는 쪽과 분리됨.

### Dependency Injection

- Dependency Injection (DI)를 하면 의존성 관리에 대해 Inversion of Control (IoC, 제어의 역전)을 할 수 있어서 object가 의존성 관리를 할 필요가 없음.
- eg. JNDI
  ```java
  // MyService : interface
  MyService myService = (MyService) (jndiContext.lookup(“NameOfMyService”));
  ```
  - JNDI가 의존성 알아서 주입해주고 리턴해줌.
  - Lazy evaluation도 DI container가 필요할때만 생성하게 해서 해줄 수 있음.

## Scaling Up

- 도시에 처음부터 다 있진 않음. 수도, 전기, 인터넷 등이 모두 도시가 커지면서 생기는 것.
- 그 과정은 순탄하지않음. 그러고 "왜 처음부터 이걸 생각하지 못한거지?" 라고 함. But 처음부터 모든 것을 생각하고 하는건 불가능임.
- Code level에서는 처음에는 작게 만들었다가 새로운 요구사항은 clean code, refactoring, tdd 같은걸 하면서 추가할 수 있음.
- System level의 경우 separation of concerns (관심사 분리)가 잘되어 있으면 보다 복잡한 시스템으로 보다 쉽게 갈 수 있음.
  - 안좋은 예시 : ejb
    ```java
    // Client가 사용하는 Bank interface
    public interface BankLocal extends java.ejb.EJBLocalObject {
      String getStreetAddr1() throws EJBException;
      String getStreetAddr2() throws EJBException;
      String getCity() throws EJBException;
      String getState() throws EJBException;
      String getZipCode() throws EJBException;
      void setStreetAddr1(String street1) throws EJBException;
      void setStreetAddr2(String street2) throws EJBException;
      void setCity(String city) throws EJBException;
      void setState(String state) throws EJBException;
      void setZipCode(String zip) throws EJBException;
      Collection getAccounts() throws EJBException;
      void setAccounts(Collection accounts) throws EJBException;
      void addAccount(AccountDTO accountDTO) throws EJBException;
    }

    // Table row를 표현하는 Bank class
    public abstract class Bank implements javax.ejb.EntityBean {
      // Business logic...
      public abstract String getStreetAddr1();
      public abstract String getStreetAddr2();
      public abstract String getCity();
      public abstract String getState();
      public abstract String getZipCode();
      public abstract void setStreetAddr1(String street1);
      public abstract void setStreetAddr2(String street2);
      public abstract void setCity(String city);
      public abstract void setState(String state);
      public abstract void setZipCode(String zip);
      public abstract Collection getAccounts();
      public abstract void setAccounts(Collection accounts);
      public void addAccount(AccountDTO accountDTO) {
          InitialContext context = new InitialContext();
          AccountHomeLocal accountHome = context.lookup("AccountHomeLocal");
          AccountLocal account = accountHome.create(accountDTO);
          Collection accounts = getAccounts();
          accounts.add(account);
      }
      
      // EJB container logic
      public abstract void setId(Integer id);
      public abstract Integer getId();
      public Integer ejbCreate(Integer id) { ... }
      public void ejbPostCreate(Integer id) { ... }
      
      // The rest had to be implemented but were usually empty:
      public void setEntityContext(EntityContext ctx) {}
      public void unsetEntityContext() {}
      public void ejbActivate() {}
      public void ejbPassivate() {}
      public void ejbLoad() {}
      public void ejbStore() {}
      public void ejbRemove() {}
    }
    ```
    - Business logic이 EJB container랑 결합되어 있음.
    - EJB container랑 결합되어 있어서 business logic unit test를 하려면 EJB container를 mock을 해야함. 
    - 새 값이 추가되는 경우 같은 것을 표현하는 클래스를 두개 만들어야 함.

### Cross-Cutting Concerns

- EJB는 transactional, security 같은거는 separation of concern을 잘 지킴.
- persistence 같은 concern은 여러 객체들에 일괄되게 적용되어야 하는데 이런걸 cross-cutting concerns 이라고 함.
- EJB는 AOP framework를 사용해서 cross-cutting concerns을 비침투적으로 적용함.

## Java Proxies

- 단순한 Java Proxy
  ```java
  // The abstraction of a bank.
  public interface Bank {
    Collection<Account> getAccounts();
    void setAccounts(Collection<Account> accounts);
  }

  // The “Plain Old Java Object” (POJO) implementing the abstraction.
  public class BankImpl implements Bank {
    private List<Account> accounts;

    public Collection<Account> getAccounts() {
      return accounts;
    }
    
    public void setAccounts(Collection<Account> accounts) {
      this.accounts = new ArrayList<Account>();
      for (Account account: accounts) {
        this.accounts.add(account);
      }
    }
  }

  // “InvocationHandler” required by the proxy API.
  public class BankProxyHandler implements InvocationHandler {
    private Bank bank;
    
    public BankHandler (Bank bank) {
      this.bank = bank;
    }
    
    // Method defined in InvocationHandler
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String methodName = method.getName();
      if (methodName.equals("getAccounts")) {
        bank.setAccounts(getAccountsFromDatabase());
        
        return bank.getAccounts();
      } else if (methodName.equals("setAccounts")) {
        bank.setAccounts((Collection<Account>) args[0]);
        setAccountsToDatabase(bank.getAccounts());
        
        return null;
      } else {
          ...
      }
    }
    
    // Lots of details here:
    protected Collection<Account> getAccountsFromDatabase() { ... }
    protected void setAccountsToDatabase(Collection<Account> accounts) { ... }
  }

  // Somewhere else...
  Bank bank = (Bank) Proxy.newProxyInstance(
      Bank.class.getClassLoader(),
      new Class[] { Bank.class },
      new BankProxyHandler(new BankImpl())
  );
  ```
  - Boilerplate code가 많아서 Clean code가 힘듦.
  - 진정한 의미에서의 AOP를 구현할 때 필요한 system-wide level에서의 관심사 분리를 제공하지 못함.

## Pure Java AOP Frameworks

- Spring AOP 같은 툴을 사용하면 boilerplate code를 줄일 수 있음.
- AOP 툴을 통해 persistence, transaction 같은 cross-cutting concerns을 분리시킴으로써 business logic을 POJO로만 구현할 수 있음.
- Spring 동작방식
  - 정의
    ```xml
    <beans>
        <bean id="appDataSource"
            class="org.apache.commons.dbcp.BasicDataSource"
            destroy-method="close"
            p:driverClassName="com.mysql.jdbc.Driver"
            p:url="jdbc:mysql://localhost:3306/mydb"
            p:username="me"/>
        
        <bean id="bankDataAccessObject"
            class="com.example.banking.persistence.BankDataAccessObject"
            p:dataSource-ref="appDataSource"/>
        
        <bean id="bank"
            class="com.example.banking.model.Bank"
            p:dataAccessObject-ref="bankDataAccessObject"/>
    </beans>
    ```
  - 사용
    ```java
    XmlBeanFactory bf = new XmlBeanFactory(new ClassPathResource("app.xml", getClass()));
    Bank bank = (Bank) bf.getBean("bank");
    ```
  - 특징
    - xml은 다소 복잡하지만 이전의 dynamic proxy 보단 간단함.
    - 사용하는 쪽이 spring하고 거의 분리되어 있음.
- EJB3 : annotation을 활용한 cross-cutting concerns
  ```java
  @Entity
  @Table(name = "BANKS")
  public class Bank implements java.io.Serializable {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Embeddable // An object “inlined” in Bank’s DB row
    public class Address {
      protected String streetAddr1;
      protected String streetAddr2;
      protected String city;
      protected String state;
        protected String zipCode;
    }
    
    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="bank")
    private Collection<Account> accounts = new ArrayList<Account>();

    public int getId() {
      return id;
    }
    
    public void setId(int id) {
      this.id = id;
    }
    
    public void addAccount(Account account) {
      account.setBank(this);
      accounts.add(account);
    }
    
    public Collection<Account> getAccounts() {
      return accounts;
    }
    
    public void setAccounts(Collection<Account> accounts) {
      this.accounts = accounts;
    }
  }
  ```

## AspectJ Aspects

- AspectJ는 AOP를 위한 보다 더 강력한 기능을 제공함.

> 2021 현재 최신 spring boot는 AspectJ를 기본으로 사용

## Test Drive the System Architecture

todo: 정리

코드 레벨에서부터 아키텍쳐와 분리된(decouple된) 프로그램 작성은 당신의 아키텍쳐를 test drive하기 쉽게 만들어 준다. 처음에는 작고 간단한 구조에서 시작하지만 필요에 따라 새로운 기술을 추가해 정교한 아키텍쳐로 진화할 수 있다. 또한 decouple된 코드는 user story, 규모 변화와 같은 변경사항에 더 빠르게 대처할 수 있도록 우리를 도와 준다. 도리어 BDUF(Big Design Up First)와 같은 방식은 변경이 생길 경우 기존의 구조를 버려야 한다는 심리적 저항, 아키텍쳐에 따른 디자인에 대한 고민 등 변화에 유연하지 못한 단점들을 가져오게 된다.
초기 EJB와 같이 너무 많은 엔지니어링이 가미되어 많은 concern들을 묶어버리지 않으며 오히려 많은 부분들을 숨기는 것이 아름다운 구조를 가져올 것이다.

이상적인 시스템 아키텍쳐는 각각 POJO로 만들어진 모듈화된 관심 분야 영역(modularized domains of concern)으로 이루어져야 한다. 다른 영역끼리는 Aspect의 개념을 사용해 최소한의 간섭으로 통합되어야 한다. 이러한 아키텍쳐는 코드와 마찬가지로 test-driven될 수 있다.


## Optimize Decision Making

- Modularity와 separation of concern은 의사결정을 분리해서 할 수 있게 해줌.
- 의사결정은 필요한 정보가 모일때 까지 가능한 최대한 늦춰서 해당 component에 맡기는게 좋음.

## Use Standards Wisely, When They Add DemonstrableValue

todo: 정리

많은 소프트웨어 팀들은 훨씬 가볍고 직관적인 디자인이 가능했음에도 불구하고 그저 표준이라는 이유만으로 EJB2 구조를 사용했다. 표준에 심취해 "고객을 위한 가치 창출"이라는 목표를 잃어 버렸기 때문이다.

표준은 아이디어와 컴포넌트의 재사용, 관련 전문가 채용, 좋은 아이디어의 캡슐화, 컴포넌트들의 연결을 쉽게 도와 준다. 하지만 종종 표준을 만드는 데에 드는 시간은 납품 기한을 맞추기 어렵게 만들고, 혹은 최초에 제공하려던 기능과 동떨어지게 되기도 한다.

## Systems Need Domain-Specific Languages

todo: 정리

좋은 DSL은 도메인 영역의 개념과 실제 구현될 코드 사이의 "소통의 간극"을 줄여 도메인 영역을 코드 구현으로 번역하는 데에 오역을 줄여준다. DSL을 효율적으로 사용하면 코드 덩어리와 디자인 패턴의 추상도를 높여 주며 그에 따라 코드의 의도를 적절한 추상화 레벨에서 표현할 수 있게 해준다.

    DSL은 "모든 단계에서의 추상화"와 "모든 도메인의 POJO화"를 고차원적 규칙과 저차원적 디테일 전반에 걸쳐 도와 준다.

## Conclusion

- System도 clean하고 의도를 명확하게 표현해야 함. 침투적인 architecture ㄴㄴ해.
- 어떤 것을 디자인 하든 동작하는 범위에서 가장 간단한 것을 사용해라.
