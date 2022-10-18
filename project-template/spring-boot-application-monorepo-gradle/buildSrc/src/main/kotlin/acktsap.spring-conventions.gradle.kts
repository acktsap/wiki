import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
}

configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.0.0-M4")

        // when using spring boot plugin, use spring boot gradle plugin version
        // mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}


configurations {
    all {
        // to use log4j2, exclude default logback
        exclude(group = "org.springframework.boot", module= "spring-boot-starter-logging")
    }
}
