plugins {
    id("acktsap.java-library-conventions")
    id("acktsap.maven-publish-conventions")
}

dependencies {
    api(project(":spring-boot-autoconfigure-lib")) // mostly, api
    api(project(":spring-lib"))
}
