plugins {
    java
}

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    implementation(project(":spring-boot-starter-lib"))
    // implementation("acktsap:spring-boot-starter-lib:0.1.0-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.1")
}
