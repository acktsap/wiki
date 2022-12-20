# Run Kafka

- [Using Bitnami Kafka Docker images](#using-bitnami-kafka-docker-images)
  - [Interacting with zookeeper](#interacting-with-zookeeper)
  - [Interacting with kafka](#interacting-with-kafka)

## Using Bitnami Kafka Docker images

[bitnami/kafka](https://hub.docker.com/r/bitnami/kafka)

[bitnami/zookeeper](https://hub.docker.com/r/bitnami/zookeeper)

```sh
# pull image
docker pull bitnami/kafka
docker pull bitnami/zookeeper

# create a network
docker network create kafka-tier --driver bridge

# run zookeeper1
docker run -d --name zookeeper1 \
    --network kafka-tier \
    -e ALLOW_ANONYMOUS_LOGIN=yes \
    -e ZOO_SERVER_ID=1 \
    -e ZOO_SERVERS=0.0.0.0:2888:3888,zookeeper2:2888:3888,zookeeper3:2888:3888 \
    bitnami/zookeeper:latest

# run zookeeper2
docker run -d --name zookeeper2 \
    --network kafka-tier \
    -e ALLOW_ANONYMOUS_LOGIN=yes \
    -e ZOO_SERVER_ID=2 \
    -e ZOO_SERVERS=zookeeper1:2888:3888,0.0.0.0:2888:3888,zookeeper3:2888:3888 \
    bitnami/zookeeper:latest

# run zookeeper3
docker run -d --name zookeeper3 \
    --network kafka-tier \
    -e ALLOW_ANONYMOUS_LOGIN=yes \
    -e ZOO_SERVER_ID=3 \
    -e ZOO_SERVERS=zookeeper1:2888:3888,zookeeper2:2888:3888,0.0.0.0:2888:3888 \
    bitnami/zookeeper:latest

# run kafka server1
docker run -d --name kafka-server1 \
    --network kafka-tier \
    -e ALLOW_PLAINTEXT_LISTENER=yes \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181,zookeeper2:2181,zookeeper3:2181 \
    -e KAFKA_BROKER_ID=1 \
    bitnami/kafka:latest

# run kafka server2
docker run -d --name kafka-server2 \
    --network kafka-tier \
    -e ALLOW_PLAINTEXT_LISTENER=yes \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181,zookeeper2:2181,zookeeper3:2181 \
    -e KAFKA_BROKER_ID=2 \
    bitnami/kafka:latest

# run kafka server3
docker run -d --name kafka-server3 \
    --network kafka-tier \
    -e ALLOW_PLAINTEXT_LISTENER=yes \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181,zookeeper2:2181,zookeeper3:2181 \
    -e KAFKA_BROKER_ID=3 \
    bitnami/kafka:latest
```

### Interacting with zookeeper

```sh
# run zkCli
docker run -it --rm \
    --network kafka-tier \
    bitnami/zookeeper:latest zkCli.sh -server zookeeper1:2181

# ls rootj
ls /

# ls zookeeper
ls /zookeeper

# get zookeeper config
get /zookeeper/config

# check kafka brokers ids
ls /brokers/ids
```

### Interacting with kafka

```sh
# create topic
docker run -it --rm \
    --network kafka-tier \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    bitnami/kafka:latest kafka-topics.sh --bootstrap-server kafka-server1:9092 \
    --replication-factor 1 --partitions 1 --topic peter-topic --create

# list topic
docker run -it --rm \
    --network kafka-tier \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    bitnami/kafka:latest kafka-topics.sh --list --bootstrap-server kafka-server1:9092

# list topic in cluster 2
docker run -it --rm \
    --network kafka-tier \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    bitnami/kafka:latest kafka-topics.sh --list --bootstrap-server kafka-server2:9092

# remove topic
docker run -it --rm \
    --network kafka-tier \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    bitnami/kafka:latest kafka-topics.sh --bootstrap-server kafka-server1:9092 \
    --topic peter-topic --delete

# produce message
docker run -it --rm \
    --network kafka-tier \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    bitnami/kafka:latest kafka-console-producer.sh --broker-list kafka-server1:9092 \
    --topic peter-topic

# consume message from beginning
docker run -it --rm \
    --network kafka-tier \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    bitnami/kafka:latest kafka-console-consumer.sh --bootstrap-server kafka-server1:9092 \
    --topic peter-topic --from-beginning
```

