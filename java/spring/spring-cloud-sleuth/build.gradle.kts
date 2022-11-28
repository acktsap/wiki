plugins {
    java
    kotlin("jvm") version "1.6.10"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:2.6.4")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.1.1")
}
