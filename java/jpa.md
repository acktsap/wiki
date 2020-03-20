# JPA

- [JPA](#jpa)
  - [Problem of SQL Oriented Development](#problem-of-sql-oriented-development)
  - [Concept](#concept)
    - [How JPA works](#how-jpa-works)
  - [EntityManager](#entitymanager)
  - [Persistant Context](#persistant-context)
    - [Read](#read)
    - [Create](#create)
    - [Update](#update)
    - [Delete](#delete)
    - [Flush](#flush)
    - [Detached](#detached)
  - [References](#references)

## Problem of SQL Oriented Development

1. 지루한 코드의 무한 반복
    - 자바 객체를 SQL로, SQL을 자바 객체로 변환하는 과정의 반복
    - Ex) Field하나 추가 시 1. class에 추가, 2. INSERT, SELECT, UPDATE 등 관련된 모든 쿼리에 추가
2. OOP와 RDB간 패러다임 불일치
    - 상속
      - OOP : 있음
        - 객체의 저장 : `list.add(xxx)`
        - 객체의 조회 : `list.get(xxx)`
      - RDB : 없음. 비슷한 걸로 Table supertype-subtype이 있으나..
        ![jpa-rdb-super-sub-relation](./img/jpa-rdb-super-sub-relation.png)
        - 객체의 저장 : 객체를 분해한 후 각각 다른 테이블에 대해 INSERT를 두번 날림
        - 객체의 조회 : 두개의 테이블에 대해 Join을 하고 값에 대한 모든 필드값을 설정
    - Association
      ![jpa-rdb-association-relation](./img/jpa-rdb-association-relation.png)
      - OOP : Reference를 사용. 단방향으로만 조회가 가능 (`member.getTeam()`)
      - RDB : PK와 FK + Join을 사용해서 관계. 양방향으로 조회가 가능 (`JOIN ON M.TEAM_ID = T.TEAM_ID`)
    - 객체 그래프 탐색
      ![jpa-object-graph-searching](./img/jpa-object-graph-searching.png)
      - 객체는 자유롭게 객체간 연간관계를 탐색할 수 있음. But 앞서 말한 association의 문제 때문에
        query를 할 때 해당 객체에 대해서도 조회되었는지 알 수가 없음. 즉 `member.getTeam().getOrderItem()`
        이 항상 성공한다고 보장할 수 없음. 이를 Entity 신뢰의 문제라고 함.

이것들을 해결하려고 나온 것이 JPA

## Concept

- ORM(Object-Relational Mapping) 객체는 객체대로 설계하고 RDB는 RDB대로 설계하면 중간에서 이를 매핑해주는 녀석임
- JPA(Java Persistence API)는 Java진영의 ORM표준으로 EJB스타일의 ORM의 문제를 많이 해결한 것
- JPA는 스펙일 뿐임! 구현체로 Hibernate, EclipseLink, DataNucleus가 있음

### How JPA works

![jpa-basic-structure](./img/jpa-basic-structure.png)

Application과 JDBC사이에서 동작. 사용자가 JPA를 통해 요청을 하면 내부적으로 JDBC를 사용해서 SQL을 호출하는 식임.
쿼리를 JPA가 만들어주기 때문에 사용자는 신경 안써도 됨

![jpa-insert-structure](./img/jpa-insert-structure.png)

삽입할 경우

1. 개발자는 JPA에 Member 객체를 넘긴다.
2. JPA가 엔티티를 분석해서 적절한 INSERT SQL을 생성
3. JPA가 JDBC API를 사용하여 SQL을 DB에 날린다

![jpa-select-structure](./img/jpa-select-structure.png)

조회할 경우

1. 개발자는 member의 pk 값을 JPA에 넘긴다.
2. JPA가 엔티티를 분석해서 적절한 SELECT SQL을 생성
3. JPA가 JDBC API를 사용하여 SQL을 DB에 날린다.
4. JPA가 DB로부터 결과를 받아온다.
5. JPA가 결과(ResultSet)를 객체에 모두 매핑

## EntityManager

EntityManagerFactory  : EntityManager를 생성, 내부적으로 DB connection pool을 사용함.

```java
// application loading 시점에 DB 당 딱 하나만 생성되어야 한다.
EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

// do something...

// application이 종료되는 시점에 close
entityManagerFactory.close();
```

EntityManager : Transaction단위를 수행할 때 마다 생성하여 사용

```java
// Transaction수행 후 닫아야 DB connection을 반환함
entityManager.close();
```

EntityTransaction

- Data 를 “변경”하는 모든 작업은 반드시 Transaction 안에서 이루어져야 함. 단순한 조회의 경우는 상관없음.

```java
EntityTransaction tx = entityManager.getTransaction();
tx.begin();  // Transaction 시작
tx.commit();  // Transaction 수행
tx.rollback();  // 작업에 문제가 생겼을 시
```

## Persistant Context

![jpa-persistence-context](./img/jpa-persistence-context.png)

Entity를 영구히 저장하는 환경으로 User Application과 DB의 사이에 위치함. EntityManager를 하나 생성하면 1:1로 Persistant Context가 생성됨. Entity의 LifeCycle은 다음과 같다.

- new/transient (비영속) : 객체를 새로 생성한 것으로 Persistent Context와 관계가 없는 상태
- managed (영속) : Persistant Context에 저장된 상태
- detatched (준영속) : Persistant Context에서 지운 상태
- removed (삭제) : 실제 DB에 삭제를 요청한 상태

Persistant Context를 시용하면 buffering, caching 등의 이점이 있음. buffering의 경우 commit전까지 query를 Persistant Context에만 저장해 두고 나중에 요청하는 식으로 해서 가능. cache의 경우 query를 할 때 매번 db를 찾지 않아도 되서 그렇다.

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
entityManager.getTransaction().begin();

// 객체를 생성한 상태 (비영속)
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");

// 객체를 저장한 상태 (영속)
entityManager.persist(member);

// 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
entityManager.detach(member);

// 객체를 삭제한 상태
entityManager.remove(member);
```

### Read

Read : 조회 시 1차 cache를 통해 같은 key로 가져오면 같은 객체를 return함

```java
Member a = entityManager.find(Member.class, "member1");
Member b = entityManager.find(Member.class, "member1");
System.out.println(a == b); // 동일성 비교 true
```

### Create

Create : Transaction Write-Behind를 통해 commit 전까지는 실제 db에 요청을 하지 않음

```java
EntityManager entityManager = emf.createEntityManager();
EntityTransaction transaction = entityManager.getTransaction();
transaction.begin(); // Transaction 시작

entityManager.persist(memberA);
entityManager.persist(memberB);

// 커밋하는 순간 DB에 INSERT SQL을 보낸다.
transaction.commit(); // Transaction 커밋
```

### Update

Update : Dirty Checking을 통해 처음 읽어온 snapshot과 비교를 해서 다르면 전체 field를 업데이트 하는 query를 날림.

```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
transaction.begin(); // Transaction 시작

Member memberA = em.find(Member.class, "memberA"); // 조회

memberA.setUsername("hi"); // 수정
memberA.setAge(10);

// em.update(member) 또는 em.persist(member) 이런 코드 없어도 됨

transaction.commit(); // Transaction 커밋
```

### Delete

Delete : `em.remove`를 통해 삭제 요청을 할 수 있으나 역시 commit전까지는 반영이 안됨.

```java
transaction.begin(); // Transaction 시작

Member memberA = em.find(Member.class, "memberA");
em.remove(memberA); // 엔티티 삭제

transaction.commit(); // Transaction 커밋
```

### Flush

PersistContext의 변경 내용을 DB에 반영하는 것. 다음과 같이 동작함

1. Dirty Checking을 해서 수정된 Entity를 쓰기 지연 SQL저장소에록등록
2. 쓰기 지연 SQL을 DB에 전송

Flush하는 방법은

1. 직접 호출
2. transaction commit호출
3. JPQL 쿼리 실행시 자동 호출 : JPQL을 SQL로 번역이 되서 날라가는 것이기 때문에 강제로 sync를 맞춰줌

```java
Member member = new Member(200L, "A");
entityManager.persist(member);

entityManager.flush(); // 강제 호출 (쿼리가 DB 에 반영됨)

tx.commit();
```

```java
em.persist(memberA);
em.persist(memberB);
em.persist(memberC);

// JPQL 실행 : flush 호출
query = entityManager.createQuery("select m from Member m", Member.class);
// member a, b, c조회
List<Member> members = query.getResultList();
```

### Detached

detach나 clear를 하면 detach로 변경됨

```java
// 영속 상태 (Persistence Context 에 의해 Entity 가 관리되는 상태)
Member findMember = entityManager.find(Member.class, 150L);
findMember.setName("AAAAA"); // update

entityManager.detach(findMember); // 영속성 컨텍스트에서 떼넨다. (더 이상 JPA 의 관리 대상이 아님.)

tx.commit(); // DB에 insert query 가 날라가는 시점 (아무 일도 발생하지 않음.)
```

```java
// 영속 상태 (Persistence Context 에 의해 Entity 가 관리되는 상태)
Member findMember = entityManager.find(Member.class, 150L);
findMember.setName("AAAAA"); // update

entityManager.clear(); // 영속성 컨텍스트를 완전히 초기화

Member findMember2 = entityManager.find(Member.class, 150L); // 간은 Entity 를 다시 조회

tx.commit(); // DB에 insert query 가 날라가는 시점 (아무일도 발생하지 않음.)
```

## References

https://gmlwjd9405.github.io/2019/08/03/reason-why-use-jpa.html

https://gmlwjd9405.github.io/2019/08/04/what-is-jpa.html

https://gmlwjd9405.github.io/2019/08/06/persistence-context.html

https://gmlwjd9405.github.io/2019/08/07/what-is-flush.html

https://gmlwjd9405.github.io/2019/08/08/jpa-entity-lifecycle.html