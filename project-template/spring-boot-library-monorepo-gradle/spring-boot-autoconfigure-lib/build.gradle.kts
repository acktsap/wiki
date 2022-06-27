plugins {
    id("acktsap.java-library-conventions")
    id("acktsap.maven-publish-conventions")
    id("acktsap.spring-conventions")
}

dependencies {
    api(project(":spring-lib"))
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    testImplementation("org.springframework.boot:spring-boot-test")
}
