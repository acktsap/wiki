plugins {
    java
    kotlin("jvm") version "1.7.10"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.1")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.7.1")

    implementation("org.apache.tika:tika-core:1.23")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.1")
}
