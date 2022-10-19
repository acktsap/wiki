plugins {
    id("acktsap.kotlin-conventions")
    id("acktsap.coverage-conventions")
    id("acktsap.spring-conventions")
    id("acktsap.distribution-conventions")
}

dependencies {
    implementation(project(":spring-common"))

    // spring
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.log4j2)

    // test
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.restassured.spring.kotlin)
}
