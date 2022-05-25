plugins {
    java
    kotlin("jvm") version "1.6.10"
}

dependencies {
    // webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.4")

    // coroutine adaptor
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.1")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.4")
}
