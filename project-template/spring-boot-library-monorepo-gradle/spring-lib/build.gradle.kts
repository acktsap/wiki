plugins {
    id("custom.java-library-conventions")
    id("custom.kotlin-conventions")
    id("custom.spring-boot-conventions")
    id("custom.maven-publish-conventions")
}

dependencies {
    implementation(project(":spring-lib-module"))

    implementation("org.springframework.boot:spring-boot-starter")
}
