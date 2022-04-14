plugins {
    id("custom.java-library-conventions")
    id("custom.spring-boot-conventions")
    id("custom.maven-publish-conventions")
}

dependencies {
    api(project(":spring-lib"))

    implementation("org.springframework.boot:spring-boot-starter")
}
