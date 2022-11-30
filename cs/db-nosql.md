# NoSQL

- [Motivation](#motivation)
- [Advantage over RDB](#advantage-over-rdb)
- [NoSQL Design Considerations](#nosql-design-considerations)
- [CAP Theorm](#cap-theorm)
- [Consistency](#consistency)
  - [Type of Consistency](#type-of-consistency)
  - [ACID vs BASE](#acid-vs-base)
  - [Versioning](#versioning)
  - [Vector Clock (Versioning)](#vector-clock-versioning)
- [Partitioning](#partitioning)
  - [Mapping object to server](#mapping-object-to-server)
    - [Consistent Hashing](#consistent-hashing)
  - [R/W on Partitioned Data](#rw-on-partitioned-data)
  - [Memberchip Changes](#memberchip-changes)
- [Storage Layout](#storage-layout)
  - [Row-Based Storage Layout](#row-based-storage-layout)
  - [Columnar Storage Layout](#columnar-storage-layout)
  - [Columnar Storage Layout with Locality Groups](#columnar-storage-layout-with-locality-groups)
  - [Log Structured Merge Trees (LSM-trees)](#log-structured-merge-trees-lsm-trees)
- [Query Models](#query-models)
  - [Companion SQL-database](#companion-sql-database)
  - [Scatter/Gather Local Search](#scattergather-local-search)
  - [Distributed B+ Tree](#distributed-b-tree)
  - [Previx Hash Table](#previx-hash-table)
  - [OR-Junctions](#or-junctions)
  - [AND-Junctions](#and-junctions)
- [Key-Value Stores](#key-value-stores)
- [Document Databases](#document-databases)
- [Column-Orignted Databases](#column-orignted-databases)
- [Reference](#reference)

## Motivation

## Advantage over RDB

## NoSQL Design Considerations

## CAP Theorm

- shared-data system에서는 다음의 3개 중 2개 초과를 보장할 수 없음.
  - Consistency : 분산된 데이터 간 동일한 시간에 조회하면 동일한 데이터를 얻는 것.
  - Availability : 항상 이용가능한 것.
  - Partition Tolerance  : 네트워크 장애가 발생해도 시스템이 정상적으로 운영되어야 함.
- Partition Tolerance를 보장하면서.
  - Concsistency를 보장하려면 네트워크 장애가 발생하면 기다려야함 (Availability 불만족).
  - Availability를 보장하려면 네트워크 장애가 발생해도 동작해야함. 그 서버에는 값이 다를 수 있음 (Consistency 불만족).
- 그렇다고 Partition Tolerance를 버리면 분산 하는 의미가 없음.

## Consistency

- 데이터 일관성.

### Type of Consistency

### ACID vs BASE

### Versioning

### Vector Clock (Versioning)

## Partitioning

### Mapping object to server 

#### Consistent Hashing

### R/W on Partitioned Data

### Memberchip Changes

## Storage Layout

### Row-Based Storage Layout

### Columnar Storage Layout

### Columnar Storage Layout with Locality Groups

### Log Structured Merge Trees (LSM-trees)

## Query Models

### Companion SQL-database

### Scatter/Gather Local Search

### Distributed B+ Tree

### Previx Hash Table

### OR-Junctions

### AND-Junctions

## Key-Value Stores

## Document Databases

## Column-Orignted Databases

## Reference