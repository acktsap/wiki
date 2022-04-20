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
    - [Limit & Offset](#limit--offset)
    - [Fetch](#fetch)
    - [Where](#where)
    - [Comparison Operators](#comparison-operators)
    - [Logical Operators](#logical-operators)
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
    first_name, -- ASC
    last_name DESC;
```

### Filtering

#### Distinct

- `DISTINCT` 다음에 오는 colunm들을 기준으로 중복 제거해서 보여줌.
- `NULL`은 동일하게 취급해서 `NULL` 여러개 오는 경우 한개만 출력함.

Distinct by single column.

```sql
-- distinct by salary only
SELECT
    DISTINCT salary
FROM
    employees
ORDER BY salary DESC;
```

Distinct by two column.

```sql
-- distinct by (job_id, salary) pair
SELECT DISTINCT
    job_id,
    salary
FROM
    employees
ORDER BY
    job_id,
    salary DESC;
```

#### Limit & Offset

- `LIMIT` the number of rows returned by a select statement.
- `OFFSET` 지정하면 해당 row만큼 skip하고 시작함.
- `LIMIT` & `OFFSET` 은 sql 표준은 아님. (eg. oracle에서 미지원)

Select first 5 rows.

```sql
SELECT
    employee_id,
    first_name,
    last_name
FROM
    employees
ORDER BY
    first_name
LIMIT 5;
```

Select 5 rows starting from 4th row.

```sql
SELECT
    employee_id, first_name, last_name
FROM
    employees
ORDER BY first_name
LIMIT 5 OFFSET 3;
```

Select top 5 employees by salary.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
ORDER BY
    salary DESC
LIMIT 5;
```

MySql short form.

```sql
SELECT
    employee_id,
    first_name,
    last_name
FROM
    employees
ORDER BY
    first_name
LIMIT 3 , 5; -- LIMIT 5 OFFSET 3
```

Select 2nd highest salary in the company.

```sql
SELECT DISTINCT
    salary
FROM
    employees
ORDER BY salary DESC
LIMIT 1 , 1;
```

Select 2nd highest slave in the company.

```sql
SELECT
    employee_id, first_name, last_name, salary
FROM
    employees
WHERE
    salary = (
        SELECT DISTINCT
            salary
        FROM
            employees
        ORDER BY salary DESC
        LIMIT 1 , 1
    );
```

#### Fetch

- `OFFSET FETCH`는 `LIMIT`에 비해 많은 db에서 지원됨.
- 주로 pagination할 때 사용.

Select the first employee who has the highest salary.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM employees
ORDER BY
    salary DESC
OFFSET 0 ROWS -- skips 0 row
FETCH NEXT 1 ROWS ONLY; -- return 1 row only
```

Sorts the employees by salary, skips the first five employees with the highest salary, and fetches the next five ones.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM employees
ORDER BY
    salary DESC
OFFSET 5 ROWS
FETCH NEXT 5 ROWS ONLY;
```

#### Where

- row에 대한 조건을 지정. 조건에 대해서 `TRUE` 인 row만 result에 포함.

#### Comparison Operators

- `=` :	Equal
- `<>` : Not equal to
- `>`	: Greater than
- `>=` : Greater than or equal to
- `<`	: Less than
- `<=` : Less than or equal to

Equal

```sql
SELECT
    employee_id, first_name, last_name
FROM
    employees
WHERE
    last_name = 'Himuro';
```

Equal (is null)

```sql
SELECT
    employee_id, first_name, last_name, phone_number
FROM
    employees
WHERE
    phone_number IS NULL;

```

Not equal to

```sql
SELECT
    employee_id, first_name, last_name, department_id
FROM
    employees
WHERE
    department_id <> 8
    AND department_id <> 10
ORDER BY first_name , last_name;

```

Greater then

```sql
SELECT
    employee_id, first_name, last_name, salary
FROM
    employees
WHERE
    salary > 10000
ORDER BY salary DESC;
```

Greater then and equals to

```sql
SELECT
    employee_id, first_name, last_name, salary
FROM
    employees
WHERE
    salary > 10000 AND department_id = 8
ORDER BY salary DESC;
```

Greater then of equal to

```sql
SELECT
    employee_id, first_name, last_name, salary
FROM
    employees
WHERE
    salary >= 9000
ORDER BY salary;
```

Less then

```sql
SELECT
    employee_id, first_name, last_name, salary
FROM
    employees
WHERE
    salary < 10000
ORDER BY salary DESC;
```

Less than or equal to

```sql
SELECT
    employee_id, first_name, last_name, salary
FROM
    employees
WHERE
    salary <= 10000
ORDER BY salary DESC;
```

#### Logical Operators

- `AND` : Return true if both expressions are true.
- `OR` : Return true if either expression is true.
- `IS NULL` : Return true if it's null.
- `BETWEEN` : Return true if the operand is within a range.
- `IN` : Return true if the operand is equal to one of the value in a list.
- `LIKE` : Return true if the operand matches a pattern.
  - The percent sign (`%`) represents zero, one, or multiple characters.
  - The underscore sign (`_`) represents a single character.
- `ALL` :	Return true if all comparisons are true.
  - Must be preceded by a comparison operator and followed by a subquery.
- `ANY` : Return true if any one of the comparisons is true.
  -  Must be preceded by a comparison operator and followed by a subquery.
- `EXISTS` : Return true if a subquery contains any rows.
- `NOT` : Reverse the result of any other Boolean operator.
- `SOME` : Return true if some of the expressions are true.

AND

```sql
SELECT
    first_name, last_name, salary
FROM
    employees
WHERE
    salary > 5000 AND salary < 7000
ORDER BY salary;
```

OR

```sql
SELECT
    first_name, last_name, salary
FROM
    employees
WHERE
    salary = 7000 OR salary = 8000
ORDER BY salary;
```

IS NULL

```sql
SELECT
    first_name, last_name, phone_number
FROM
    employees
WHERE
    phone_number IS NULL
ORDER BY first_name , last_name;
```

BETWEEN

```sql
SELECT
    first_name, last_name, salary
FROM
    employees
WHERE
    salary BETWEEN 9000 AND 12000
ORDER BY salary;
```

IN

```sql
SELECT
    first_name, last_name, department_id
FROM
    employees
WHERE
    department_id IN (8, 9)
ORDER BY department_id;
```

LIKE

```sql
SELECT
    employee_id, first_name, last_name
FROM
    employees
WHERE
    first_name LIKE 'jo%' -- % : zero, one, or multiple characters
ORDER BY first_name;
```

ALL

```sql
SELECT
    first_name, last_name, salary
FROM
    employees
WHERE
    salary >= ALL (
        SELECT
            salary
        FROM
            employees
        WHERE
            department_id = 8
    )
ORDER BY salary DESC;
```

ANY

```sql
SELECT
    first_name, last_name, salary
FROM
    employees
WHERE
    salary > ANY (
        SELECT
            AVG(salary)
        FROM
            employees
        GROUP BY department_id
    )
ORDER BY first_name , last_name;
```

EXISTS

```sql
SELECT
    first_name, last_name
FROM
    employees e
WHERE
    EXISTS (
        SELECT
            1
        FROM
            dependents d
        WHERE
            d.employee_id = e.employee_id);
```

NOT

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    department_id = 5
AND NOT salary > 5000
ORDER BY
    salary;
```

NOT IN

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    department_id
FROM
    employees
WHERE
    department_id NOT IN (1, 2, 3)
ORDER BY
    first_name;
```

NOT LIKE

```sql
SELECT
    first_name,
    last_name
FROM
    employees
WHERE
    first_name NOT LIKE 'D%'
ORDER BY
    first_name;
```

NOT BETWEEN

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary NOT BETWEEN 3000 AND 5000
ORDER BY
    salary;
```

NOT EXISTS

```sql
SELECT
    employee_id,
    first_name,
    last_name
FROM
    employees e
WHERE
    NOT EXISTS (
        SELECT
            employee_id
        FROM
            dependents d
        WHERE
            d.employee_id = e.employee_id
    );
```

### Conditional Expression

#### Case

todo: https://www.sqltutorial.org/sql-case/

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

## References

- [SQL (wiki)](https://en.wikipedia.org/wiki/SQL)
- [SQLBolt](https://sqlbolt.com/)
- [SQLZOO](https://sqlzoo.net/wiki/)
- [w3school SQL](https://www.w3schools.com/sql)
- [SQL Tutorial](https://www.sqltutorial.org/)
- [sql-tutorial (javatpoint)](https://www.javatpoint.com/sql-tutorial)