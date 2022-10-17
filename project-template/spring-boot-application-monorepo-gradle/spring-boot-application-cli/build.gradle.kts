plugins {
    id("acktsap.kotlin-conventions")
    id("acktsap.coverage-conventions")
    id("acktsap.spring-conventions")
    id("acktsap.spring-boot-jar-conventions")
}

dependencies {
    implementation(project(":spring-common"))

    implementation(libs.spring.boot.starter)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.mockito.kotlin)
}
