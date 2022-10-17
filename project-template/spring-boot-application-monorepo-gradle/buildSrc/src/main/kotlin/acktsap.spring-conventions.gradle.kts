import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
}

configure<DependencyManagementExtension> {
    imports {
        // use spring boot gradle plugin version
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}
