# Sql

- [Sql Basic](#sql-basic)
  - [Comments](#comments)
- [DDL](#ddl)
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
- [DQL](#dql)
  - [Select](#select)
  - [Sorting](#sorting)
    - [Order By](#order-by)
  - [Filtering](#filtering)
    - [Distinct](#distinct)
    - [Limit](#limit)
    - [Fetch](#fetch)
    - [Where](#where)
    - [Comparison](#comparison)
    - [Logical](#logical)
  - [Conditional Expression](#conditional-expression)
    - [Case](#case)
  - [Join](#join)
    - [Alias](#alias)
    - [Inner Join](#inner-join)
    - [Left Outer Join](#left-outer-join)
    - [Right Outer Join](#right-outer-join)
    - [Full Outer Join](#full-outer-join)
    - [Cross Join](#cross-join)
    - [Self Join](#self-join)
  - [Aggregation](#aggregation)
    - [Avg](#avg)
    - [Count](#count)
    - [MAX](#max)
    - [MIN](#min)
    - [SUM](#sum)
  - [Grouping](#grouping)
    - [Group By](#group-by)
    - [Having](#having)
    - [Groupoing Sets](#groupoing-sets)
    - [Rollup](#rollup)
    - [Cube](#cube)
  - [Set](#set)
    - [Union](#union)
    - [Union All](#union-all)
    - [Intersect](#intersect)
    - [Minus](#minus)
  - [SubQuery](#subquery)
    - [SubQuery](#subquery-1)
    - [Correlated SubQuery](#correlated-subquery)
    - [Exists](#exists)
    - [All](#all)
    - [Any](#any)
- [DCL](#dcl)
- [References](#references)

## Sql Basic

- Structured Query Language.
- 관계형 데이터베이스에서 데이터를 관리하기 위한 언어.
- declarative languag라서 자연어랑 유사함.
- ISO Standard임. But 부족한 feature가 있어서 vender사마다 자기들만의 SQL Dialect가 있음.
- [practice](https://www.sqltutorial.org/seeit/)

### Comments

```sql
/* i am comment */

-- i am comment
```

## DDL

- Data Definition Language.

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

### Alter Table

### Drop Table

### Truncate Table

### Constraints

#### Primary Key

#### Foreign Key

#### Unique

#### Not Null

#### Check

### View

## DML

- Data Manupulation Language.

### Insert

### Update

### Delete

## DQL

- Data Query Languag.

### Select

Select all columns.

```sql
SELECT * FROM employees;
```

Select specific columns.

```sql
SELECT 
    employee_id, 
    first_name, 
    last_name, 
    hire_date
FROM
    employees;
```

Select & simple calculation.

```sql
SELECT 
    first_name, 
    last_name, 
    salary, 
    salary * 1.05
FROM
    employees;
```

Select & simple calculation and set alias.

```sql
SELECT 
    first_name, 
    last_name, 
    salary, 
    salary * 1.05 AS new_salary
FROM
    employees;
```

### Sorting

#### Order By

- FROM -> SELECT -> ORDER BY 순으로 수행됨.
- ASC | DESC인데 지정 안하면 ASC.

Order by first_name in asc.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    hire_date,
    salary
FROM
    employees
ORDER BY
    first_name;
```

Order by salary in desc.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    hire_date,
    salary
FROM
    employees
ORDER BY
    salary DESC;
```

Order by multiple columns.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    hire_date,
    salary
FROM
    employees
ORDER BY
    first_name,
    last_name DESC;
```

### Filtering

#### Distinct

#### Limit

#### Fetch

#### Where

#### Comparison

Equal

Not equal to

Greater then

Greater then of equal to

Less then

Less than or equal to

#### Logical

ALL

AND

ANY

BETWEEN

EXISTS

IN

LIKE

NOT

OR

SOME

### Conditional Expression

#### Case

### Join

#### Alias

#### Inner Join

#### Left Outer Join

#### Right Outer Join

#### Full Outer Join

#### Cross Join

#### Self Join

### Aggregation

#### Avg

#### Count

#### MAX

#### MIN

#### SUM

### Grouping

#### Group By

#### Having

#### Groupoing Sets

#### Rollup

#### Cube

### Set

#### Union

#### Union All

#### Intersect

#### Minus

### SubQuery

#### SubQuery

#### Correlated SubQuery

#### Exists

#### All

#### Any

## DCL

- Data Control Language.

## References

- [SQL (wiki)](https://en.wikipedia.org/wiki/SQL)
- [SQLBolt](https://sqlbolt.com/) 
- [SQLZOO](https://sqlzoo.net/wiki/)
- [w3school SQL](https://www.w3schools.com/sql)
- [SQL Tutorial](https://www.sqltutorial.org/)
- [sql-tutorial (javatpoint)](https://www.javatpoint.com/sql-tutorial)