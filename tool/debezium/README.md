# Debezium

## Introduction

- A distributed platform that turns your existing databases into event streams, so applications can see and respond immediately to each row-level change in the databases.
- Built on top of Apache Kafka.
- Includes multiple connectors.

## Starting Sample

Podman

```sh
## create user-defined bridge
docker network create dbz-net

## start zookeeper container
docker run --rm --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 --net dbz-net debezium/zookeeper:1.8

## start kafka container
# docker run --rm --name kafka -p 9092:9092 --network dbz-net debezium/kafka:1.8
docker run --rm --name kafka -p 9092:9092 --net dbz-net debezium/kafka:1.8 # not works
```

[see also](https://debezium.io/documentation/reference/1.8/tutorial.html#starting-services)
