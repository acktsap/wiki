buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
}

plugins {
    kotlin("jvm") version "1.5.20"
}

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
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
}
