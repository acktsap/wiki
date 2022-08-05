plugins {
    java
    kotlin("jvm") version "1.6.20"
}

dependencies {
    // why compileOnly? -> servelt container가 runtime시 제공해 줌
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework:spring-webmvc:5.3.22")
    runtimeOnly("com.h2database:h2:1.4.200")
}
