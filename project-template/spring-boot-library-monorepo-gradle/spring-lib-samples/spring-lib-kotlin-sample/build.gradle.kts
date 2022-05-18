plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.6")
    implementation("acktsap:spring-boot-starter-lib:0.0.1-SNAPSHOT")
}