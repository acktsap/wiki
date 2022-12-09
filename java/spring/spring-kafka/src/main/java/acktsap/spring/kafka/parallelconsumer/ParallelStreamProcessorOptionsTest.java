package acktsap.spring.kafka.parallelconsumer;

import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static acktsap.spring.kafka.parallelconsumer.SendMessage.TOPIC_NAME;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public class ParallelStreamProcessorOptionsTest {

    private static final Logger logger = getLogger(ParallelStreamProcessorOptionsTest.class);

    public static void main(String[] args) {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        Consumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProperties);

        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        Producer<String, String> kafkaProducer = new KafkaProducer<>(producerProperties);

        var options = ParallelConsumerOptions.<String, String>builder()
            .consumer(kafkaConsumer)
            .producer(kafkaProducer)
            .ordering(ParallelConsumerOptions.ProcessingOrder.UNORDERED) // ordering 맞춰주는 수준
            .maxConcurrency(9) // ThreadPoolExecutor pool size (pc-xxx로 시작)
            .commitMode(ParallelConsumerOptions.CommitMode.PERIODIC_CONSUMER_ASYNCHRONOUS) // offset commit 옵션
            .thresholdForTimeSpendInQueueWarning(Duration.ofMillis(1L)) // work queue에서 이 시간이 지나면 warning 발생시킴
            .commitInterval(Duration.ofSeconds(3L)) // offset commit interval
            .allowEagerProcessingDuringTransactionCommit(false) // transaction commit이 되는 동안 processing을 할건지
            .batchSize(3) // ThreadPoolExecutor에 한번에 몇개의 consumer message를 넘겨줄건지
            .commitLockAcquisitionTimeout(Duration.ofSeconds(5L)) // offset commit lock을 얻는 timeout
            .managedExecutorService("BrokerPoolExecutorService") // InitialContext.doLookup에서 broker pool을 하는 thread를 가져오기 위한 이름
            .managedThreadFactory("WorkerPoolFactory")
            .retryDelayProvider(context -> Duration.ofSeconds(3L)) // 실패하면 무조건 retry 하는데 retry하는 term
            .sendTimeout(Duration.ofSeconds(3L)) // pollAndProduce 같은거 할 때 producer future timeout (future.get(timeout값))
            .offsetCommitTimeout(Duration.ofSeconds(10L)) // offset commit timeout
            .produceLockAcquisitionTimeout(Duration.ofSeconds(10L)) // produce 할 때 lock을 얻음. 이 때 기다리는 시간
            .build();

        ParallelStreamProcessor<String, String> parallelStreamProcessor = ParallelStreamProcessor.createEosStreamProcessor(options);
        parallelStreamProcessor.subscribe(List.of(TOPIC_NAME));

        parallelStreamProcessor.pollAndProduceMany(pollContext -> {
            logger.info("consumes: {}", pollContext);
            List<ProducerRecord<String, String>> producerRecords = pollContext.stream()
                .map(it -> new ProducerRecord<String, String>("newTopic", it.value()))
                .collect(toList());
            logger.info("produces: {}", producerRecords);
            return producerRecords;
        });
    }
}
