# Elastic Search

## Run as docker

- Create: `docker-compose -f ./docker-compose.yml up --no-start`
- Create & Run: `docker-compose -f ./docker-compose.yml up`
- Start/Stop/Rm: `docker-compose -f ./docker-compose.yml start/stop/rm`
- Access:
  - `docker exec -it ${CONTAINER_NAME} bash`
  - `mysql -uroot -p${MYSQL_PASSWORD}"`
- Apply dump: `docker exec -i ${CONTAINER_NAME} mysql -uroot -p{MYSQL_PASSWORD} < dbdump.db`

## Management

Test without commit. Run all running sql with scripts

management tools

- [dbeaver](https://dbeaver.io/download/)
- [auto commit & manual commit](https://github.com/dbeaver/dbeaver/wiki/Auto-and-Manual-Commit-Modes)

## Database

```sql
CREATE DATABASE testdb;
```

## User

Create & give GRANT

```sql
CREATE USER testuser@'localhost' identified by 'userpw';
GRANT ALL ON testdb.* TO testuser@'localhost';
GRANT SELECT ON testdb2.table1 TO testuser@'localhost';
GRANT SELECT ON testdb2.table2 TO testuser@'localhost';
FLUSH PRIVILEGES;

SHOW GRANTS FOR testuser@'localhost';
```

note that GRANT is done by OR operation

```sql
GRANT SELECT ON testdb.* TO testuser@localhost;
/* this fails since testdb.* is always success by OR operation */
REVOKE ALL PRIVILEGES ON testdb.tblname FROM testuser@localhost;
```

## Use table, show table

```sql
use testdb;
CREATE TABLE testtable (
  col1 VARCHAR(5),
  col2 INTEGER
);
desc testtable;
```

## Replication

Create & insert init value

```sql
CREATE DATABASE IF NOT EXISTS testdb;
CREATE TABLE testdb.testtable (test varchar(5));
INSERT testdb.testtable VALUES ("test1");
INSERT testdb.testtable VALUES ("test2");
```

Dump

```sql
mysqldump --databases testdb -uroot -p$MYSQL_PASSWORD > mydump.sql
```

1. [Master config](https://dev.mysql.com/doc/refman/5.7/en/replication-howto-masterbaseconfig.html)
2. Create replicator user with target priviledge
3. [Lock master](https://dev.mysql.com/doc/refman/5.7/en/replication-howto-masterstatus.html)
4. [Dump master data](https://dev.mysql.com/doc/refman/5.7/en/replication-snapshot-method.html)
5. [Setup slaves](https://dev.mysql.com/doc/refman/5.7/en/replication-setup-slaves.html)
6. Unlock master

[see also](https://gangnam-americano.tistory.com/12)

## Reference

Grant

https://stackoverflow.com/questions/8369253/grant-user-access-to-limited-number-of-tables-in-mysql

https://stackoverflow.com/questions/8131849/how-to-subtract-privileges-in-mysql
