plugins {
    `kotlin-dsl` // support convension plugins in kotlin
}

repositories {
    mavenCentral()
    gradlePluginPortal() // give accees to gradle community plugins
    maven {
        url = uri("https://repo.spring.io/milestone")
    }
}

// to remove following warning
// Task :buildSrc:compileKotlin
// 'compileJava' task (current target is 17) and 'compileKotlin' task (current target is 1.8) jvm target compatibility should be set to the same Java version.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    implementation("org.jlleitschuh.gradle.ktlint:org.jlleitschuh.gradle.ktlint.gradle.plugin:10.2.1")
    implementation("org.jetbrains.kotlinx:kover:0.6.1")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.0-M4")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.14.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.7.20")
    implementation("org.graalvm.buildtools.native:org.graalvm.buildtools.native.gradle.plugin:0.9.14")
}
