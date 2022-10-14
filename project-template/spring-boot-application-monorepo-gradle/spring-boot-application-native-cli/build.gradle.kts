plugins {
    id("acktsap.kotlin-conventions")
    id("acktsap.spring-boot-conventions")
}

dependencies {
    implementation(project(":spring-common"))

    implementation(libs.spring.boot.starter)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.mockito.kotlin)
}
