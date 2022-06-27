plugins {
    id("acktsap.java-library-conventions")
    id("acktsap.kotlin-conventions")
    id("acktsap.maven-publish-conventions")
    id("acktsap.spring-conventions")
}

dependencies {
    implementation(project(":spring-lib-module"))
    implementation("org.slf4j:slf4j-api")

    runtimeOnly("org.apache.logging.log4j:log4j")
}
