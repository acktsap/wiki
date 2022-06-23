plugins {
    java
    kotlin("jvm") version "1.6.10"
}

val springBootVersion by extra("2.7.0")

dependencies {
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")

    // test lombok
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")
}
