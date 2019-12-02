# Elastic Search

## Run as docker

- Create: `docker-compose -f ./docker-compose.yml up --no-start`
- Create & Run: `docker-compose -f ./docker-compose.yml up`
- Start/Stop/Rm: `docker-compose -f ./docker-compose.yml start/stop/rm`
- Access:
  - `docker exec -it ${containerName} bash`
  - `mysql -u root -p${MYSQL_PASSWORD}"

## Management

Test without commit. Run all running sql with scripts

management tools

- [dbeaver](https://dbeaver.io/download/)
- [auto commit & manual commit](https://github.com/dbeaver/dbeaver/wiki/Auto-and-Manual-Commit-Modes)

## Create db, user

```sql
mysql -u root -p$MYSQL_ROOT_PASSWORD;
CREATE USER testuser@'localhost' identified by 'userpw';
CREATE DATABASE testdb;
GRANT ALL ON testdb.* TO testuser@'localhost';
FLUSH PRIVILEGES;
exit
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
CREATE DATABASE IF NOT EXISTS replidb;
CREATE TABLE replidb.testtable (test varchar(5));
INSERT testdb.testtable VALUES ("test1");
INSERT testdb.testtable VALUES ("test2");
```

1. [Master config](https://dev.mysql.com/doc/refman/5.7/en/replication-howto-masterbaseconfig.html)
2. Create replicator user with target priviledge
3. [Lock master](https://dev.mysql.com/doc/refman/5.7/en/replication-howto-masterstatus.html)
4. [Dump master data](https://dev.mysql.com/doc/refman/5.7/en/replication-snapshot-method.html)
5. [Setup slaves](https://dev.mysql.com/doc/refman/5.7/en/replication-setup-slaves.html)
6. Unlock master

[see also](https://gangnam-americano.tistory.com/12)

## Reference
