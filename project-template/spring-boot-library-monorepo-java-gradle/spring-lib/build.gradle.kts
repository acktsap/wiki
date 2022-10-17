plugins {
    id("acktsap.java-library-conventions")
    id("acktsap.maven-publish-conventions")
}

dependencies {
    implementation(project(":spring-lib-module"))

    implementation(libs.slf4j)

    testImplementation(libs.junit)
    testImplementation(libs.assertj)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
    testRuntimeOnly(libs.log4j)
}
