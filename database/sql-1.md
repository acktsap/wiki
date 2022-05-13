# Sql

- [Sql Basic](#sql-basic)
  - [Comments](#comments)
- [DDL](#ddl)
  - [Sql Types](#sql-types)
    - [CHAR(n), VARCHAR(n)](#charn-varcharn)
    - [DECIMAL(p,s)](#decimalps)
    - [INT, BIGINT](#int-bigint)
    - [FLOAT(n)](#floatn)
    - [DATE, TIME, TIMESTAMP](#date-time-timestamp)
  - [Create Table](#create-table)
  - [Alter Table](#alter-table)
  - [Drop Table](#drop-table)
  - [Truncate Table](#truncate-table)
  - [Constraints](#constraints)
    - [Primary Key](#primary-key)
    - [Foreign Key](#foreign-key)
    - [Unique](#unique)
    - [Not Null](#not-null)
    - [Check](#check)
  - [View](#view)
- [DML](#dml)
  - [Insert](#insert)
  - [Update](#update)
  - [Delete](#delete)
- [References](#references)

## Sql Basic

- Structured Query Language.
- 관계형 데이터베이스에서 데이터를 관리하기 위한 언어.
- 대소문자 미구분.
- Declarative language 라서 자연어랑 유사함.
- ISO Standard임. But 부족한 feature가 있어서 vender사마다 자기들만의 SQL Dialect가 있음.
- [practice](https://www.sqltutorial.org/seeit/)

### Comments

```sql
/* i am comment */

-- i am comment
```

[Go to top](#sql)

## DDL

- Data Definition Language.

### Sql Types

#### CHAR(n), VARCHAR(n)

`CHAR(n)`

- Fixed length character. 더 짧은거 넣으려고 하면 space 로 채워서 저장함.
  ```sql
  column_name CHARACTER(5)
  ```
- 장점
  - string manipulation 할 때 길이 체크를 하지 않아도 되므로 index lookup이 `VARCHAR(n)`보다 빠름
- 단점
  - 공간 낭비가 발생할 수 있음.

`VARCHAR(n)`

- 최대 n길이의 가변길이 문자열. 더 짧은거 넣으려고 해도 남은 길이 padding 안하고 저장함.
  ```sql
  first_name VARCHAR(50)
  ```
- 장점
  - `CHAR(n)` 에 비해서 공간 낭비를 하지 않음.
- 단점
  - string manupulication이 항상 길이 체크를 해야하기 때문에 index lookup이 `CHAR(n)`보다 느림.

see also

- [char vs varchar performance](https://dba.stackexchange.com/questions/2640/what-is-the-performance-impact-of-using-char-vs-varchar-on-a-fixed-size-field)

#### DECIMAL(p,s)

- Store exact numeric values in the database.
- Precision of p digits, with n digits to the right of decimal point.

Define the salary column with 12 digits which include 4 digits after the decimal point.

```sql
salary DECIMAL (12,4)
```

#### INT, BIGINT

- 정수용. `BIGINT`는 범위가 더 넓은 정수.

#### FLOAT(n)

- 실수. n은 precision and scale of the floating point.

#### DATE, TIME, TIMESTAMP

`DATE`

- 'YYYY-DD-MM'
  ```sql
  '2020-12-31'
  ```

`TIME`

- 'HH:MM:SS'
  ```sql
  '10:59:30.9999'
  ```

`TIMESTAMP`

- Both DATE and TIME values.
  ```sql
  TIMESTAMP 'YYYY-MM-DD HH:MM:SS'
  ```

### Create Table

- 한 database 안에 unique한 table을 생성.

Create table with simple pk.

```sql
CREATE TABLE courses (
    course_id INTEGER PRIMARY KEY AUTOINCREMENT, -- db automatically increment id when inserted
    course_name VARCHAR(50) NOT NULL -- set as not null
);
```

Create table composite pk.

```sql
CREATE TABLE trainings (
    employee_id INTEGER,
    course_id INTEGER,
    taken_date DATE,
    PRIMARY KEY (employee_id , course_id) -- set composite key
);
```

[Go to top](#sql)

### Alter Table

[Go to top](#sql)

### Drop Table

[Go to top](#sql)

### Truncate Table

[Go to top](#sql)

### Constraints

[Go to top](#sql)

#### Primary Key

[Go to top](#sql)

#### Foreign Key

[Go to top](#sql)

#### Unique

[Go to top](#sql)

#### Not Null

[Go to top](#sql)

#### Check

[Go to top](#sql)

### View

[Go to top](#sql)

## DML

- Data Manupulation Language.

### Insert

[Go to top](#sql)

### Update

[Go to top](#sql)

### Delete

[Go to top](#sql)

## References

- [SQL Tutorial](https://www.sqltutorial.org/)
- [SQL (wiki)](https://en.wikipedia.org/wiki/SQL)
- [SQLBolt](https://sqlbolt.com/)
- [SQLZOO](https://sqlzoo.net/wiki/)
- [w3school SQL](https://www.w3schools.com/sql)
- [sql-tutorial (javatpoint)](https://www.javatpoint.com/sql-tutorial)
