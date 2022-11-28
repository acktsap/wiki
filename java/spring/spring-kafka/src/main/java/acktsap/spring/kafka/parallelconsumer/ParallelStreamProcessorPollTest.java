package acktsap.spring.kafka.parallelconsumer;

import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static acktsap.spring.kafka.parallelconsumer.SendMessage.TOPIC_NAME;
import static org.slf4j.LoggerFactory.getLogger;

public class ParallelStreamProcessorPollTest {

    private static final Logger logger = getLogger(ParallelStreamProcessorPollTest.class);

    public static void main(String[] args) {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        Consumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProperties);

        var options = ParallelConsumerOptions.<String, String>builder()
            .ordering(ParallelConsumerOptions.ProcessingOrder.KEY)
            .maxConcurrency(1000)
            .consumer(kafkaConsumer)
            .build();
        ParallelStreamProcessor<String, String> parallelStreamProcessor = ParallelStreamProcessor.createEosStreamProcessor(options);
        parallelStreamProcessor.subscribe(List.of(TOPIC_NAME));

        parallelStreamProcessor.poll(pollContext -> {
            logger.info("consumes: {}", pollContext);
        });
    }
}
