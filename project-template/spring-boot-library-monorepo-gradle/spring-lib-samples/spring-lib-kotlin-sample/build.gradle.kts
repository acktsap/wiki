plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":spring-boot-starter-lib"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.1")
}
