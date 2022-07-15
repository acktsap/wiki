plugins {
    java
    kotlin("jvm") version "1.7.10"
}

val springBootVersion = "2.7.1"
val springVersion = "5.3.18"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-aop:$springBootVersion")
    implementation("javax.validation:validation-api:2.0.1.Final")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
}
