import kotlinx.kover.api.JacocoEngine
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict") // enable jsr305 null-safety in kotlin
        jvmTarget = "1.8"
        // https://kotlinlang.org/docs/compatibility-modes.html
        languageVersion = "1.5" // source compatibility
        apiVersion = "1.5" // allow api to use only this version
    }
}
