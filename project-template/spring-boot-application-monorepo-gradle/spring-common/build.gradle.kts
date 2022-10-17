plugins {
    id("acktsap.java-conventions")
    id("acktsap.kotlin-conventions")
    id("acktsap.coverage-conventions")
    id("acktsap.spring-conventions")
}

dependencies {
    testImplementation(libs.spring.boot.starter.test)
}
