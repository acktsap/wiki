plugins {
    java
    kotlin("jvm") version "1.7.20"
}

val springBootVersion = "3.0.0-M5"

dependencies {
    // boot
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // mock http server
    implementation("com.squareup.okhttp3:mockwebserver:4.10.0")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}
