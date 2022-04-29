# Database

- [Why](#why)
- [DB Performance](#db-performance)
- [Physical Design](#physical-design)
  - [Transaction Deadlock](#transaction-deadlock)
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

## Physical Design

스키마를 물리적으로 구현하는 과정

[위로](#Database)

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

Join

https://advenoh.tistory.com/23

NoSQL

https://www.alachisoft.com/nosdb/why-nosql.html

https://www.techrepublic.com/blog/the-enterprise-cloud/migrating-from-a-relational-to-a-nosql-cloud-database/

https://nosql.mypopescu.com/post/540412780/from-mysql-to-mongodb-migration-steps