# Spring Kafka

- [Admin](#admin)
- [Configuring Topic](#configuring-topic)
- [Producer](#producer)
- [Consumer](#consumer)
- [Samples](#samples)
- [Reference](#reference)

## Admin

## Configuring Topic

- `KafkaAdmin`빈으로 등록하고 `TopicBuilder` 사용해서 `NewTopic`을 빈으로 등록하면 `KafkaAdmin`이 알아서 Topic 등록해줌.

## Producer

- `KafkaTemplate`를 사용해서 message 보내기 가능.
- `ProducerFactory`를 사용해서 `KafkaTemplate`을 bean으로 등록 가능.

## Consumer

- `ConsumerFactory`

## Samples

https://github.com/spring-projects/spring-kafka/tree/main/samples

## Reference

- [Spring Kafka Official Doc (3.0.0-RC2)](https://docs.spring.io/spring-kafka/docs/3.0.0-RC2/reference/html/)
- [Kafka Streams (kafka official)](https://kafka.apache.org/33/documentation/streams/)
- [Spring Kafka Support (spring official)](https://spring.io/projects/spring-kafka#support)
