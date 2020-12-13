buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
}

plugins {
    kotlin("jvm") version "1.4.21"
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        jcenter()
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                val compileJvmTarget = "11"
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = compileJvmTarget
            }
        }
        compileTestKotlin {
            kotlinOptions {
                val testJvmTarget = "11"
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = testJvmTarget
            }
        }
    }

    dependencies {
        // Align versions of all Kotlin components
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

        // Use the Kotlin JDK 8 standard library.
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Use the Kotlin test library.
        testImplementation("org.jetbrains.kotlin:kotlin-test")

        // Use the Kotlin JUnit integration.
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    }
}
