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

[Go to top](#sql)

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

## DQL

- Data Query Languag.

### Select

- 골라골라~ `SELECT`

To select all the rows and columns in the employees table, use `SELECT`.

```sql
SELECT * FROM employees;
```

```text
+-------------+-------------+-------------+-----------------------------------+--------------+------------+--------+----------+------------+---------------+
| employee_id | first_name  | last_name   | email                             | phone_number | hire_date  | job_id | salary   | manager_id | department_id |
+-------------+-------------+-------------+-----------------------------------+--------------+------------+--------+----------+------------+---------------+
|         100 | Steven      | King        | steven.king@sqltutorial.org       | 515.123.4567 | 1987-06-17 |      4 | 24000.00 |       NULL |             9 |
|         101 | Neena       | Kochhar     | neena.kochhar@sqltutorial.org     | 515.123.4568 | 1989-09-21 |      5 | 17000.00 |        100 |             9 |
|         102 | Lex         | De Haan     | lex.de haan@sqltutorial.org       | 515.123.4569 | 1993-01-13 |      5 | 17000.00 |        100 |             9 |
|         103 | Alexander   | Hunold      | alexander.hunold@sqltutorial.org  | 590.423.4567 | 1990-01-03 |      9 |  9000.00 |        102 |             6 |
...
```

To select employee id, first name, last name, and hire date of all rows in the employees table, use column name.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    hire_date
FROM
    employees;
```

```sql
+-------------+-------------+-------------+------------+
| employee_id | first_name  | last_name   | hire_date  |
+-------------+-------------+-------------+------------+
|         100 | Steven      | King        | 1987-06-17 |
|         101 | Neena       | Kochhar     | 1989-09-21 |
|         102 | Lex         | De Haan     | 1993-01-13 |
|         103 | Alexander   | Hunold      | 1990-01-03 |
...
```

To select first name, last name, salary, and new salary using simple calculation.

```sql
SELECT
    first_name,
    last_name,
    salary,
    salary * 1.05
FROM
    employees;
```

```text
+-------------+-------------+----------+---------------+
| first_name  | last_name   | salary   | salary * 1.05 |
+-------------+-------------+----------+---------------+
| Steven      | King        | 24000.00 |    25200.0000 |
| Neena       | Kochhar     | 17000.00 |    17850.0000 |
| Lex         | De Haan     | 17000.00 |    17850.0000 |
| Alexander   | Hunold      |  9000.00 |     9450.0000 |
| Nancy       | Greenberg   | 12000.00 |    12600.0000 |
...
```

To assign new salary a new column, use `AS`.
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

```text
+-------------+-------------+----------+------------+
| first_name  | last_name   | salary   | new_salary |
+-------------+-------------+----------+------------+
| Steven      | King        | 24000.00 | 25200.0000 |
| Neena       | Kochhar     | 17000.00 | 17850.0000 |
| Lex         | De Haan     | 17000.00 | 17850.0000 |
| Nancy       | Greenberg   | 12000.00 | 12600.0000 |
...
```

[Go to top](#sql)

### Sorting

#### Order By

- `FROM` -> `SELECT` -> `ORDER BY` 순으로 수행됨.
- `ASC` 또는 `DESC`인데 지정 안하면 `ASC`.

To sort employees by first names in alphabetical order, use `ORDER BY`.

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

```text
+-------------+-------------+-------------+------------+----------+
| employee_id | first_name  | last_name   | hire_date  | salary   |
+-------------+-------------+-------------+------------+----------+
|         121 | Adam        | Fripp       | 1997-04-10 |  8200.00 |
|         115 | Alexander   | Khoo        | 1995-05-18 |  3100.00 |
|         103 | Alexander   | Hunold      | 1990-01-03 |  9000.00 |
|         193 | Britney     | Everett     | 1997-03-03 |  3900.00 |
|         104 | Bruce       | Ernst       | 1991-05-21 |  6000.00 |
|         179 | Charles     | Johnson     | 2000-01-04 |  6200.00 |
...
```

To sort the employees by the first name in ascending order and the last name in descending order, use and `DESC`

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

```text
+-------------+-------------+-------------+------------+----------+
| employee_id | first_name  | last_name   | hire_date  | salary   |
+-------------+-------------+-------------+------------+----------+
|         121 | Adam        | Fripp       | 1997-04-10 |  8200.00 |
|         115 | Alexander   | Khoo        | 1995-05-18 |  3100.00 |
|         103 | Alexander   | Hunold      | 1990-01-03 |  9000.00 |
|         193 | Britney     | Everett     | 1997-03-03 |  3900.00 |
|         104 | Bruce       | Ernst       | 1991-05-21 |  6000.00 |
|         179 | Charles     | Johnson     | 2000-01-04 |  6200.00 |
...
```

To sort employees by salary from high to low, use `DESC`.

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

```text
+-------------+-------------+-------------+------------+----------+
| employee_id | first_name  | last_name   | hire_date  | salary   |
+-------------+-------------+-------------+------------+----------+
|         100 | Steven      | King        | 1987-06-17 | 24000.00 |
|         101 | Neena       | Kochhar     | 1989-09-21 | 17000.00 |
|         102 | Lex         | De Haan     | 1993-01-13 | 17000.00 |
|         145 | John        | Russell     | 1996-10-01 | 14000.00 |
|         108 | Nancy       | Greenberg   | 1994-08-17 | 12000.00 |
...
```

[Go to top](#sql)

### Filtering

todo: 쿼리 정리해야 함

#### Distinct

- `DISTINCT` 다음에 오는 colunm들을 기준으로 중복 제거해서 보여줌.
- `NULL`은 동일하게 취급해서 `NULL` 여러개 오는 경우 한개만 출력함.

To select salary value from the salary.

```sql
SELECT 
    salary
FROM
    employees
ORDER BY salary DESC;
```

```text
+----------+
| salary   |
+----------+
| 24000.00 |
| 17000.00 |
| 17000.00 |
| 14000.00 |
| 13500.00 |
| 13000.00 |
...
```

To select unique values from the salary, use `DISTINCT`.

```sql
SELECT
    DISTINCT salary
FROM
    employees
ORDER BY salary DESC;
```

```text
+----------+
| salary   |
+----------+
| 24000.00 |
| 17000.00 |
| 14000.00 |
| 13500.00 |
| 13000.00 |
...
```

To selects the job id and salary.

```sql
SELECT
	job_id,
	salary
FROM
	employees
ORDER BY
	job_id,
	salary DESC;
```

```text
+--------+----------+
| job_id | salary   |
+--------+----------+
|      1 |  8300.00 |
|      2 | 12000.00 |
|      3 |  4400.00 |
|      4 | 24000.00 |
|      5 | 17000.00 |
|      5 | 17000.00 |
|      6 |  9000.00 |
...
```

To selects the job id and salary without duplication, use `DISTINCT`.

```sql
SELECT DISTINCT
    job_id,
    salary
FROM
    employees
ORDER BY
    job_id,
    salary DESC;
```

```text
+--------+----------+
| job_id | salary   |
+--------+----------+
|      1 |  8300.00 |
|      2 | 12000.00 |
|      3 |  4400.00 |
|      4 | 24000.00 |
|      5 | 17000.00 |
|      6 |  9000.00 |
...
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

[Go to top](#sql)

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

- `=` :    Equal
- `<>` : Not equal to
- `>`    : Greater than
- `>=` : Greater than or equal to
- `<`    : Less than
- `<=` : Less than or equal to

Equal

```sql
SELECT
    employee_id,
    first_name,
    last_name
FROM
    employees
WHERE
    last_name = 'Himuro';
```

Equal (is null)

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    phone_number
FROM
    employees
WHERE
    phone_number IS NULL;

```

Not equal to

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    department_id
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
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary > 10000
ORDER BY salary DESC;
```

Greater then and equals to

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary > 10000 AND department_id = 8
ORDER BY salary DESC;
```

Greater then of equal to

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary >= 9000
ORDER BY salary;
```

Less then

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary < 10000
ORDER BY salary DESC;
```

Less than or equal to

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary <= 10000
ORDER BY salary DESC;
```

[Go to top](#sql)

#### Logical Operators

- `AND` : Return true if both expressions are true.
- `OR` : Return true if either expression is true.
- `IS NULL` : Return true if it's null.
- `BETWEEN` : Return true if the operand is within a range.
- `IN` : Return true if the operand is equal to one of the value in a list.
- `LIKE` : Return true if the operand matches a pattern.
  - The percent sign (`%`) represents zero, one, or multiple characters.
  - The underscore sign (`_`) represents a single character.
- `ALL` :    Return true if all comparisons are true.
  - Must be preceded by a comparison operator and followed by a subquery.
- `ANY` : Return true if any one of the comparisons is true.
  -  Must be preceded by a comparison operator and followed by a subquery.
- `EXISTS` : Return true if a subquery contains any rows.
- `NOT` : Reverse the result of any other Boolean operator.
- `SOME` : Return true if some of the expressions are true.

AND

```sql
SELECT
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary > 5000 AND salary < 7000
ORDER BY salary;
```

OR

```sql
SELECT
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary = 7000 OR salary = 8000
ORDER BY salary;
```

IS NULL

```sql
SELECT
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    phone_number IS NULL
ORDER BY first_name , last_name;
```

BETWEEN

```sql
SELECT
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary BETWEEN 9000 AND 12000
ORDER BY salary;
```

IN

```sql
SELECT
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    department_id IN (8, 9)
ORDER BY department_id;
```

LIKE

```sql
SELECT
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    first_name LIKE 'jo%' -- % : zero, one, or multiple characters
ORDER BY first_name;
```

ALL

```sql
SELECT
    first_name,
    last_name,
    salary
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
    first_name,
    last_name,
    salary
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
            d.employee_id = e.employee_id
    );
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
ORDER BY salary;
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

[Go to top](#sql)

### Conditional Expression

#### Case

- Allows you to evaluate a list of conditions and returns one of the possible results.
- `ELSE` is optional. If no `ELSE` & expression does not match any of expression, returns `NULL`.

To get the work anniversaries of employees, use simple `CASE`.

```sql
SELECT 
    first_name,
    last_name,
    hire_date,
    CASE (2000 - YEAR(hire_date))
        WHEN 1 THEN '1 year'
        WHEN 3 THEN '3 years'
        WHEN 5 THEN '5 years'
        WHEN 10 THEN '10 years'
        WHEN 15 THEN '15 years'
        WHEN 20 THEN '20 years'
        WHEN 25 THEN '25 years'
        WHEN 30 THEN '30 years'
    END aniversary
FROM
    employees
ORDER BY first_name;
```

To get salary by three cagegory, use searched `CASE`.

```sql
SELECT 
    first_name,
    last_name,
    CASE
        WHEN salary < 3000 THEN 'Low'
        WHEN salary >= 3000 AND salary <= 5000 THEN 'Average'
        WHEN salary > 5000 THEN 'High'
    END evaluation
FROM
    employees;
```

[Go to top](#sql)

### Join

#### Alias

- SQL alias allows you to assign a table or a column a temporary name during the execution of a query.
- Two types of aliases: table and column aliases.
- `FROM` > `WHERE` > `SELECT` > `ORDER BY` 순서로 조회를 하기 때문에 alias 이름을 where 조건에 쓸 수 없음. 하지만 order by에서는 쓸 수 있음.

Column aliases.

```sql
SELECT
    inv_no AS invoice_no,
    amount,
    due_date AS 'Due date',
    cust_no 'Customer No' -- 없어도 되긴 함
FROM
    invoices;
```

Alias for expression.

```sql
SELECT 
    first_name, 
    last_name, 
    salary * 1.1 AS new_salary
FROM
    employees;
```

Using alias in where (error).

```sql
SELECT 
    first_name, 
    last_name, 
    salary * 1.1 AS new_salary
FROM
    employees
WHERE
    new_salary > 5000; -- error
```

Using alias in order by (not error)

```sql
SELECT 
    first_name, 
    last_name, 
    salary * 1.1 AS new_salary
FROM
    employees
ORDER BY new_salary;
```

Table alias.

```sql
SELECT 
    e.first_name, 
    e.last_name
FROM
    employees AS e;
```

[Go to top](#sql)

#### Inner Join

![inner-join](./img/sql-inner-join.png)

- Select statement는 여러 table의 값들을 link해서 조회할 수 있음. 이를 Join이라고 부름.
- Inner join은 겹치는거를 출력.

Inner join on two tables.

```sql
SELECT 
    first_name,
    last_name,
    employees.department_id,
    departments.department_id,
    department_name
FROM
    employees
    INNER JOIN departments
        ON departments.department_id = employees.department_id
WHERE
    employees.department_id IN (1 , 2, 3);
```

![inner-join-result1](./img/sql-inner-join-result1.png)

Inner join on three tables.

```sql
SELECT
    first_name,
    last_name,
    job_title,
    department_name
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
    INNER JOIN jobs j
        ON j.job_id = e.job_id
WHERE
    e.department_id IN (1, 2, 3);
```

![inner-join-result2](./img/sql-inner-join-result2.png)

Explicit notation.

```sql
SELECT
    *
FROM
    employees
    INNER JOIN dept_emp
        ON employees.emp_no = dept_emp.emp_no;
```

Implicit notation.

```sql
SELECT
    *
FROM 
    employees, dept_emp
WHERE 
    employees.emp_no = dept_emp.emp_no;
```

[Go to top](#sql)

#### Left Outer Join

![left-outer-join](./img/sql-left-outer-join.png)

- Right table의 값이 없어도 Left table은 다 출력.

Left outer join on two table.

```sql
SELECT
    c.country_name,
    c.country_id,
    l.country_id,
    l.street_address,
    l.city
FROM
    countries c
    LEFT OUTER JOIN locations l
        ON l.country_id = c.country_id
WHERE
    c.country_id IN ('US', 'UK', 'CN');
```

![left-outer-join-result1](./img/sql-left-outer-join-result1.png)

Left outer join on three tables.

```sql
SELECT
    r.region_name,
    c.country_name,
    l.street_address,
    l.city
FROM
    regions r
    LEFT OUTER JOIN countries c
        ON c.region_id = r.region_id
    LEFT OUTER JOIN locations l
        ON l.country_id = c.country_id
WHERE
    c.country_id IN ('US', 'UK', 'CN');
```

![left-outer-join-result2](./img/sql-left-outer-join-result2.png)

[Go to top](#sql)

#### Right Outer Join

- Left outer join과 동일한데 방향만 다름.

[Go to top](#sql)

#### Full Outer Join

![full-outer-join](./img/sql-full-outer-join.png)

- Left outer join + right outer join .
- 어느 한쪽에 매칭되는 데이터가 없어도 다 출력.

Full outer join.

```sql
INSERT INTO baskets (basket_id, basket_name)
VALUES
    (1, 'A'),
    (2, 'B'),
    (3, 'C');

INSERT INTO fruits (
    fruit_id,
    fruit_name,
    basket_id
)
VALUES
    (1, 'Apple', 1),
    (2, 'Orange', 1),
    (3, 'Banana', 2),
    (4, 'Strawberry', NULL);
```

```sql
SELECT
    basket_name,
    fruit_name
FROM
    fruits
    FULL OUTER JOIN baskets
        ON baskets.basket_id = fruits.basket_id;
```

```text
 basket_name | fruit_name
-------------+------------
 A           | Apple
 A           | Orange
 B           | Banana
 (null)      | Strawberry
 C           | (null)
```

[Go to top](#sql)

#### Cross Join

![cross-join](./img/cross-join.png)

- Make a Cartesian product of the joined tables.
- 다른 Join과는 다르게 Join 조건이 없음.

```sql
INSERT INTO sales_organization (sales_org_id, sales_org)
VALUES
    (1, 'Domestic'),
    (2, 'Export');

INSERT INTO sales_channel (channel_id, channel)
VALUES
    (1, 'Wholesale'),
    (2, 'Retail'),
    (3, 'eCommerce'),
    (4, 'TV Shopping');
```

Cross join.

```sql
SELECT
    sales_org,
    channel
FROM
    sales_organization
CROSS JOIN sales_channel; 
```

Cross join (without keyword).

```sql
SELECT
    sales_org,
    channel
FROM
    sales_organization,
    sales_channel;
```

![cross-join-result](./img/sql-cross-join-result.png)

[Go to top](#sql)

#### Self Join

- 필요에 따라 자기 자신과 Join하는 것. 주로 Inner join이나 Left outer join 사용.

Joins the employees table to itself to query the information of who reports to whom.

```sql
SELECT 
    e.first_name || ' ' || e.last_name AS employee,
    m.first_name || ' ' || m.last_name AS manager
FROM
    employees e
    INNER JOIN employees m
        ON m.employee_id = e.manager_id
ORDER BY manager;
```

![self-join-result1](./img/sql-self-join-result1.png)

Joins the employees table to itself to query the information of who reports to whom including no manager.

```sql
SELECT 
    e.first_name || ' ' || e.last_name AS employee,
    m.first_name || ' ' || m.last_name AS manager
FROM
    employees e
    LEFT JOIN employees m
        ON m.employee_id = e.manager_id
ORDER BY manager;
```

![self-join-result2](./img/sql-self-join-result2.png)

[Go to top](#sql)

### Aggregation

- An SQL aggregate function calculates on a set of values and returns a single value.

#### Avg

- Calculates the average value of a set.
- `DISTINCT` 쓰면 unique 한 값들만 포함.

To calculate the average salary of all employees, use `AVG`.

```sql
SELECT 
    AVG(salary)
FROM
    employees;
```

![avg-result](./img/sql-avg-result.png)

To calculate the distinct average salary of all employees, use `DISTINCT`.

```sql
SELECT 
    AVG(DISTINCT salary)
FROM
    employees;
```

![avg-distinct-result](./img/sql-avg-distinct-result.png)

To round the result to 2 decimal places, use `ROUND`.

```sql
SELECT 
    ROUND(AVG(DISTINCT salary), 2)
FROM
    employees;
```

![avg-round-result](./img/sql-avg-round-result.png)

To calculate the average salary of employees in the department id 5, use `WHERE`.

```sql
SELECT
    AVG(DISTINCT salary)
FROM
    employees
WHERE
    department_id = 5;
```

![avg-where-result](./img/sql-avg-where-result.png)

To get the departments and the average salary of employees of each department, use `GROUP BY`.

```sql
SELECT
    department_id,
    AVG(salary)
FROM
    employees
GROUP BY
    department_id;
```

![avg-group-by-result](./img/sql-avg-group-by-result.png)

To get the department name data, use `INNER JOIN`.

```sql
SELECT
    e.department_id,
    department_name,
    AVG(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id;
```

![avg-inner-join-result](./img/sql-avg-inner-join-result.png)

To sort salary in desc, use `ORDER BY`.

```sql
SELECT
    e.department_id,
    e.department_name,
    AVG(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id
ORDER BY
    AVG(salary) DESC;
```

![avg-order-by-result](./img/sql-avg-order-by-result.png)

To filter salary is greater then 400, use `HAVING`.

```sql
SELECT
    e.department_id,
    department_name,
    AVG(salary)
FROM
    employees e
INNER JOIN departments d ON d.department_id = e.department_id
GROUP BY
    e.department_id
HAVING AVG(salary) > 4000
ORDER BY AVG(salary) DESC;
```

![avg-having-result](./img/sql-avg-having-result.png)

To calculate the average salary of employees in each department and apply the average of it, use subquery.

```sql
SELECT
    AVG(t.employee_sal_avg)
FROM
    (
        SELECT
            AVG(salary) employee_sal_avg
        FROM
            employees
        GROUP BY
            department_id
    ) t;
```

![avg-subquery-result](./img/sql-avg-subquery-result.png)

[Go to top](#sql)

#### Count

- Returns the number of rows returned by a query. 
- `DISTINCT` 쓰면 unique 하고 null이 아닌 것들만 포함.


To get the number of rows in the employees table, use `COUNT`.

```sql
SELECT
    COUNT(*)
FROM
    employees;
```

![count-result](./img/sql-count-result.png)

To get the number of jobs, use `COUNT`.

```sql
SELECT 
    COUNT(job_id)
FROM
    employees;
```

![count-single-result](./img/sql-count-single-result.png)

To get the distinct number of jobs, use `DISTINCT`.

```sql
SELECT 
    COUNT(DISTINCT job_id)
FROM
    employees;
```

![count-single-distinct-result](./img/sql-count-single-distinct-result.png)

To find how many employees who work in the department id 6, use `WHERE`.

```sql
SELECT 
    COUNT(*)
FROM
    employees
WHERE
    department_id = 6;
```

![count-where-result](./img/sql-count-where-result.png)

To find the number of employees per department, use `GROUP BY`.

```sql
SELECT
    department_id,
    COUNT(*)
FROM
    employees
GROUP BY
    department_id;
```

![count-group-by-result](./img/sql-count-group-by-result.png)

To get department_name, use `INNER JOIN`.

```sql
SELECT
    e.department_id,
    d.department_name,
    COUNT(*)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id;
```

![count-inner-join-result](./img/sql-count-inner-join-result.png)

To sorts the result set based on the number of employees in descending order, use `ORDER BY`.

```sql
SELECT
    e.department_id,
    d.department_name,
    COUNT(*)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id
ORDER BY
    COUNT(*) DESC;
```

![count-order-by-result](./img/sql-count-order-by-result.png)

To selects only departments whose the number of employees is greater than 5, use `HAVING`

```sql
SELECT
    e.department_id,
    department_name,
    COUNT(*)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id
HAVING
    COUNT(*) > 5
ORDER BY
    COUNT(*) DESC;
```

![count-having-result](./img/sql-count-having-result.png)

[Go to top](#sql)

#### MAX

- To find the maximum value in a set of values. 
- `DISTINCT` 은 못씀. NULL 은 무시.

To get the highest (maximum) salary of employees in the employees table, use `MAX`

```sql
SELECT 
    MAX(salary)
FROM
    employees;
```

![max-result](./img/sql-max-result.png)

To get the employees who have the highest salary, use subquery.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary = (
        SELECT
            MAX(salary)
        FROM
            employees
    );
```

![max-subquery-result](./img/sql-max-subquery-result.png)

To find the highest salary of employee in each department, use `GROUP BY`.

```sql
SELECT
    department_id,
    MAX(salary)
FROM
    employees
GROUP BY
    department_id;
```

![max-group-by-result](./img/sql-max-group-by-result.png)

To include the department names in the result, use `INNER JOIN`.

```sql
SELECT
    d.department_id,
    department_name,
    MAX(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id;
```

![max-inner-join-result](./img/sql-max-inner-join-result.png)

To get the highest salaries of employees in each department and sorts the result set based on the highest salaries, use `ORDER BY`.

```sql
SELECT
    d.department_id,
    department_name,
    MAX(salary)
FROM
    employees e
INNER JOIN departments d ON d.department_id = e.department_id
GROUP BY
    e.department_id
ORDER BY
    MAX(salary) DESC;
```

![max-order-by-result](./img/sql-max-order-by-result.png)

To get the department that has employee whose highest salary is greater than 12000, use `HAVING`.

```sql
SELECT
    d.department_id,
    department_name,
    MAX(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id
HAVING
    MAX(salary) > 12000;
```

![max-having-result](./img/sql-max-having-result.png)

[Go to top](#sql)

#### MIN

- To find the minimum value in a set of values. 
- `DISTINCT` 은 못씀. NULL 은 무시.

To find the lowest (minimum) salary of employees, use `MIN`.

```sql
SELECT 
    MIN(salary)
FROM
    employees;
```

![min-result](./img/sql-min-result.png)

To get the information of the employee who has the lowest salary, use subquery.

```sql
SELECT
    employee_id,
    first_name,
    last_name,
    salary
FROM
    employees
WHERE
    salary = (
        SELECT
            MIN(salary)
        FROM
            employees
    );
```

![min-subquery-result](./img/sql-min-subquery-result.png)

To get the employees who have the lowest salaries in each department, use `GROUP BY`.

```sql
SELECT
    department_id,
    MIN(salary)
FROM
    employees
GROUP BY
    department_id;
```

![min-group-by-result](./img/sql-min-group-by-result.png)

To combine the department’s name in the result set, use `INNER JOIN`.

```sql
SELECT
    d.department_id,
    department_name,
    MIN(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    d.department_id;
```

![min-inner-join-result](./img/sql-min-inner-join-result.png)

To retrieves the employee who has the lowest salary in each department, then sorts these departments by the salary in ascending order, use `ORDER BY`.

```sql
SELECT
    d.department_id,
    department_name,
    MIN(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    d.department_id
ORDER BY
    MIN(salary);
```

![min-order-by-result](./img/sql-min-order-by-result.png)

To retrieves the employees who have the lowest salary in each department filter salary less than 3000, use `HAVING`.

```sql
SELECT
    d.department_id,
    d.department_name,
    MIN(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    d.department_id
HAVING
    MIN(salary) < 3000;
```

![min-having-result](./img/sql-min-having-result.png)

[Go to top](#sql)

#### SUM

- Returns the sum of all or distinct values.
- `DISTINCT` 쓰면 unique 하고 null이 아닌 것들만 포함.

To get the sum of salaries of all employees, use `SUM`.

```sql
SELECT 
    SUM(salary)
FROM
    employees;
```

![sum-result](./img/sql-sum-result.png)

To calculate the sum of salaries of employees who work in department id 5, use `WHERE`.

```sql
SELECT 
    SUM(salary)
FROM
    employees
WHERE
    department_id = 5;
```

![sum-where-result](./img/sql-sum-where-result.png)

To calculate the sum of salaries of employees for each department, use `GROUP BY`.

```sql
SELECT
    department_id,
    SUM(salary)
FROM
    employees
GROUP BY
    department_id;
```

![sum-group-by-result](./img/sql-sum-group-by-result.png)

To include the department name in the result set, use `INNER JOIN`.

```sql
SELECT
    e.department_id,
    department_name,
    SUM(salary)
FROM
    employees e
    INNER JOIN departments d
        ON d.department_id = e.department_id
GROUP BY
    e.department_id;
```

![sum-inner-join-result](./img/sql-sum-inner-join-result.png)

To have the sums of salaries of departments sorted in descending order, use `ORDER BY`.

```sql
SELECT
    e.department_id,
    department_name,
    SUM(salary)
FROM
    employees e
INNER JOIN departments d ON d.department_id = e.department_id
GROUP BY
    e.department_id
ORDER BY
    SUM(salary) DESC;
```

![sum-order-by-result](./img/sql-sum-order-by-result.png)

To get the departments and their sums of salaries greater than 57000, use `HAVING`.

```sql
SELECT
    e.department_id,
    department_name,
    SUM(salary)
FROM
    employees e
INNER JOIN departments d ON d.department_id = e.department_id
GROUP BY
    e.department_id
HAVING
    SUM(salary) > 57000
ORDER BY
    SUM(salary) DESC;
```

![sum-having-result](./img/sql-sum-having-result.png)

[Go to top](#sql)

### Grouping

todo: https://www.sqltutorial.org/sql-group-by/

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

- [SQL Tutorial](https://www.sqltutorial.org/)
- [SQL (wiki)](https://en.wikipedia.org/wiki/SQL)
- [SQLBolt](https://sqlbolt.com/)
- [SQLZOO](https://sqlzoo.net/wiki/)
- [w3school SQL](https://www.w3schools.com/sql)
- [sql-tutorial (javatpoint)](https://www.javatpoint.com/sql-tutorial)