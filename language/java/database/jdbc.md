# Jdbc

## Introduction

JDBC helps you to write Java applications that manage these three programming activities:

- Connect to a data source, like a database.
- Send queries and update statements to the database.
- Retrieve and process the results received from the database in answer to your query.

## Components

- The JDBC API
  - Execute SQL statements, retrieve results, and propagate changes back to an underlying data source.
  - Part of the the Java™ Standard Edition (Java™ SE ) and the Java™ Enterprise Edition (Java™ EE).
  - The JDBC 4.0 API is divided into two packages: `java.sql` and `javax.sql` and both are included in the Java SE and Java EE platforms.
- JDBC Driver Manager
  - The JDBC `DriverManager` defines objects which can connect Java applications to a JDBC driver.
  - The Standard Extension packages `javax.naming` and `javax.sql` let you use a DataSource object
    registered with a Java Naming and Directory Interface™ (JNDI) naming service to establish a connection with a data source.
  - Using DataSource object is recommended.
- JDBC Test Suite
  - Helps you to determine that JDBC drivers will run your program.
- JDBC-ODBC Bridge
  - Java Software bridge provides JDBC access via ODBC drivers

## References

- [oracle jdbc tutorial](https://docs.oracle.com/javase/tutorial/jdbc/index.html)
