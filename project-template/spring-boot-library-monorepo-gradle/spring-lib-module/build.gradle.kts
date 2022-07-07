plugins {
    id("acktsap.java-library-conventions")
    id("acktsap.maven-publish-conventions")
}

dependencies {
    implementation(libs.apache.commons.math)

    testImplementation(libs.junit)
    testImplementation(libs.assertj)
    testImplementation(libs.mockito)
}
