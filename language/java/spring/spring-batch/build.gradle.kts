plugins {
    java
    kotlin("jvm") version "1.6.10"
}

val developmentOnly = configurations.create("developmentOnly")
configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

dependencies {
    // spring batch
    implementation("org.springframework.boot:spring-boot-starter-batch:2.6.4")

    // for web launcher
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.4")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    // for embeded db
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.6.4")
    runtimeOnly("com.h2database:h2:1.4.200")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.4")
    testImplementation("org.springframework.batch:spring-batch-test:4.3.5")

    // test lombok
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")
}
