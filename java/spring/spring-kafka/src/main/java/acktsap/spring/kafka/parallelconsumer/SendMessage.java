package acktsap.spring.kafka.parallelconsumer;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;

/*
    다른 테스트에서 사용하는 용도로 message 보내는 친구
 */
public class SendMessage {

    private static final Logger logger = getLogger(SendMessage.class);
    static final String TOPIC_NAME = "test-topic-1";
    static final int PARTITION_NUMBER = 5;

    public static void main(String[] args) throws Exception {
        createTopic();
        sendMessage();
    }

    private static void sendMessage() {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        Producer<String, String> kafkaProducer = new KafkaProducer<>(producerProperties);

        IntStream.range(0, 100).forEach(i -> {
            int partition = i % PARTITION_NUMBER;
            String key = "";
            String value = "testMessage" + i;
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, partition, key, value);
            logger.info("send: {}", producerRecord);
            kafkaProducer.send(producerRecord);
        });

        kafkaProducer.close();
    }

    private static void createTopic() throws Exception {
        Properties adminProperties = new Properties();
        adminProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        Admin admin = Admin.create(adminProperties);

        NewTopic topic = TopicBuilder.name(TOPIC_NAME)
            .partitions(PARTITION_NUMBER)
            .build();
        admin.createTopics(List.of(topic));

        ListTopicsResult listTopicsResult = admin.listTopics();
        logger.info("list topic result: {}", listTopicsResult.listings().get());

        admin.close();
    }
}
