plugins {
    id("custom.java-library-conventions")
    id("custom.spring-boot-conventions")
    id("custom.maven-publish-conventions")
}

dependencies {
    implementation(project(":spring-boot-autoconfigure-lib"))
    api(project(":spring-lib"))
}
