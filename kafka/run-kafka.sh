# pull image
# docker pull bitnami/kafka
# docker pull bitnami/zookeeper

# create a network
docker network create kafka-tier --driver bridge

# run zookeeper1
docker run -d --name zookeeper1 \
    --network kafka-tier \
    -e ALLOW_ANONYMOUS_LOGIN=yes \
    -e ZOO_SERVER_ID=1 \
    -e ZOO_SERVERS=0.0.0.0:2888:3888 \
    bitnami/zookeeper:latest

# run kafka server1
docker run -d --name kafka-server1 \
    --network kafka-tier \
    -e ALLOW_PLAINTEXT_LISTENER=yes \
    -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper1:2181 \
    -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092 \
    -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092 \
    -e KAFKA_BROKER_ID=1 \
    -p 9092:9092 \
    bitnami/kafka:latest
