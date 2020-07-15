# Database

- [Database](#database)
  - [Why](#why)
  - [DB Performance](#db-performance)
  - [Logical Design](#logical-design)
    - [Normalization](#normalization)
    - [Entity-Relational Model](#entity-relational-model)
  - [Physical Design](#physical-design)
    - [Index](#index)
  - [Join](#join)
    - [Cross Join](#cross-join)
    - [Natural Join](#natural-join)
    - [Inner Join](#inner-join)
    - [Outer Join](#outer-join)
      - [Left Outer Join](#left-outer-join)
      - [Right Outer Join](#right-outer-join)
    - [Inner Join vs Outer Join](#inner-join-vs-outer-join)
  - [Transaction](#transaction)
    - [Transaction Status](#transaction-status)
    - [Transaction Deadlock](#transaction-deadlock)
    - [Lock vs Transaction](#lock-vs-transaction)
  - [Statement vs PreparedStatement](#statement-vs-preparedstatement)
  - [Replication vs Clustering](#replication-vs-clustering)
  - [NoSQL](#nosql)
    - [CAP](#cap)
  - [References](#references)

---

## Why

- 데이터를 파일로 관리하면 update를 해야 하는 경우 여러 PC에서 업데이트를 해야 하는 등 어려움이 있음. 데이터를 한곳에 모아서 이를 해결하자!
- 데이터를 한곳에 모운 것을 database. 이를 관리하는 프로그램을 DBMS (DataBase Management System)라고 함

## DB Performance

- 데이터를 읽으려면 결국 디스크를 뒤져야 하는데 디스크 원판을 돌리는 시간이 좀 걸림. 그래서 순차 Access가 빠름
- 근데 현실에서는 다 Random I/O. Random I/O를 순차 I/O를 바꿀 수 없을까? 이게 쿼리 튜닝의 시작.

[위로](#Database)

## Logical Design

데이터의 중복을 제거해서 저장하기 논리적으로 설계하는 과정.

### Normalization

![normalization](./img/normalization.jpeg)
![bcnf](./img/bcnf.jpeg)

- 중복을 최소화하게 데이터를 구조화하는 프로세스. 주로 함수적 종속으로 정의.
  - 함수적 종속 : X 와 Y가 속성집합일 때, X 값이 Y 값을 유일하게 결정하는 경우
  - 1NF : 모든 속성의 도메인이 원자 값으로만 구성
    - 해결방안 : 그냥 속성에 ',' 같은거 안넣음
  - 2NF : 1NF이면서 기본키의 일부에 종족적인 속성이 없는 경우 (eg. (X,Y)->Z일 때 X->Z가 없는 경우)
    - 해결방안 : 기본키의 일부에 종속적인 속성들을 다른 테이블로 분리
  - 3NF
    - 2NF이면서 이행적 함수 종속이 없는 경우를 말하는데 이행적 함수 종속(Transitive Functional Dependency)이란 X가 primary key일 때 X->Y이고, Y->Z라서 X->Z가 되는 경우를 의미. 3NF는 여기서 Y가 Primary attribute인 경우는 제외
    - 해결방안 : Y->Z를 다른 테이블로 분리
  - BCNF : 3NF에서 Y가 primary attribute일 경우를 제외한다는 조건을 뺌
- 장점 : 중복 제거
- 단점 : query를 할 때 join을 해야 함. 그래서 query리 성능 저하가 심하게 발생하면 비정규화(De-normalization)를 하기도 함

### Entity-Relational Model

![batch-schema](./img/batch-schema.png)

[위로](#Database)

## Physical Design

스키마를 물리적으로 구현하는 과정

### Index

- Column의 값과 해당 record가 저장된 주소를 미리 저장해 둬서 검색 속도를 올리는 자료구조
- 장점 : index된 값으로 검색하면 속도가 빠름
- 단점 : 데이터를 추가할 시 Index도 같이 업데이트 시켜줘야 함
- 키에 따른 분류
  - Primary index : 기본키를 포함하는 것으로 각 테이블당 하나만 생성
  - Secondary index : Primary index이외의 것
- 저장 상태에 따른 분류
  - Clustered Index
    - Primary Key에만 적용 가능한 것으로 물리적으로 키값이 비슷한 record끼리 묶어서 저장하는 것
    - 장점 : 비슷한 값들을 묶어서 저장하기 때문에 검색시 속도가 빠름
    - 단점 : Primary key를 수정하는 경우 물리적으로 실제 저장되어야 하는 위치가 변경
  - Non-clustered Index
    - 물리적으로 묶어서 저장히지 않는것
    - 장점 : Primary key를 수정하는 경우 물리적으로 수정 안해도 됨
    - 단점 : Clustered index에 비해 느림

[위로](#Database)

## Join

### Cross Join

Cartesin Product

```sql
-- explicit notation
SELECT *
FROM employees
  CROSS JOIN dept_emp;

-- implicit notation
SELECT *
FROM employees, dept_emp;
```

![cross-join](./img/cross-join.png)

### Natural Join

같은 이름을 가진 컬럼은 한 번만 추출

```sql
-- 명시적 표현법 (explicit notation)
SELECT *
FROM employees NATURAL JOIN dept_emp;
```

![natural-join](./img/natural-join.png)

### Inner Join

조건문을 만족시키는 행만 표시

```sql
-- explicit notation
SELECT *
FROM employees
  INNER JOIN dept_emp
    ON employees.emp_no = dept_emp.emp_no;

-- implicit notation
SELECT *
FROM employees, dept_emp
WHERE employees.emp_no = dept_emp.emp_no;
```

![inner-join](./img/inner-join.png)

### Outer Join

조건문에 만족시키지 않는 행도 표시

#### Left Outer Join

```sql
SELECT *
FROM table1
  LEFT OUTER JOIN table2
    ON table1.n = table2.n;
```

![left-outer-join](./img/left-outer-join.png)

#### Right Outer Join

```sql
SELECT *
FROM table1
  RIGHT OUTER JOIN table2
    ON table1.n = table2.n;
```

![right-outer-join](./img/right-outer-join.png)

### Inner Join vs Outer Join

Innter Join은 조건문에 만족시키는 것만 보여주는 반면에 Outer Join은 만족시키지 않는 부분도 보여줌

[위로](#database)

## Transaction

- All or Nothing. 모두 완벽하게 처리하거나 처리하지 못할 경우에는 원 상태로 복구해서 일부만 적용되는 현상이 발생하지 않게 방지
- ACID의 성질이 있다
  - Atomicity (원자성) : 만약 트랜잭션 중간에 어떠한 문제가 발생한다면 어떠한 작업 내용도 수행되어서는 안됨
  - Consistency (일관성) : 트랜잭션이 완료된 다음의 상태에서도 데이터의 일관성을 보장해야 함
  - Isolation (고립성) : 각각의 트랜잭션은 서로 간섭없이 독립적으로 수행
  - Durability (지속성) : 트랜잭션이 종료된 후 영구적으로 결과가 저장되어야 함
- 장점 : All or nothing이라서 한개가 에러가 발생하면 rollback함
- 단점 : db connection수는 한정되어 있기 때문에 과도하게 사용하면 collection pool이 남아나질 않음

### Transaction Status

![transaction-status](./img/transaction-status.png)

- Active : 활동 상태
- Partial Committed
  - 트랜잭션의 `Commit` 명령이 도착한 상태. `commit`이전 `sql`문이 수행되고 `commit`만 남은 상태
  - `Commit`을 문제없이 수행할 수 있으면 `Committed` 상태로 전이, 오류가 발생하면 `Failed` 상태가 됨
- Committed : Commit 완료
- Failed : Commit 실패
- Aborted : 트랜잭션이 취소되고 Rollback된 상태

### Transaction Deadlock

- Transaction 1이 table B에 insert하고 Transaction 2가 table A에 insert. 이 때 Transaction1은 B에 대한 lock을 소유, Transaction2는 A에 대한 Lock을 소유
- 그러고 Transaction 1이 table A에 insert하고 Transaction 2가 table B에 insert하면 lock을 얻지 못해서 deadlock 발생

```SQL
Transaction 1> start transaction; insert into B values(1);
Transaction 2> start transaction; insert into A values(1);

Transaction 1> insert into A values(1);
Transaction 2> insert into B values(1);

ERROR 1213 (40001): Deadlock found when trying to get lock; try restarting transaction
```

### Lock vs Transaction

- Lock
  - 동시성을 제어하는 기술
- Transaction
  - 데이터의 정합성을 보장하는 기술
  - transaction을 수행할 때 lock을 걸기도 함

## Statement vs PreparedStatement

- Statement
  - 매번 컴파일을 해서 실행
- PreparedStatement
  - 한번만 컴파일을 한 후 값을 변경해서 재사용
  - SQL Injection도 방지해 주고 더 빨라서 쓰는 것을 권장

[위로](#database)

## Replication vs Clustering

- Replication
  - Master-slave model로 비동기식으로 단순 복제를 함
  - 장점 : 복제 속도가 빠름
  - 단점 : Auto Failover 기능이 없어서 사용자가 직접 해줘야 함
- Clustering
  - 동기식으로 서로의 데이터를 복제
  - 장점 : Auto Failover가 됨
  - 단점 : 복제 속도가 느림

[위로](#database)

## NoSQL

- RDB는 현대의 3V (high-volume, high-velocity, and high-variety)의 데이터 시대에는 맞지 않음. RDB를 사용하면서 성능을 올리려면 Scale-up을 해야함. But 이는 비용이 많이 듬. 그래서 Scala-out을 해야 함
- 요즘 시대에는 요구사항이 계속 바뀌어서 rdb의 엄격한 스키마 구조가 좋지 않음
- 이것들을 해결하고자 분삭 데이터베이스 기반으로 하는 느슨한 스키마를 제공하는 것임 NoSQL

### CAP

- 데이터베이스를 분산해서 관리하면 다음의 3개 중 2개 초과를 보장할 수 없음
  - Consistency : 분산된 데이터 간 동일한 시간에 조회하면 동일한 데이터를 얻는 것
  - Availability : 항상 이용가능한 것
  - Partition Tolerance  : 네트워크 장애가 발생해도 시스템이 정상적으로 운영되어야 함
- Partition Tolerance를 보장하면서
  - Concsistency를 보장하려면 네트워크 장애가 발생하면 기다려야함 (Availability 불만족)
  - Availability를 보장하려면 네트워크 장애가 발생해도 동작해야함. 그 서버에는 값이 다를 수 있음 (Consistency 불만족)
- 그렇다고 Partition Tolerance를 버리면 분산 하는 의미가 없음

[위로](#database)

## References

Common

https://github.com/JaeYeopHan/Interview_Question_for_Beginner/blob/master/Database

CAP Theorm

https://en.wikipedia.org/wiki/CAP_theorem

Index

https://chartworld.tistory.com/18

Normalization

https://yaboong.github.io/database/2018/03/09/database-normalization-1/

https://magok-leaders-coding.tistory.com/4

Join

https://advenoh.tistory.com/23

NoSQL

https://www.alachisoft.com/nosdb/why-nosql.html

https://www.techrepublic.com/blog/the-enterprise-cloud/migrating-from-a-relational-to-a-nosql-cloud-database/

https://nosql.mypopescu.com/post/540412780/from-mysql-to-mongodb-migration-steps