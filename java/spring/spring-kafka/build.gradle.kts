plugins {
    java
    kotlin("jvm") version "1.7.20"
}

val springBootVersion = "3.0.0"
val kafkaClientVersion = "3.2.0"
val springKafkaVersion = "3.0.0"

dependencies {
    // boot
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // kafka
    implementation("org.springframework.kafka:spring-kafka:$springKafkaVersion")
    implementation("org.apache.kafka:kafka-streams:$kafkaClientVersion") // optional
    implementation("org.springframework.kafka:spring-kafka-test:$springKafkaVersion")
    implementation("io.confluent.parallelconsumer:parallel-consumer-core:0.5.2.4")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.springframework.kafka:spring-kafka-test:$springKafkaVersion")
}
