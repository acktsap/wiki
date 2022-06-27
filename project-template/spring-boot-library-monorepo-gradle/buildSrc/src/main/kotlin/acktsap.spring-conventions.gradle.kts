plugins {
    id("acktsap.java-library-conventions")
}


dependencies {
    constraints {
        implementation("org.springframework.boot:spring-boot-autoconfigure:2.7.1")
        testImplementation("org.springframework.boot:spring-boot-test:2.7.1")
    }
}
