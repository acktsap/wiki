# Oracle Tips

## Sql

### MERGE INTO

```sql
MERGE INTO ${target_table} -- Update또는 Insert할 테이블 명 혹은 뷰
USING ${sub_table}
ON {target_table.a = sub_table.a}  -- target table과 sub_table간의 조인 조건. 일치여부 (UPDATE/INSERT) 조건은 바로 ON절에 의해 결정
WHEN MATCHED THEN  -- 일치되는 경우 UPDATE
UPDATE SET -- 조인조건(on)절에 사용한 컬럼은 UPDATE가 불가
  [컬럼1] = [값1],
  [컬럼2] = [값2],
  ...​
WHEN NOT MATCHED THEN -- 일치 안 되는 경우 INSERT
INSERT
  (
    컬럼1,
    컬럼2,
    ...
  )
VALUES
  (
    값1,
    값2,
    ...
  )
```

## Sequence

```sql
-- show all sequences
SELECT * FROM USER_SEQUENCES  

-- use
INSERT INTO SOME_TABLE VALUES(${SEQUENCE_NAME}.NEXTVAL);
```

## Functions

Oracle aggregate functions (sum, average, ... ) generally ignore NULL values. 

- NVL(expr1, expr2) : if (expr1 != null) expr1 else expr2
- NVL(expr1, expr2, expr3) : if (expr1 != null) expr2 else expr3
- SUM

```sql
-- 1 + 2 -> 3
-- 1 + null -> 1
-- null + null -> 0
NVL(SUM(COL1), 0)
```

Conditional

- DECODE(column, condition, expr1, expr2) : if (condition.match(column) expr1 else expr2
