plugins {
    id("acktsap.java-library-conventions")
    id("acktsap.coverage-conventions")
    id("acktsap.maven-publish-conventions")
}

dependencies {
    api(project(":spring-lib"))
    implementation(libs.spring.boot.autoconfigure)

    testImplementation(libs.junit)
    testImplementation(libs.assertj)
    testImplementation(libs.mockito)
    testImplementation(libs.spring.boot.test)
}
