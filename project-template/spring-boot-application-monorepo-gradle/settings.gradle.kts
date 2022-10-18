rootProject.name = "spring-boot-application-monorepo-gradle"

include("spring-common")
include("spring-boot-application-native-cli")
include("spring-boot-application-web")
include("kotlin-application-native-cli")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
    }

    versionCatalogs {
        create("libs") {
            library(
                "spring-boot-starter",
                "org.springframework.boot",
                "spring-boot-starter"
            ).withoutVersion()
            library(
                "spring-boot-starter-web",
                "org.springframework.boot",
                "spring-boot-starter-web"
            ).withoutVersion()
            library(
                "spring-boot-starter-log4j2",
                "org.springframework.boot",
                "spring-boot-starter-log4j2"
            ).withoutVersion()
            library(
                "spring-boot-starter-actuator",
                "org.springframework.boot",
                "spring-boot-starter-actuator"
            ).withoutVersion()
            library(
                "kotlin-logging",
                "io.github.microutils:kotlin-logging-jvm:3.0.1"
            )

            // test only
            library(
                "spring-boot-starter-test",
                "org.springframework.boot",
                "spring-boot-starter-test"
            ).withoutVersion()
            library(
                "mockito.kotlin",
                "org.mockito.kotlin:mockito-kotlin:4.0.0"
            )
            library(
                "junit",
                "org.junit.jupiter:junit-jupiter:5.9.1"
            )
            library(
                "assertj",
                "org.assertj:assertj-core:3.23.1"
            )
            library(
                "restassured",
                "io.rest-assured:rest-assured:5.2.0"
            )
            library(
                "restassured.spring.kotlin",
                "io.rest-assured:spring-mock-mvc-kotlin-extensions:5.2.0"
            )
        }
    }
}
