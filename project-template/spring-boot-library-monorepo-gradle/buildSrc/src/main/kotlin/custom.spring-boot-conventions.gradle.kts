plugins {
    id("custom.java-library-conventions")
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        // get version from "org.springframework.boot:spring-boot-gradle-plugin:x.x.x"
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
