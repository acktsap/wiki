rootProject.name = "spring-boot-library-monorepo-java-gradle"

include("spring-boot-autoconfigure-lib")
include("spring-boot-starter-lib")
include("spring-lib")
include("spring-lib-module")

include("spring-lib-sample:spring-lib-test-sample")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val springBootVersion = "2.7.1"

            library(
                "spring-boot-autoconfigure",
                "org.springframework.boot:spring-boot-autoconfigure:$springBootVersion"
            )
            library(
                "slf4j",
                "org.slf4j:slf4j-api:1.7.36"
            )
            library(
                "apache-commons-math",
                "org.apache.commons:commons-math3:3.6.1"
            )

            // test only
            library(
                "junit",
                "org.junit.jupiter:junit-jupiter:5.+"
            )
            library(
                "assertj",
                "org.assertj:assertj-core:3.+"
            )
            library(
                "mockito",
                "org.mockito:mockito-core:3.+"
            )
            library(
                "mockito-kotlin",
                "org.mockito.kotlin:mockito-kotlin:4.+"
            )
            library(
                "spring-boot-test",
                "org.springframework.boot:spring-boot-test:$springBootVersion"
            )
            library(
                "log4j",
                "org.apache.logging.log4j:log4j-slf4j-impl:2.17.2"
            )
        }
    }
}

