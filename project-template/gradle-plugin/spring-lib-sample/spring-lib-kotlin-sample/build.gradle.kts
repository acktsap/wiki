plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":spring-boot-starter-lib"))
    // implementation("acktsap:spring-boot-starter-lib:0.1.0-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.1")
}
