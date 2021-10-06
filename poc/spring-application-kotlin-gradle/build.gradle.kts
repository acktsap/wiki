buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.2.0")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.spring") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions { jvmTarget = "1.8" }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-batch:2.5.5")
    implementation("org.springframework.batch.extensions:spring-batch-kotlin-dsl:0.1.0-SNAPSHOT")
    runtimeOnly("com.h2database:h2:1.4.200")
}
