plugins {
    java
    kotlin("jvm") version "1.6.10"
}

val springBootVersion = "2.6.4"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-aop:${springBootVersion}")
    implementation("javax.validation:validation-api:2.0.1.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")
}
