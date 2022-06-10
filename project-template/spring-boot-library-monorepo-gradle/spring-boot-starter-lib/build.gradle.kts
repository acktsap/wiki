plugins {
    id("custom.java-library-conventions")
    id("custom.spring-boot-conventions")
    id("custom.maven-publish-conventions")
}

dependencies {
    api(project(":spring-boot-autoconfigure-lib")) // mostly, api
    api(project(":spring-lib"))
}
