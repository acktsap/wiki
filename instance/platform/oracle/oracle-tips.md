# Oracle Tips

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
