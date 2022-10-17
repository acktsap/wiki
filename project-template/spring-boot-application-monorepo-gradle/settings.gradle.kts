rootProject.name = "spring-boot-application-monorepo-gradle"

include("spring-common")
include("spring-boot-application-cli")
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
                "spring-boot-starter-test",
                "org.springframework.boot",
                "spring-boot-starter-test"
            ).withoutVersion()

            library(
                "mockito.kotlin",
                "org.mockito.kotlin:mockito-kotlin:4.0.0"
            )
        }
    }
}
